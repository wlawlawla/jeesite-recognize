package com.jeesite.modules.task.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.task.entity.TaskDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务DAO接口
 * @author w
 * @version 2023-08-21
 */
@MyBatisDao
public interface TaskDetailEntityDao extends CrudDao<TaskDetailEntity> {
    /**
     * 查询任务
     * @param taskId
     * @return
     */
    TaskDetailEntity findByTaskId(@Param("taskId") Long taskId);
/*
    *//**
     * 任务查询
     * @param page
     * @param searchParameter
     * @return
     *//*
    Page<TaskDetailEntity> searchTask(Page page, @Param("param")TaskSearchParameter searchParameter);*/

    /**
     * 查询当前任务列表
     * @param userId
     * @param relationType
     * @return
     */
    List<TaskDetailEntity> getCurrentTask(@Param("userId") String userId, @Param("relationType") Integer relationType);
}