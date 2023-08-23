package com.jeesite.modules.strap.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 压板Entity
 * @author w
 * @version 2023-08-21
 */
@Table(name="strap_detail", alias="a", label="压板信息", columns={
		@Column(name="strap_id", attrName="strapId", label="压板id", isPK=true),
		@Column(name="screen_id", attrName="screenId", label="屏幕id"),
		@Column(name="strap_name", attrName="strapName", label="压板名称", queryType=QueryType.LIKE),
		@Column(name="strap_value", attrName="strapValue", label="压板值"),
		@Column(name="strap_position", attrName="strapPosition", label="压板位置", isUpdateForce=true),
	}, orderBy="a.strap_id DESC"
)
public class StrapDetailEntity extends DataEntity<StrapDetailEntity> {
	
	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long strapId;		// 压板id
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long screenId;		// 屏幕id
	private String strapName;		// 压板名称
	private String strapValue;		// 压板值
	private Integer strapPosition;		// 压板位置

	private String screenName;

	public void setScreenName(String screenName){
		this.screenName = screenName;
	}

	public String getScreenName(){
		return screenName;
	}

	public StrapDetailEntity() {
		this(null);
	}
	
	public StrapDetailEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getStrapId() {
		return strapId;
	}

	public void setStrapId(Long strapId) {
		this.strapId = strapId;
	}
	
	@NotNull(message="屏幕id不能为空")
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	@Size(min=0, max=200, message="压板名称长度不能超过 200 个字符")
	public String getStrapName() {
		return strapName;
	}

	public void setStrapName(String strapName) {
		this.strapName = strapName;
	}
	
	@Size(min=0, max=200, message="压板值长度不能超过 200 个字符")
	public String getStrapValue() {
		return strapValue;
	}

	public void setStrapValue(String strapValue) {
		this.strapValue = strapValue;
	}


	public Integer getStrapPosition() {
		return strapPosition;
	}

	public void setStrapPosition(Integer strapPosition) {
		this.strapPosition = strapPosition;
	}
}