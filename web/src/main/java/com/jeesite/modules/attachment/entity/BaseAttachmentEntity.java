package com.jeesite.modules.attachment.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import io.swagger.models.auth.In;

/**
 * base_attachmentEntity
 * @author w
 * @version 2023-08-22
 */
@Table(name="base_attachment", alias="a", label="base_attachment信息", columns={
		@Column(name="id", attrName="tid", label="主键", isPK=true),
		@Column(name="fk_sid", attrName="fkSid", label="外键", isUpdateForce=true),
		@Column(name="attachment_type", attrName="attachmentType", label="类型"),
		@Column(name="attachment_name", attrName="attachmentName", label="名称", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="附件"),
		@Column(name="sizein_mb", attrName="sizeinMb", label="附件大小", isUpdateForce=true),
	}, orderBy="a.id DESC"
)
public class BaseAttachmentEntity extends DataEntity<BaseAttachmentEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long tid;		// 主键
	private Long fkSid;		// 外键
	private Integer attachmentType;		// 类型
	private String attachmentName;		// 名称
	private byte[] content;		// 附件
	private Double sizeinMb;		// 附件大小

	public BaseAttachmentEntity() {
		this(null);
	}
	
	public BaseAttachmentEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	public Long getFkSid() {
		return fkSid;
	}

	public void setFkSid(Long fkSid) {
		this.fkSid = fkSid;
	}
	
	@NotNull(message="类型不能为空")
	public Integer getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	@Size(min=0, max=300, message="名称长度不能超过 300 个字符")
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public Double getSizeinMb() {
		return sizeinMb;
	}

	public void setSizeinMb(Double sizeinMb) {
		this.sizeinMb = sizeinMb;
	}
	
}