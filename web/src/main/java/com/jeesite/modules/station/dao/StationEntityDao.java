package com.jeesite.modules.station.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.station.entity.StationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * station_infoDAO接口
 * @author w
 * @version 2023-08-18
 */
@MyBatisDao
public interface StationEntityDao extends CrudDao<StationEntity> {

    List<StationEntity> findAll();

    StationEntity findByStationId(@Param("id") Long id);


    /**
     * 批量查询
     * @param stationIdList
     * @return
     */
    List<StationEntity> findByStationIdIn(@Param("stationIdList") List<Long> stationIdList);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    StationEntity findByStationName(@Param("name") String name);
}