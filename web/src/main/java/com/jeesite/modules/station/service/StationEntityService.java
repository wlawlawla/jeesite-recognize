package com.jeesite.modules.station.service;

import java.io.File;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeesite.modules.attachment.common.AttachmentType;
import com.jeesite.modules.attachment.service.BaseAttachmentEntityService;
import com.jeesite.modules.attachment.vo.AttachmentVO;
import com.jeesite.modules.base.constant.BaseConstants;
import com.jeesite.modules.device.util.DeviceImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.station.dao.StationEntityDao;

/**
 * station_infoService
 * @author w
 * @version 2023-08-18
 */
@Service
public class StationEntityService extends CrudService<StationEntityDao, StationEntity> {

	@Autowired
	private BaseAttachmentEntityService attachmentEntityService;
	
	/**
	 * 获取单条数据
	 * @param stationEntity
	 * @return
	 */
	@Override
	public StationEntity get(StationEntity stationEntity) {
		StationEntity entity = super.get(stationEntity);
		setImageUrl(entity);
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param stationEntity 查询条件
	 * @param stationEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<StationEntity> findPage(StationEntity stationEntity) {
		Page<StationEntity> page = super.findPage(stationEntity);
		List<StationEntity> list = page.getList();
		if (CollectionUtils.isNotEmpty(list)){
			list.forEach(entity -> setImageUrl(entity));
		}
		return page;
	}
	
	/**
	 * 查询列表数据
	 * @param stationEntity
	 * @return
	 */
	@Override
	public List<StationEntity> findList(StationEntity stationEntity) {
		List<StationEntity> list = super.findList(stationEntity);
		if (CollectionUtils.isNotEmpty(list)){
			list.forEach(entity -> setImageUrl(entity));
		}
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param stationEntity
	 */
	@Override
	@Transactional
	public void save(StationEntity stationEntity) {
		if (stationEntity.getStationId() != null){
			StationEntity dbStation = dao.findByStationId(stationEntity.getStationId());
			if (dbStation != null){
				stationEntity.setAttachmentId(dbStation.getAttachmentId());
			}
		}
		super.save(stationEntity);
		if (stationEntity.getAttachmentId() == null){
			//生成站所二维码
			File image = new File(stationEntity.getStationName() + ".jpg");
			DeviceImageUtil.generateImage(stationEntity.getStationId().toString(), image);
			AttachmentVO attachmentVO = attachmentEntityService.uploadAttachment(image, AttachmentType.STATION_IMAGE.getType(), stationEntity.getStationId());

			if (attachmentVO != null && attachmentVO.getTid() != null) {
				stationEntity.setAttachmentId(attachmentVO.getTid());
				stationEntity.setIsNewRecord(false);
				super.save(stationEntity);
			}
		}

	}
	
	/**
	 * 更新状态
	 * @param stationEntity
	 */
	@Override
	@Transactional
	public void updateStatus(StationEntity stationEntity) {
		super.updateStatus(stationEntity);
	}
	
	/**
	 * 删除数据
	 * @param stationEntity
	 */
	@Override
	@Transactional
	public void delete(StationEntity stationEntity) {
		super.delete(stationEntity);
	}

	private void setImageUrl(StationEntity stationEntity){
		if (stationEntity != null && stationEntity.getAttachmentId() != null){
			stationEntity.setImageUrl(BaseConstants.IMAGE_URL_PREFIX + stationEntity.getAttachmentId());
		}
	}
	
}