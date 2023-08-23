package com.jeesite.modules.device.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jeesite.modules.attachment.common.AttachmentType;
import com.jeesite.modules.attachment.service.BaseAttachmentEntityService;
import com.jeesite.modules.attachment.vo.AttachmentVO;
import com.jeesite.modules.base.constant.BaseConstants;
import com.jeesite.modules.device.util.DeviceImageUtil;
import com.jeesite.modules.device.util.DeviceImportUtil;
import com.jeesite.modules.device.vo.DeviceInfoVO;
import com.jeesite.modules.screen.service.StrapScreenEntityService;
import com.jeesite.modules.screen.vo.StrapScreenVO;
import com.jeesite.modules.station.dao.StationEntityDao;
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.station.service.StationEntityService;
import com.jeesite.modules.strap.service.StrapDetailEntityService;
import com.jeesite.modules.utils.RecognizeDictUtils;
import com.jeesite.modules.utils.VOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.device.dao.DeviceEntityDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备Service
 * @author w
 * @version 2023-08-17
 */
@Service
public class DeviceEntityService extends CrudService<DeviceEntityDao, DeviceEntity> {

	@Autowired
	private RecognizeDictUtils recognizeDictUtils;

	@Autowired
	private StationEntityDao stationEntityDao;

	@Autowired
	private StationEntityService stationEntityService;

	@Autowired
	private BaseAttachmentEntityService attachmentEntityService;

	@Autowired
	private StrapDetailEntityService strapDetailEntityService;

	/**
	 * 导入数据
	 * @param file
	 * @throws IOException
	 */
	public void importDeviceInfo(MultipartFile file)throws IOException {
		List<DeviceInfoVO> importDeviceList = DeviceImportUtil.convertToVO(file);
		List<DeviceInfoVO> result = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(importDeviceList)){
			importDeviceList.forEach(deviceInfoVO -> result.add(addDevice(deviceInfoVO)));
		}

	}

	/**
	 * 新增设备信息
	 * @param deviceInfoVO
	 * @return
	 */
	public DeviceInfoVO addDevice(DeviceInfoVO deviceInfoVO){
		if (deviceInfoVO == null){
			return null;
		}

		if (StringUtils.isNotBlank(deviceInfoVO.getStationName()) && deviceInfoVO.getStationId() == null){
			StationEntity stationEntity = stationEntityDao.findByStationName(deviceInfoVO.getStationName());
			if (stationEntity == null){
				stationEntity = new StationEntity();
				stationEntity.setStationName(deviceInfoVO.getStationName());
				stationEntity.setIsNewRecord(true);
				stationEntityService.save(stationEntity);
			}

			//如果只有站点名称没有站点id，先根据名称查询是否已存在该站点，如果已存在就是用已存在的站点id，若不存在就创建
			deviceInfoVO.setStationId(stationEntity.getStationId());
		}

		DeviceEntity deviceInfoEntity = VOUtil.getVO(DeviceEntity.class, deviceInfoVO);
		deviceInfoEntity.setIsNewRecord(true);
		//保存设备信息，拿到设备id
		save(deviceInfoEntity);

		File image = new File(deviceInfoEntity.getDeviceName() + ".jpg");
		DeviceImageUtil.generateImage(deviceInfoEntity.getDeviceId().toString(), image);
		AttachmentVO attachmentVO = attachmentEntityService.uploadAttachment(image, AttachmentType.DEVICE_IMAGE.getType(), deviceInfoEntity.getDeviceId());

		if (attachmentVO != null && attachmentVO.getTid() != null) {
			deviceInfoEntity.setAttachmentId(attachmentVO.getTid());
			deviceInfoVO.setAttachmentId(attachmentVO.getTid());
			deviceInfoEntity.setIsNewRecord(false);
			save(deviceInfoEntity);
		}

		List<StrapScreenVO> strapScreenVOList = new ArrayList<>();

		//把设备id填充到压板屏幕对象里面，然后保存压板屏幕
		if (CollectionUtils.isNotEmpty(deviceInfoVO.getStrapScreenVOList())){
			deviceInfoVO.getStrapScreenVOList().forEach(strapScreenVO -> {
				strapScreenVO.setDeviceId(deviceInfoEntity.getDeviceId());
				strapScreenVOList.add(strapDetailEntityService.addStrapScreen(strapScreenVO));
			});
		}

		//组装响应数据
		deviceInfoVO.setDeviceId(deviceInfoEntity.getDeviceId());
		deviceInfoVO.setStrapScreenVOList(strapScreenVOList);
		if (deviceInfoVO.getStationId() != null){
			StationEntity stationInfoEntity = stationEntityDao.findByStationId(deviceInfoVO.getStationId());
			if (stationInfoEntity != null){
				deviceInfoVO.setStationName(stationInfoEntity.getStationName());
			}
		}

		fullDeviceInfoVO(deviceInfoVO);

		return deviceInfoVO;
	}


	/**
	 * 组装设备信息:压板统计、二维码地址
	 * @param deviceInfoVO
	 */
	private void fullDeviceInfoVO(DeviceInfoVO deviceInfoVO){
		if (deviceInfoVO == null || CollectionUtils.isEmpty(deviceInfoVO.getStrapScreenVOList())){
			return;
		}

		List<StrapScreenVO> hardScreenList = deviceInfoVO.getStrapScreenVOList().stream().filter(strapScreenVO -> BaseConstants.STRAP_TYPE_HARD.equals(strapScreenVO.getScreenType())).collect(Collectors.toList());
		List<StrapScreenVO> softScreenList = deviceInfoVO.getStrapScreenVOList().stream().filter(strapScreenVO -> BaseConstants.STRAP_TYPE_SOFT.equals(strapScreenVO.getScreenType())).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(hardScreenList)){
			hardScreenList.forEach(hardScreen -> {
				deviceInfoVO.setHardNumber(deviceInfoVO.getHardNumber() + (hardScreen.getStrapNum() == null ? 0 : hardScreen.getStrapNum()));
				deviceInfoVO.setHardColNumber(deviceInfoVO.getHardColNumber() + (hardScreen.getColNum() == null ? 0 : hardScreen.getColNum()));
				deviceInfoVO.setHardRowNumber(deviceInfoVO.getHardRowNumber() + (hardScreen.getRowNum() == null ? 0 : hardScreen.getRowNum()));
			});
		}

		if (CollectionUtils.isNotEmpty(softScreenList)){
			deviceInfoVO.setSoftScreenNumber(softScreenList.size());
			softScreenList.forEach(softScreen -> {
				deviceInfoVO.setSoftNumber(deviceInfoVO.getSoftNumber() + (softScreen.getStrapNum() == null ? 0 : softScreen.getStrapNum()));
			});
		}

		deviceInfoVO.setImageUrl();

	}
	
	/**
	 * 获取单条数据
	 * @param deviceEntity
	 * @return
	 */
	@Override
	public DeviceEntity get(DeviceEntity deviceEntity) {
		DeviceEntity entity = super.get(deviceEntity);
		setStationName(Arrays.asList(entity));
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param deviceEntity 查询条件
	 * @param deviceEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<DeviceEntity> findPage(DeviceEntity deviceEntity) {
		Page<DeviceEntity> page = super.findPage(deviceEntity);
		setStationName(page.getList());
		return page;
	}
	
	/**
	 * 查询列表数据
	 * @param deviceEntity
	 * @return
	 */
	@Override
	public List<DeviceEntity> findList(DeviceEntity deviceEntity) {
		List<DeviceEntity> list = super.findList(deviceEntity);
		setStationName(list);
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param deviceEntity
	 */
	@Override
	@Transactional
	public void save(DeviceEntity deviceEntity) {
		super.save(deviceEntity);
		recognizeDictUtils.updateStationMap();
	}
	
	/**
	 * 更新状态
	 * @param deviceEntity
	 */
	@Override
	@Transactional
	public void updateStatus(DeviceEntity deviceEntity) {
		super.updateStatus(deviceEntity);
	}
	
	/**
	 * 删除数据
	 * @param deviceEntity
	 */
	@Override
	@Transactional
	public void delete(DeviceEntity deviceEntity) {
		super.delete(deviceEntity);
		recognizeDictUtils.updateStationMap();
	}

	private void setStationName(List<DeviceEntity> deviceEntities){
		Map<Long, String> stationNameMap = recognizeDictUtils.getStationNameMap();
		if (CollectionUtils.isNotEmpty(deviceEntities)){
			deviceEntities.forEach(deviceEntity -> deviceEntity.setStationName(stationNameMap.get(deviceEntity.getStationId())));
		}
	}
	
}