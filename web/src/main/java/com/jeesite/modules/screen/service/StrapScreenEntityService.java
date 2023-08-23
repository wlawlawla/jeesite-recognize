package com.jeesite.modules.screen.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeesite.modules.device.dao.DeviceEntityDao;
import com.jeesite.modules.device.entity.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.screen.entity.StrapScreenEntity;
import com.jeesite.modules.screen.dao.StrapScreenEntityDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * strap_screenService
 * @author w
 * @version 2023-08-18
 */
@Service
public class StrapScreenEntityService extends CrudService<StrapScreenEntityDao, StrapScreenEntity> {

	@Autowired
	private DeviceEntityDao deviceEntityDao;
	
	/**
	 * 获取单条数据
	 * @param strapScreenEntity
	 * @return
	 */
	@Override
	public StrapScreenEntity get(StrapScreenEntity strapScreenEntity) {
		StrapScreenEntity screenEntity = super.get(strapScreenEntity);
		setDeviceName(Arrays.asList(screenEntity));
		return screenEntity;
	}
	
	/**
	 * 查询分页数据
	 * @param strapScreenEntity 查询条件
	 * @param strapScreenEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<StrapScreenEntity> findPage(StrapScreenEntity strapScreenEntity) {
		Page<StrapScreenEntity> page = super.findPage(strapScreenEntity);
		List<StrapScreenEntity> list = page.getList();
		setDeviceName(list);
		return page;
	}
	
	/**
	 * 查询列表数据
	 * @param strapScreenEntity
	 * @return
	 */
	@Override
	public List<StrapScreenEntity> findList(StrapScreenEntity strapScreenEntity) {
		List<StrapScreenEntity> list = super.findList(strapScreenEntity);
		setDeviceName(list);
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param strapScreenEntity
	 */
	@Override
	@Transactional
	public void save(StrapScreenEntity strapScreenEntity) {
		super.save(strapScreenEntity);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(strapScreenEntity, strapScreenEntity.getId(), "strapScreenEntity_image");
	}
	
	/**
	 * 更新状态
	 * @param strapScreenEntity
	 */
	@Override
	@Transactional
	public void updateStatus(StrapScreenEntity strapScreenEntity) {
		super.updateStatus(strapScreenEntity);
	}
	
	/**
	 * 删除数据
	 * @param strapScreenEntity
	 */
	@Override
	@Transactional
	public void delete(StrapScreenEntity strapScreenEntity) {
		super.delete(strapScreenEntity);
	}

	/**
	 * 填充设备名称
	 * @param list
	 */
	private void setDeviceName(List<StrapScreenEntity> list){
		if (CollectionUtils.isNotEmpty(list)){
			List<Long> deviceIds = list.stream().map(StrapScreenEntity::getDeviceId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(deviceIds)){
				List<DeviceEntity> deviceEntities = deviceEntityDao.findByDeviceIdIn(deviceIds);
				if (CollectionUtils.isNotEmpty(deviceEntities)){
					Map<Long, String> deviceMap = deviceEntities.stream().collect(Collectors.toMap(DeviceEntity::getDeviceId, DeviceEntity::getDeviceName));
					list.forEach(screen -> screen.setDeviceNameStr(deviceMap.get(screen.getDeviceId())));
				}
			}
		}
	}


	
}