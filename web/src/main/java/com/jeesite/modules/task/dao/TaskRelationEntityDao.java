package com.jeesite.modules.task.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.task.entity.TaskRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * task_relationDAO接口
 * @author w
 * @version 2023-08-22
 */
@MyBatisDao
public interface TaskRelationEntityDao extends CrudDao<TaskRelationEntity> {

    /**
     * 查询任务关联信息
     * @param taskId
     * @return
     */
    List<TaskRelationEntity> findByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据任务id删除关联信息
     * @param taskId
     */
    void deleteByTaskId(@Param("taskId") Long taskId);
}