package com.jeesite.modules.station.service;

import java.util.List;
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
	
	/**
	 * 获取单条数据
	 * @param stationEntity
	 * @return
	 */
	@Override
	public StationEntity get(StationEntity stationEntity) {
		return super.get(stationEntity);
	}
	
	/**
	 * 查询分页数据
	 * @param stationEntity 查询条件
	 * @param stationEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<StationEntity> findPage(StationEntity stationEntity) {
		return super.findPage(stationEntity);
	}
	
	/**
	 * 查询列表数据
	 * @param stationEntity
	 * @return
	 */
	@Override
	public List<StationEntity> findList(StationEntity stationEntity) {
		return super.findList(stationEntity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param stationEntity
	 */
	@Override
	@Transactional
	public void save(StationEntity stationEntity) {
		super.save(stationEntity);
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
	
}