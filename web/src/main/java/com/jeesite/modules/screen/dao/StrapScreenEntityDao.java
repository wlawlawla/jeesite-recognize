package com.jeesite.modules.screen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.screen.entity.StrapScreenEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * strap_screenDAO接口
 * @author w
 * @version 2023-08-18
 */
@MyBatisDao
public interface StrapScreenEntityDao extends CrudDao<StrapScreenEntity> {


    List<StrapScreenEntity> findAll();

    List<StrapScreenEntity> findByScreenIdIn(@Param("idList") List<Long> idList);
}