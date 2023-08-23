package com.jeesite.modules.strap.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.strap.entity.StrapDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 压板DAO接口
 * @author w
 * @version 2023-08-21
 */
@MyBatisDao
public interface StrapDetailEntityDao extends CrudDao<StrapDetailEntity> {

    /**
     * 根据压板屏id查询
     * @param screenId
     * @return
     */
    List<StrapDetailEntity> findByScreenId(@Param("screenId") Long screenId);
}