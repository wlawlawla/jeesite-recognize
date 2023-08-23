package com.jeesite.modules.task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * task_recognizeEntity
 * @author w
 * @version 2023-08-22
 */
@Table(name="task_recognize", alias="a", label="task_recognize信息", columns={
		@Column(name="id", attrName="tid", label="主键", isPK=true),
		@Column(name="task_id", attrName="taskId", label="任务id"),
		@Column(name="device_id", attrName="deviceId", label="设备id"),
		@Column(name="screen_id", attrName="screenId", label="压板屏幕id"),
		@Column(name="recognize_num", attrName="recognizeNum", label="识别出的压板数量", isUpdateForce=true),
		@Column(name="strap_num", attrName="strapNum", label="压板数量", isUpdateForce=true),
		@Column(name="error_num", attrName="errorNum", label="异常数量", isUpdateForce=true),
		@Column(name="attachment_id", attrName="attachmentId", label="图片附件id", comment="图片附件id（圈出异常框）", isUpdateForce=true),
	}, orderBy="a.id DESC"
)
public class TaskRecognizeEntity extends DataEntity<TaskRecognizeEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long tid;		// 主键
	private Long taskId;		// 任务id
	private Long deviceId;		// 设备id
	private Long screenId;		// 压板屏幕id
	private Integer recognizeNum;		// 识别出的压板数量
	private Integer strapNum;		// 压板数量
	private Integer errorNum;		// 异常数量
	private Long attachmentId;		// 图片附件id（圈出异常框）

	public TaskRecognizeEntity() {
		this(null);
	}
	
	public TaskRecognizeEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	@NotNull(message="任务id不能为空")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@NotNull(message="设备id不能为空")
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	@NotNull(message="压板屏幕id不能为空")
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	public Integer getRecognizeNum() {
		return recognizeNum;
	}

	public void setRecognizeNum(Integer recognizeNum) {
		this.recognizeNum = recognizeNum;
	}
	
	public Integer getStrapNum() {
		return strapNum;
	}

	public void setStrapNum(Integer strapNum) {
		this.strapNum = strapNum;
	}
	
	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}
	
	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
}