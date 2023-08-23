package com.jeesite.modules.device.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.device.entity.DeviceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备DAO接口
 * @author w
 * @version 2023-08-17
 */
@MyBatisDao
public interface DeviceEntityDao extends CrudDao<DeviceEntity> {

    List<DeviceEntity> findAll();

    List<DeviceEntity> findByDeviceIdIn(@Param("idList") List<Long> idList);

    DeviceEntity findById(@Param("id") Long id);
}