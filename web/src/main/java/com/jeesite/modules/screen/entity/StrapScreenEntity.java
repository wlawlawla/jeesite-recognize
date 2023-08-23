package com.jeesite.modules.screen.entity;

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
 * strap_screenEntity
 * @author w
 * @version 2023-08-18
 */
@Table(name="strap_screen", alias="a", label="strap_screen信息", columns={
		@Column(name="screen_id", attrName="screenId", label="主键", isPK=true),
		@Column(name="device_id", attrName="deviceId", label="设备id"),
		@Column(name="screen_type", attrName="screenType", label="屏类型"),
		@Column(name="screen_name", attrName="screenName", label="名称", queryType=QueryType.LIKE),
		@Column(name="soft_type", attrName="softType", label="软压板类型", isUpdateForce=true),
		@Column(name="soft_page", attrName="softPage", label="软压板页码", isUpdateForce=true),
		@Column(name="row_num", attrName="rowNum", label="行数", isUpdateForce=true),
		@Column(name="col_num", attrName="colNum", label="列数", isUpdateForce=true),
		@Column(name="strap_num", attrName="strapNum", label="压板个数", isUpdateForce=true),
		@Column(name="remark", attrName="remark", label="备注", isUpdateForce=true),
		@Column(name="attachment_id", attrName="attachmentId", label="图片id", isUpdateForce=true),
	}, orderBy="a.screen_id DESC"
)
public class StrapScreenEntity extends DataEntity<StrapScreenEntity> {
	
	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long screenId;		// 主键
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deviceId;		// 设备名id
	private String screenType;		// 屏类型
	private String screenName;		// 名称
	private String softType;		// 软压板类型
	private Integer softPage;		// 软压板页码
	private Integer rowNum;		// 行数
	private Integer colNum;		// 列数
	private Integer strapNum;		// 压板个数
	private String remark;		// 备注
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long attachmentId;		// 图片id

	private String deviceNameStr;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long stationId;
	private String stationName;
	private String areaSelectValue;

	public StrapScreenEntity() {
		this(null);
	}
	
	public StrapScreenEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	@NotNull(message="设备名不能为空")
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@NotNull(message="屏类型不能为空")
	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
	
	@Size(min=0, max=200, message="名称长度不能超过 200 个字符")
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getSoftType() {
		return softType;
	}

	public void setSoftType(String softType) {
		this.softType = softType;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getDeviceNameStr(){
		return deviceNameStr;
	}

	public void setDeviceNameStr(String deviceNameStr){
		this.deviceNameStr = deviceNameStr;
	}

	public String getStationName(){
		return stationName;
	}

	public void setStationName(String stationName){
		this.stationName = stationName;
	}

	public void setStationId(Long stationId){
		this.stationId = stationId;
	}

	public Long getStationId(){
		return stationId;
	}

	public String getAreaSelectValue(){
		return areaSelectValue;
	}

	public void setAreaSelectValue(String areaSelectValue){
		this.areaSelectValue = areaSelectValue;
	}

	public Integer getSoftPage() {
		return softPage;
	}

	public void setSoftPage(Integer softPage) {
		this.softPage = softPage;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public Integer getStrapNum() {
		return strapNum;
	}

	public void setStrapNum(Integer strapNum) {
		this.strapNum = strapNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}