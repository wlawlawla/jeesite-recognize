package com.jeesite.modules.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jeesite.modules.device.dao.DeviceEntityDao;
import com.jeesite.modules.device.service.DeviceEntityService;
import com.jeesite.modules.station.dao.StationEntityDao;
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.station.service.StationEntityService;
import com.jeesite.modules.sys.dao.EmpUserDao;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.task.constant.RelationType;
import com.jeesite.modules.task.constant.TaskRecordType;
import com.jeesite.modules.task.dao.TaskRecordEntityDao;
import com.jeesite.modules.task.dao.TaskRelationEntityDao;
import com.jeesite.modules.task.dto.TaskInfoDto;
import com.jeesite.modules.task.entity.TaskRecordEntity;
import com.jeesite.modules.task.entity.TaskRelationEntity;
import com.jeesite.modules.task.vo.TaskInfoVO;
import com.jeesite.modules.utils.DateUtils;
import com.jeesite.modules.utils.JsonUtil;
import com.jeesite.modules.utils.VOUtil;
import javafx.concurrent.Task;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.task.entity.TaskDetailEntity;
import com.jeesite.modules.task.dao.TaskDetailEntityDao;

/**
 * 任务Service
 * @author w
 * @version 2023-08-21
 */
@Service
public class TaskDetailEntityService extends CrudService<TaskDetailEntityDao, TaskDetailEntity> {

	@Autowired
	private DeviceEntityDao deviceEntityDao;

	@Autowired
	private StationEntityDao stationEntityDao;

	@Autowired
	private TaskRelationEntityDao taskRelationEntityDao;

	@Autowired
	private TaskRelationEntityService taskRelationEntityService;

	@Autowired
	private EmpUserDao empUserDao;

	@Autowired
	private TaskRecordEntityService taskRecordEntityService;

	
	/**
	 * 获取单条数据
	 * @param taskDetailEntity
	 * @return
	 */
	@Override
	public TaskDetailEntity get(TaskDetailEntity taskDetailEntity) {
		TaskDetailEntity taskDetailEntity1 = super.get(taskDetailEntity);
		TaskInfoVO taskInfoVO = getTaskInfoVO(taskDetailEntity);
		setIdsToEntity(taskInfoVO, taskDetailEntity1);
		return taskDetailEntity1;
	}

	private void setIdsToEntity(TaskInfoVO taskInfoVO, TaskDetailEntity taskDetailEntity){
		if (taskInfoVO == null || taskDetailEntity == null){
			return;
		}
		taskDetailEntity.setDirectors(taskInfoVO.getDirectors());
		taskDetailEntity.setWorkers(taskInfoVO.getWorkers());
		taskDetailEntity.setDevices(taskInfoVO.getDevices());
		taskDetailEntity.setDirectorIds(taskInfoVO.getDirectorIds());
		taskDetailEntity.setWorkerIds(taskInfoVO.getWorkerIds());
		taskDetailEntity.setDeviceIds(taskInfoVO.getDeviceIds());
		taskDetailEntity.setStationName(taskInfoVO.getStationName());

	}
	
	/**
	 * 查询分页数据
	 * @param taskDetailEntity 查询条件
	 * @param taskDetailEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskDetailEntity> findPage(TaskDetailEntity taskDetailEntity) {
		return super.findPage(taskDetailEntity);
	}

	public Page<TaskInfoVO> findPageVO(TaskDetailEntity taskDetailEntity) {
		Page<TaskDetailEntity> pageEntity = super.findPage(taskDetailEntity);
		Page<TaskInfoVO> result = new Page<>();
		result.setPageNo(pageEntity.getPageNo());
		result.setPageSize(pageEntity.getPageSize());
		result.setCount(pageEntity.getCount());
		List<TaskInfoVO> taskInfoVOS = new ArrayList<>();
		result.setList(taskInfoVOS);
		fullTaskVOList(taskInfoVOS, pageEntity.getList());
		return result;
	}
	
	/**
	 * 查询列表数据
	 * @param taskDetailEntity
	 * @return
	 */
	@Override
	public List<TaskDetailEntity> findList(TaskDetailEntity taskDetailEntity) {
		List<TaskDetailEntity> list = super.findList(taskDetailEntity);
		List<TaskInfoVO> taskInfoVOS = new ArrayList<>();
		fullTaskVOList(taskInfoVOS, list);
		Map<Long, TaskInfoVO> voMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)){
			voMap.putAll(taskInfoVOS.stream().collect(Collectors.toMap(TaskInfoVO::getTaskId, taskInfoVO -> taskInfoVO)));
			list.forEach(entity ->{
				TaskInfoVO taskInfoVO = voMap.get(entity.getTaskId());
				setIdsToEntity(taskInfoVO, entity);
			});
		}
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taskDetailEntity
	 */
	@Override
	@Transactional
	public void save(TaskDetailEntity taskDetailEntity) {
		super.save(taskDetailEntity);
	}

	@Transactional
	public void saveDto(TaskInfoDto taskInfoDto){
		if (taskInfoDto == null){
			return;
		}

		TaskDetailEntity taskDetailEntity = VOUtil.getVO(TaskDetailEntity.class, taskInfoDto);
		if (taskInfoDto.getTaskId() != null){
			TaskDetailEntity dbTaskDetail = dao.findByTaskId(taskInfoDto.getTaskId());
			if (dbTaskDetail != null){
				taskDetailEntity.setStartTime(dbTaskDetail.getStartTime());
				save(taskDetailEntity);
			}
			taskRelationEntityDao.deleteByTaskId(taskInfoDto.getTaskId());
		}else {
			User user = UserUtils.getUser();
			if (user != null){
				taskDetailEntity.setCreateUser(user.getUserCode());
			}
			taskDetailEntity.setStartTime(new Date());
			if (StringUtils.isBlank(taskDetailEntity.getTaskName())){
				taskDetailEntity.setTaskName(DateUtils.getNumberStrDate(LocalDate.now()) + "_" + taskDetailEntity.getTaskType());
			}
			//首次创建任务，生成编号，并且任务状态默认为"巡检中"
			taskDetailEntity.setTaskNum(DateUtils.NUMBER_DATE_TIME_FORMAT.format(LocalDateTime.now()));
			taskDetailEntity.setTaskStatus("doing");
			save(taskDetailEntity);
		}

		List<TaskRelationEntity> taskRelationEntityList = new ArrayList<>();

		//分别保存设备、负责人、工作组成员关联信息
		if (CollectionUtils.isNotEmpty(taskInfoDto.getDeviceIds())){
			taskInfoDto.getDeviceIds().forEach(deviceId -> taskRelationEntityService.save(new TaskRelationEntity(RelationType.DEVICE.getType(), taskDetailEntity.getTaskId(), deviceId.toString())));
		}

		if (CollectionUtils.isNotEmpty(taskInfoDto.getDirectorIds())){
			taskInfoDto.getDirectorIds().forEach(directorId -> taskRelationEntityService.save(new TaskRelationEntity(RelationType.DIRECTOR_USER.getType(), taskDetailEntity.getTaskId(), directorId.toString())));
		}

		if (CollectionUtils.isNotEmpty(taskInfoDto.getWorkerIds())){
			taskInfoDto.getWorkerIds().forEach(workerId -> taskRelationEntityService.save(new TaskRelationEntity(RelationType.WORKER_USER.getType(), taskDetailEntity.getTaskId(), workerId.toString())));
		}

		TaskInfoVO taskInfoVO =  getTaskInfoVO(taskDetailEntity);

		saveTaskRecord(null, taskDetailEntity.getTaskId(), TaskRecordType.TASK_INFO.getName(), taskInfoVO);


	}

	public TaskInfoVO getTaskInfo(Long taskId){
		if (taskId == null){
			return null;
		}

		TaskDetailEntity taskDetailEntity = dao.findByTaskId(taskId);

		if (taskDetailEntity == null){
			return null;
		}

		return getTaskInfoVO(taskDetailEntity);

	}
	/**
	 * 批量转换
	 * @param taskInfoVOList
	 * @param taskDetailEntityList
	 */
	private void fullTaskVOList(List<TaskInfoVO> taskInfoVOList, List<TaskDetailEntity> taskDetailEntityList){
		if (CollectionUtils.isEmpty(taskDetailEntityList)){
			return;
		}
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CompletableFuture[] completableFutures =
				taskDetailEntityList.stream().map(taskDetailEntity -> CompletableFuture.runAsync(() -> taskInfoVOList.add(getTaskInfoVO(taskDetailEntity)), executorService)).toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(completableFutures).join();
		executorService.shutdown();

	}
	/**
	 * 获取vo对象
	 * @param taskDetailEntity
	 * @return
	 */
	public TaskInfoVO getTaskInfoVO(TaskDetailEntity taskDetailEntity){
		if (taskDetailEntity == null){
			return null;
		}

		TaskInfoVO taskInfoVO = VOUtil.getVO(TaskInfoVO.class, taskDetailEntity);

		//taskInfoVO.setStatus(statusCodeMap.get(taskInfoVO.getStatus()));
		//站点填充
		CompletableFuture stationCompletableFuture = CompletableFuture.runAsync(() -> {
					StationEntity station = stationEntityDao.findByStationId(taskInfoVO.getStationId());
					if (station != null) {
						taskInfoVO.setStationName(station.getStationName());
					}
				}
				, Executors.newSingleThreadExecutor());
		CompletableFuture createUserCompletableFuture = CompletableFuture.runAsync(() -> {
					if (StringUtils.isNotBlank(taskInfoVO.getCreateUser())) {
						List<EmpUser> users = empUserDao.findUserListByIdIn(Arrays.asList(taskInfoVO.getCreateUser()));
						if (CollectionUtils.isNotEmpty(users)) {
							taskInfoVO.setCreateUserName(users.get(0).getUserName());
						}
					}
				}
				, Executors.newSingleThreadExecutor());

		List<TaskRelationEntity> taskRelationEntityList = taskRelationEntityDao.findByTaskId(taskDetailEntity.getTaskId());
		if (CollectionUtils.isNotEmpty(taskRelationEntityList)){
			//工作负责人填充
			CompletableFuture directorCompletableFuture = CompletableFuture.runAsync(() ->{
						List<String> userCodes = taskRelationEntityList.stream()
								.filter(relation -> RelationType.DIRECTOR_USER.getType().equals(relation.getRelationType()))
								.map(TaskRelationEntity::getRelationId).collect(Collectors.toList());
						taskInfoVO.setDirectorIds(userCodes);
						List<EmpUser> users = empUserDao.findUserListByIdIn(userCodes);
						taskInfoVO.setDirectors(users);
						if (CollectionUtils.isNotEmpty(users)){
							taskInfoVO.setDirectorNames(users.stream().map(EmpUser::getUserName).collect(Collectors.joining(",")));
						}
					}
					, Executors.newSingleThreadExecutor());


			//工作组成员填充
			CompletableFuture workerCompletableFuture = CompletableFuture.runAsync(() ->{
						List<String> userCodes = taskRelationEntityList.stream()
								.filter(relation -> RelationType.WORKER_USER.getType().equals(relation.getRelationType()))
								.map(TaskRelationEntity::getRelationId).collect(Collectors.toList());
						taskInfoVO.setWorkerIds(userCodes);
						List<EmpUser> users = empUserDao.findUserListByIdIn(userCodes);
						taskInfoVO.setWorkers(users);
						if (CollectionUtils.isNotEmpty(users)){
							taskInfoVO.setWorkerNames(users.stream().map(EmpUser::getUserName).collect(Collectors.joining(",")));
						}
					}
					, Executors.newSingleThreadExecutor());

			//设备列表填充
			CompletableFuture deviceCompletableFuture = CompletableFuture.runAsync(() ->{
						List<Long> deviceIds = taskRelationEntityList.stream()
								.filter(relation -> RelationType.DEVICE.getType().equals(relation.getRelationType()))
								.map(taskRelationEntity -> Long.valueOf(taskRelationEntity.getRelationId())).collect(Collectors.toList());
						taskInfoVO.setDevices(deviceEntityDao.findByDeviceIdIn(deviceIds));
						taskInfoVO.setDeviceIds(deviceIds);
						}
					, Executors.newSingleThreadExecutor());

			CompletableFuture.allOf(directorCompletableFuture, workerCompletableFuture, deviceCompletableFuture).join();
		}
		CompletableFuture.allOf(stationCompletableFuture, createUserCompletableFuture).join();
		return taskInfoVO;
	}


	/**
	 * 保存任务记录信息
	 * @param id
	 * @param taskId
	 * @param name
	 * @param t
	 * @param <T>
	 */
	private <T> void saveTaskRecord(Long id, Long taskId, String name, T t){
		if (t == null){
			return;
		}
		CompletableFuture.runAsync(() -> {
			TaskRecordEntity taskRecordEntity = new TaskRecordEntity();
			taskRecordEntity.setTaskId(taskId);
			taskRecordEntity.setColumnName(name);
			taskRecordEntity.setContext(JsonUtil.toJsonString(t));
			taskRecordEntity.setTid(id);
			if (id == null){
				taskRecordEntity.setIsNewRecord(true);
			}else {
				taskRecordEntity.setIsNewRecord(false);
			}
			taskRecordEntityService.save(taskRecordEntity);
		}, Executors.newSingleThreadExecutor());

	}
	
	/**
	 * 更新状态
	 * @param taskDetailEntity
	 */
	@Override
	@Transactional
	public void updateStatus(TaskDetailEntity taskDetailEntity) {
		super.updateStatus(taskDetailEntity);
	}
	
	/**
	 * 删除数据
	 * @param taskDetailEntity
	 */
	@Override
	@Transactional
	public void delete(TaskDetailEntity taskDetailEntity) {
		super.delete(taskDetailEntity);
	}

	public List<TaskInfoVO> getCurrentTaskList(Integer relationType){
		List<TaskInfoVO> taskInfoVOS = new ArrayList<>();
		User user = UserUtils.getUser();
		if (user == null){
			return taskInfoVOS;
		}
		List<TaskDetailEntity> currentTask = dao.getCurrentTask(user.getUserCode(), relationType);
		if (CollectionUtils.isNotEmpty(currentTask)){
			fullTaskVOList(taskInfoVOS, currentTask);
		}
		return taskInfoVOS;
	}
}