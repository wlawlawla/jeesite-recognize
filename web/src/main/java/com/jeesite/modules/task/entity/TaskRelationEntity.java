package com.jeesite.modules.task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * task_relationEntity
 * @author w
 * @version 2023-08-22
 */
@Table(name="task_relation", alias="a", label="task_relation信息", columns={
		@Column(name="id", attrName="tid", label="主键", isPK=true),
		@Column(name="relation_type", attrName="relationType", label="关联类型"),
		@Column(name="task_id", attrName="taskId", label="任务id"),
		@Column(name="relation_id", attrName="relationId", label="关联方id", comment="关联方id：设备id/用户id"),
	}, orderBy="a.id DESC"
)
public class TaskRelationEntity extends DataEntity<TaskRelationEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long tid;		// 主键
	private Integer relationType;		// 关联类型
	private Long taskId;		// 任务id
	private String relationId;		// 关联方id：设备id/用户id

	public TaskRelationEntity() {
		this(null);
	}
	public TaskRelationEntity(Integer relationType, Long taskId, String relationId){
		this.relationType = relationType;
		this.taskId = taskId;
		this.relationId = relationId;
		this.isNewRecord = true;
	}
	
	public TaskRelationEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	@NotNull(message="关联类型不能为空")
	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	
	@NotNull(message="任务id不能为空")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@NotNull(message="关联方id不能为空")
	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	
}