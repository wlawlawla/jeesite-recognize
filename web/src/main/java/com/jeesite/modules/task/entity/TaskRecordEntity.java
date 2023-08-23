package com.jeesite.modules.task.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * task_recordEntity
 * @author w
 * @version 2023-08-22
 */
@Table(name="task_record", alias="a", label="task_record信息", columns={
		@Column(name="id", attrName="tid", label="主键", isPK=true),
		@Column(name="task_id", attrName="taskId", label="任务id"),
		@Column(name="column_name", attrName="columnName", label="字段名", queryType=QueryType.LIKE),
		@Column(name="context", attrName="context", label="内容"),
	}, orderBy="a.id DESC"
)
public class TaskRecordEntity extends DataEntity<TaskRecordEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long tid;		// 主键
	private Long taskId;		// 任务id
	private String columnName;		// 字段名
	private String context;		// 内容

	public TaskRecordEntity() {
		this(null);
	}
	
	public TaskRecordEntity(String id){
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
	
	@NotBlank(message="字段名不能为空")
	@Size(min=0, max=50, message="字段名长度不能超过 50 个字符")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
}