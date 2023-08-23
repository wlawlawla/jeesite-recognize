package com.jeesite.modules.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * base_constantEntity
 * @author w
 * @version 2023-08-22
 */
@Table(name="base_constant", alias="a", label="base_constant信息", columns={
		@Column(name="c_id", attrName="cid", label="主键", isPK=true),
		@Column(name="c_type", attrName="ctype", label="类型"),
		@Column(name="c_code", attrName="ccode", label="代码"),
		@Column(name="c_value", attrName="cvalue", label="值"),
		@Column(name="p_id", attrName="pid", label="父级id", isUpdateForce=true),
		@Column(name="p_code", attrName="pcode", label="父级code"),
		@Column(name="order_num", attrName="orderNum", label="排序", isUpdateForce=true),
	}, orderBy="a.c_id DESC"
)
public class BaseConstantEntity extends DataEntity<BaseConstantEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long cid;		// 主键
	private String ctype;		// 类型
	private String ccode;		// 代码
	private String cvalue;		// 值
	private String pid;		// 父级id
	private String pcode;		// 父级code
	private Long orderNum;		// 排序

	public BaseConstantEntity() {
		this(null);
	}
	
	public BaseConstantEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	@NotBlank(message="类型不能为空")
	@Size(min=0, max=50, message="类型长度不能超过 50 个字符")
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	
	@NotBlank(message="代码不能为空")
	@Size(min=0, max=50, message="代码长度不能超过 50 个字符")
	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}
	
	@Size(min=0, max=50, message="值长度不能超过 50 个字符")
	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Size(min=0, max=50, message="父级code长度不能超过 50 个字符")
	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	
	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
}