package com.jeesite.modules.station.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * station_infoEntity
 * @author w
 * @version 2023-08-18
 */
@Table(name="station_info", alias="a", label="station_info信息", columns={
		@Column(name="station_id", attrName="stationId", label="主键", isPK=true),
		@Column(name="station_name", attrName="stationName", label="站所名称", queryType=QueryType.LIKE),
	}, orderBy="a.station_id DESC"
)
public class StationEntity extends DataEntity<StationEntity> {
	
	private static final long serialVersionUID = 1L;
	private Long stationId;		// 主键
	private String stationName;		// 站所名称

	public StationEntity() {
		this(null);
	}
	
	public StationEntity(String id){
		super(id);
	}
	
	@JsonSerialize(using = ToStringSerializer.class)
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
	@NotBlank(message="站所名称不能为空")
	@Size(min=0, max=200, message="站所名称长度不能超过 200 个字符")
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
}