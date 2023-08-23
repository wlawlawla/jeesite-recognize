package com.jeesite.modules.base.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.base.entity.BaseConstantEntity;

import java.util.List;

/**
 * base_constantDAO接口
 * @author w
 * @version 2023-08-22
 */
@MyBatisDao
public interface BaseConstantEntityDao extends CrudDao<BaseConstantEntity> {

    List<BaseConstantEntity> findAll();
}