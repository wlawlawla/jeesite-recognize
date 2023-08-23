package com.jeesite.modules.task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.sys.entity.EmpUser;

/**
 * 任务Entity
 * @author w
 * @version 2023-08-21
 */
@Table(name="task_detail", alias="a", label="任务信息", columns={
		@Column(name="task_id", attrName="taskId", label="任务id", isPK=true),
		@Column(name="task_type", attrName="taskType", label="任务类型"),
		@Column(name="task_name", attrName="taskName", label="任务名称", queryType=QueryType.LIKE),
		@Column(name="task_num", attrName="taskNum", label="任务编号", isUpdateForce=true),
		@Column(name="task_cycle", attrName="taskCycle", label="巡检周期code"),
		@Column(name="remark", attrName="remark", label="备注"),
		@Column(name="station_id", attrName="stationId", label="站所id"),
		@Column(name="task_status", attrName="taskStatus", label="任务状态"),
		@Column(name="start_time", attrName="startTime", label="任务开始时间"),
		@Column(name="end_time", attrName="endTime", label="任务结束时间", isUpdateForce=true),
		@Column(name="error_num", attrName="errorNum", label="异常数量", isUpdateForce=true),
		@Column(name="task_result", attrName="taskResult", label="巡检结果"),
		@Column(name="create_user", attrName="createUser", label="创建者"),
	}, orderBy="a.task_id DESC"
)
public class TaskDetailEntity extends DataEntity<TaskDetailEntity> {
	
	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long taskId;		// 任务id
	private String taskType;		// 任务类型
	private String taskName;		// 任务名称
	private String taskNum;		// 任务编号
	private String taskCycle;		// 巡检周期code
	private String remark;		// 备注
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long stationId;		// 站所id
	private Date startTime;		// 任务开始时间
	private Date endTime;		// 任务结束时间
	private Long errorNum;		// 异常数量
	private String taskResult;		// 巡检结果
	private String taskStatus;
	private String createUser;
	/**
	 * 负责人id
	 */
	private List<String> directorIds;

	/**
	 * 工作组成员id
	 */
	private List<String> workerIds;

	/**
	 * 巡检设备id
	 */
	private List<Long> deviceIds;
	/**
	 * 设备
	 */
	private List<DeviceEntity> devices;

	/**
	 * 负责人
	 */
	private List<EmpUser> directors;

	/**
	 * 工作组成员
	 */
	private List<EmpUser> workers;

	private String stationName;

	public TaskDetailEntity() {
		this(null);
	}
	
	public TaskDetailEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@NotBlank(message="任务类型不能为空")
	@Size(min=0, max=50, message="任务类型长度不能超过 50 个字符")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	@NotBlank(message="任务名称不能为空")
	@Size(min=0, max=500, message="任务名称长度不能超过 500 个字符")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}
	
	@Size(min=0, max=50, message="巡检周期code长度不能超过 50 个字符")
	public String getTaskCycle() {
		return taskCycle;
	}

	public void setTaskCycle(String taskCycle) {
		this.taskCycle = taskCycle;
	}
	
	@Size(min=0, max=500, message="备注长度不能超过 500 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@NotNull(message="站所id不能为空")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="任务开始时间不能为空")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Long getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Long errorNum) {
		this.errorNum = errorNum;
	}
	
	@Size(min=0, max=50, message="巡检结果长度不能超过 50 个字符")
	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public void setTaskStatus(String taskStatus){
		this.taskStatus = taskStatus;
	}
	public String getTaskStatus(){
		return taskStatus;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public List<EmpUser> getWorkers() {
		return workers;
	}

	public void setWorkers(List<EmpUser> workers) {
		this.workers = workers;
	}

	public List<EmpUser> getDirectors() {
		return directors;
	}

	public void setDirectors(List<EmpUser> directors) {
		this.directors = directors;
	}

	public List<DeviceEntity> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceEntity> devices) {
		this.devices = devices;
	}

	public List<String> getDirectorIds() {
		return directorIds;
	}

	public void setDirectorIds(List<String> directorIds) {
		this.directorIds = directorIds;
	}

	public List<String> getWorkerIds() {
		return workerIds;
	}

	public void setWorkerIds(List<String> workerIds) {
		this.workerIds = workerIds;
	}

	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}