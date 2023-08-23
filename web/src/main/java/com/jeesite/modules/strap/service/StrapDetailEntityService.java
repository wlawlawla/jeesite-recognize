package com.jeesite.modules.strap.service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jeesite.modules.base.constant.BaseConstants;
import com.jeesite.modules.base.service.BaseConstantEntityService;
import com.jeesite.modules.base.vo.ConstantVO;
import com.jeesite.modules.device.dao.DeviceEntityDao;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.device.service.DeviceEntityService;
import com.jeesite.modules.screen.dao.StrapScreenEntityDao;
import com.jeesite.modules.screen.entity.StrapScreenEntity;
import com.jeesite.modules.screen.service.StrapScreenEntityService;
import com.jeesite.modules.screen.vo.StrapScreenVO;
import com.jeesite.modules.station.dao.StationEntityDao;
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.strap.vo.StrapDetailVO;
import com.jeesite.modules.utils.VOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.strap.entity.StrapDetailEntity;
import com.jeesite.modules.strap.dao.StrapDetailEntityDao;

import javax.annotation.PostConstruct;

/**
 * 压板Service
 * @author w
 * @version 2023-08-21
 */
@Service
public class StrapDetailEntityService extends CrudService<StrapDetailEntityDao, StrapDetailEntity> {

	@Autowired
	private StrapScreenEntityDao strapScreenEntityDao;
	@Autowired
	private StrapScreenEntityService strapScreenEntityService;
	@Autowired
	private DeviceEntityDao deviceEntityDao;
	@Autowired
	private StationEntityDao stationEntityDao;

	@Autowired
	private BaseConstantEntityService constantService;


	Map<String, String> strapValueMap;
	Map<String, String> softValueMap;
	Map<String, String> softCodeMap;
	Map<String, String> strapCodeMap;

	@PostConstruct
	private void init(){
		List<ConstantVO> strapTypeList = constantService.getConstantByType(BaseConstants.CONSTANT_TYPE_STRAP);
		List<ConstantVO> softTypeList = constantService.getConstantByType(BaseConstants.CONSTANT_TYPE_SOFT);

		strapValueMap = strapTypeList.stream().collect(Collectors.toMap(ConstantVO::getCvalue, constantVO -> constantVO.getCcode()));
		softValueMap = softTypeList.stream().collect(Collectors.toMap(ConstantVO::getCvalue, constantVO -> constantVO.getCcode()));
		strapCodeMap = strapTypeList.stream().collect(Collectors.toMap(constantVO -> constantVO.getCcode(), ConstantVO::getCvalue));
		softCodeMap = softTypeList.stream().collect(Collectors.toMap(constantVO -> constantVO.getCcode(), ConstantVO::getCvalue));
	}

	public StrapScreenVO getStrapScreenInfo(Long screenId){
		if (screenId == null){
			return null;
		}

		List<StrapScreenEntity> screenEntityList = strapScreenEntityDao.findByScreenIdIn(Arrays.asList(screenId));
		if (CollectionUtils.isEmpty(screenEntityList)){
			return null;
		}

		List<StrapScreenVO> strapScreenVOList = getScreenVOList(screenEntityList, true);
		if (CollectionUtils.isEmpty(strapScreenVOList)){
			return VOUtil.getVO(StrapScreenVO.class, screenEntityList.get(0));
		}

		return strapScreenVOList.get(0);

	}

	/**
	 * 转喊成vo列表
	 * @param strapScreenEntityList
	 * @param needDetail
	 * @return
	 */
	private List<StrapScreenVO> getScreenVOList(List<StrapScreenEntity> strapScreenEntityList, boolean needDetail){
		if (CollectionUtils.isEmpty(strapScreenEntityList)){
			return Collections.emptyList();
		}

		List<StrapScreenVO> strapScreenVOList = VOUtil.getVOList(StrapScreenVO.class, strapScreenEntityList);

		DeviceEntity deviceInfoEntity = deviceEntityDao.findById(strapScreenEntityList.get(0).getDeviceId());
		StationEntity stationInfoEntity = null;
		if (deviceInfoEntity != null) {
			stationInfoEntity = stationEntityDao.findByStationId(deviceInfoEntity.getStationId());
		}
		String stationName = stationInfoEntity == null ? "" : stationInfoEntity.getStationName();
		strapScreenVOList.forEach(strapScreenVO -> {
			//组装设备名称，站点名称，压板屏幕类型，软压板类型
			if (deviceInfoEntity != null) {
				strapScreenVO.setDeviceName(deviceInfoEntity.getDeviceName());
			}
			strapScreenVO.setStationName(stationName);
			if (strapScreenVO.getScreenType() != null) {
				strapScreenVO.setScreenTypeStr(strapCodeMap.get(strapScreenVO.getScreenType()));
			}
			if (strapScreenVO.getSoftType() != null) {
				strapScreenVO.setSoftTypeStr(softCodeMap.get(strapScreenVO.getSoftType()));
			}
			strapScreenVO.setImageUrl();

			//如果需要返回压板详情，再去查询
			if (needDetail){
				strapScreenVO.setStrapDetailVOList(getStrapDetailByScreenId(strapScreenVO.getScreenId()));
			}

		});

		return strapScreenVOList;
	}

	public List<StrapDetailVO> getStrapDetailByScreenId(Long screenId){
		if (screenId == null){
			return Collections.emptyList();
		}

		return getStrapDetailVOList(dao.findByScreenId(screenId));
	}


	/**
	 * 转换成vo对象，并填充压板屏名称、设备名称、站点名称
	 * @param strapDetailEntityList
	 * @return
	 */
	private List<StrapDetailVO> getStrapDetailVOList(List<StrapDetailEntity> strapDetailEntityList){

		List<StrapDetailVO> strapDetailVOList = VOUtil.getVOList(StrapDetailVO.class, strapDetailEntityList);

		if (CollectionUtils.isEmpty(strapDetailVOList)){
			return Collections.emptyList();
		}

		List<Long> screenIdList = strapDetailEntityList.stream().map(StrapDetailEntity::getScreenId).filter(Objects::nonNull).distinct().collect(Collectors.toList());

		if (CollectionUtils.isEmpty(screenIdList)){
			return strapDetailVOList;
		}

		Map<Long, StrapScreenEntity> screenMap = new HashMap<>();
		Map<Long, DeviceEntity> deviceMap = new HashMap<>();
		Map<Long, StationEntity> stationMap = new HashMap<>();

		List<Long> deviceIdList = new ArrayList<>();
		List<Long> stationIdList = new ArrayList<>();

		List<StrapScreenEntity> strapScreenEntityList = strapScreenEntityDao.findByScreenIdIn(screenIdList);

		//逐级查询：压板屏-设备-站点
		if (CollectionUtils.isNotEmpty(strapScreenEntityList)) {
			screenMap.putAll(strapScreenEntityList.stream().collect(Collectors.toMap(StrapScreenEntity::getScreenId, strapScreenEntity -> strapScreenEntity)));
			deviceIdList.addAll(strapScreenEntityList.stream().map(StrapScreenEntity::getDeviceId).collect(Collectors.toList()));
		}

		if (CollectionUtils.isNotEmpty(deviceIdList)){
			List<DeviceEntity> deviceList = deviceEntityDao.findByDeviceIdIn(deviceIdList);
			if (CollectionUtils.isNotEmpty(deviceList)){
				deviceMap.putAll(deviceList.stream().collect(Collectors.toMap(DeviceEntity::getDeviceId, deviceInfoEntity -> deviceInfoEntity)));
				stationIdList.addAll(deviceList.stream().map(DeviceEntity::getStationId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
			}
		}

		if (CollectionUtils.isNotEmpty(stationIdList)){
			List<StationEntity> stationInfoEntityList = stationEntityDao.findByStationIdIn(stationIdList);
			if (CollectionUtils.isNotEmpty(stationInfoEntityList)){
				stationMap.putAll(stationInfoEntityList.stream().collect(Collectors.toMap(StationEntity::getStationId, stationInfoEntity -> stationInfoEntity)));
			}
		}

		//逐级组装压板屏，设备，站点名称
		strapDetailVOList.forEach(strapDetailVO -> {
			StrapScreenEntity screenEntity = screenMap.get(strapDetailVO.getScreenId());
			if (screenEntity != null){
				strapDetailVO.setScreenName(screenEntity.getScreenName());

				DeviceEntity deviceInfoEntity = deviceMap.get(screenEntity.getDeviceId());
				if (deviceInfoEntity != null){
					strapDetailVO.setDeviceName(deviceInfoEntity.getDeviceName());

					StationEntity stationInfoEntity = stationMap.get(deviceInfoEntity.getStationId());
					if (stationInfoEntity != null){
						strapDetailVO.setStationName(stationInfoEntity.getStationName());
					}
				}
			}
		});

		return strapDetailVOList;
	}

	/**
	 * 获取单条数据
	 * @param strapDetailEntity
	 * @return
	 */
	@Override
	public StrapDetailEntity get(StrapDetailEntity strapDetailEntity) {
		StrapDetailEntity strapDetailEntity1 = super.get(strapDetailEntity);
		setScreenName(Arrays.asList(strapDetailEntity1));
		return strapDetailEntity1;
	}
	
	/**
	 * 查询分页数据
	 * @param strapDetailEntity 查询条件
	 * @param strapDetailEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<StrapDetailEntity> findPage(StrapDetailEntity strapDetailEntity) {
		Page<StrapDetailEntity> page = super.findPage(strapDetailEntity);
		List<StrapDetailEntity> list = page.getList();
		setScreenName(list);
		return page;
	}
	
	/**
	 * 查询列表数据
	 * @param strapDetailEntity
	 * @return
	 */
	@Override
	public List<StrapDetailEntity> findList(StrapDetailEntity strapDetailEntity) {
		List<StrapDetailEntity> list = super.findList(strapDetailEntity);
		setScreenName(list);
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param strapDetailEntity
	 */
	@Override
	@Transactional
	public void save(StrapDetailEntity strapDetailEntity) {
		super.save(strapDetailEntity);
	}
	
	/**
	 * 更新状态
	 * @param strapDetailEntity
	 */
	@Override
	@Transactional
	public void updateStatus(StrapDetailEntity strapDetailEntity) {
		super.updateStatus(strapDetailEntity);
	}
	
	/**
	 * 删除数据
	 * @param strapDetailEntity
	 */
	@Override
	@Transactional
	public void delete(StrapDetailEntity strapDetailEntity) {
		super.delete(strapDetailEntity);
	}

	private void setScreenName(List<StrapDetailEntity> list){
		if (CollectionUtils.isNotEmpty(list)){
			List<Long> screenIdList = list.stream().map(StrapDetailEntity::getScreenId).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(screenIdList)){
				List<StrapScreenEntity> screenList = strapScreenEntityDao.findByScreenIdIn(screenIdList);
				if (CollectionUtils.isNotEmpty(screenList)){
					Map<Long, String> screenMap = screenList.stream().collect(Collectors.toMap(StrapScreenEntity::getScreenId, StrapScreenEntity::getScreenName));
					list.forEach(strap -> strap.setScreenName(screenMap.get(strap.getScreenId())));
				}
			}
		}
	}

	public StrapScreenVO addStrapScreen(StrapScreenVO strapScreenVO){
		if (strapScreenVO == null){
			return null;
		}

		if (StringUtils.isNotBlank(strapScreenVO.getScreenTypeStr())){
			strapScreenVO.setScreenType(strapValueMap.get(strapScreenVO.getScreenTypeStr()));
		}

		if (StringUtils.isNotBlank(strapScreenVO.getSoftTypeStr())){
			strapScreenVO.setSoftType(softValueMap.get(strapScreenVO.getSoftTypeStr()));
		}

		StrapScreenEntity strapScreenEntity = VOUtil.getVO(StrapScreenEntity.class, strapScreenVO);
		strapScreenEntity.setIsNewRecord(true);
		strapScreenEntityService.save(strapScreenEntity);
		strapScreenVO.setScreenId(strapScreenEntity.getScreenId());


		if (CollectionUtils.isNotEmpty(strapScreenVO.getStrapDetailVOList())){
			//保存压板详细信息
			strapScreenVO.setStrapDetailVOList(addStrapDetail(strapScreenVO.getStrapDetailVOList(), strapScreenEntity.getScreenId()));
		}

		//组装响应数据：设备名称、站点名称
		if (strapScreenVO.getDeviceId() != null){
			DeviceEntity deviceInfoEntity = deviceEntityDao.findById(strapScreenVO.getDeviceId());
			if (deviceInfoEntity != null){
				strapScreenVO.setDeviceName(deviceInfoEntity.getDeviceName());

				StationEntity stationInfoEntity = stationEntityDao.findByStationId(deviceInfoEntity.getStationId());
				if (stationInfoEntity != null){
					strapScreenVO.setStationName(stationInfoEntity.getStationName());
				}
			}
		}

		return strapScreenVO;
	}

	/**
	 * 批量保存压板信息
	 * @param strapDetailVOList
	 * @param screenId
	 * @return
	 */
	private List<StrapDetailVO> addStrapDetail(List<StrapDetailVO> strapDetailVOList, Long screenId){
		if (CollectionUtils.isEmpty(strapDetailVOList)){
			return Collections.EMPTY_LIST;
		}

		List<StrapDetailEntity> strapDetailEntityList = VOUtil.getVOList(StrapDetailEntity.class, strapDetailVOList);
		strapDetailEntityList.forEach(strapDetailEntity -> {
			if (screenId != null){
				strapDetailEntity.setScreenId(screenId);
				strapDetailEntity.setIsNewRecord(true);
				save(strapDetailEntity);
			}
		});

		return getStrapDetailVOList(strapDetailEntityList);

	}
}