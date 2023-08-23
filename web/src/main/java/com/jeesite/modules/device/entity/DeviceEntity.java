package com.jeesite.modules.device.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 设备Entity
 * @author w
 * @version 2023-08-17
 */
@Table(name="device_info", alias="a", label="设备信息", columns={
		@Column(name="device_id", attrName="deviceId", label="主键", isPK=true),
		@Column(name="device_name", attrName="deviceName", label="设备名称", queryType=QueryType.LIKE),
		@Column(name="device_num", attrName="deviceNum", label="设备编号"),
		@Column(name="device_rfid", attrName="deviceRfid", label="设备rfid"),
		@Column(name="station_id", attrName="stationId", label="站所id"),
		@Column(name="attachment_id", attrName="attachmentId", label="图片id"),
		@Column(name="order_num", attrName="orderNum", label="排序", isUpdateForce=true),
	}, orderBy="a.device_id DESC"
)
public class DeviceEntity extends DataEntity<DeviceEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long deviceId;		// 主键
	private String deviceName;		// 设备名称
	private String deviceNum;		// 设备编号
	private String deviceRfid;		// 设备rfid
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long stationId;		// 站所id
	private Long orderNum;		// 排序
	private String stationName;
	private Long attachmentId;

	public DeviceEntity() {
		this(null);
	}
	
	public DeviceEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	@NotBlank(message="设备名称不能为空")
	@Size(min=0, max=200, message="设备名称长度不能超过 200 个字符")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	@NotBlank(message="设备编号不能为空")
	@Size(min=0, max=200, message="设备编号长度不能超过 200 个字符")
	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	
	@NotBlank(message="设备rfid不能为空")
	@Size(min=0, max=200, message="设备rfid长度不能超过 200 个字符")
	public String getDeviceRfid() {
		return deviceRfid;
	}

	public void setDeviceRfid(String deviceRfid) {
		this.deviceRfid = deviceRfid;
	}
	
	@NotNull(message="站所id不能为空")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getStationName(){
		return stationName;
	}

	public void setStationName(String stationName){
		this.stationName = stationName;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
}