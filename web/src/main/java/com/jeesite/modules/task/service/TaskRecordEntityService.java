package com.jeesite.modules.task.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.task.entity.TaskRecordEntity;
import com.jeesite.modules.task.dao.TaskRecordEntityDao;

/**
 * task_recordService
 * @author w
 * @version 2023-08-22
 */
@Service
public class TaskRecordEntityService extends CrudService<TaskRecordEntityDao, TaskRecordEntity> {
	
	/**
	 * 获取单条数据
	 * @param taskRecordEntity
	 * @return
	 */
	@Override
	public TaskRecordEntity get(TaskRecordEntity taskRecordEntity) {
		return super.get(taskRecordEntity);
	}
	
	/**
	 * 查询分页数据
	 * @param taskRecordEntity 查询条件
	 * @param taskRecordEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskRecordEntity> findPage(TaskRecordEntity taskRecordEntity) {
		return super.findPage(taskRecordEntity);
	}
	
	/**
	 * 查询列表数据
	 * @param taskRecordEntity
	 * @return
	 */
	@Override
	public List<TaskRecordEntity> findList(TaskRecordEntity taskRecordEntity) {
		return super.findList(taskRecordEntity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taskRecordEntity
	 */
	@Override
	@Transactional
	public void save(TaskRecordEntity taskRecordEntity) {
		super.save(taskRecordEntity);
	}
	
	/**
	 * 更新状态
	 * @param taskRecordEntity
	 */
	@Override
	@Transactional
	public void updateStatus(TaskRecordEntity taskRecordEntity) {
		super.updateStatus(taskRecordEntity);
	}
	
	/**
	 * 删除数据
	 * @param taskRecordEntity
	 */
	@Override
	@Transactional
	public void delete(TaskRecordEntity taskRecordEntity) {
		super.delete(taskRecordEntity);
	}
	
}