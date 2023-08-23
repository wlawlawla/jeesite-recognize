package com.jeesite.modules.attachment.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.attachment.entity.BaseAttachmentEntity;
import org.apache.ibatis.annotations.Param;

/**
 * base_attachmentDAO接口
 * @author w
 * @version 2023-08-22
 */
@MyBatisDao
public interface BaseAttachmentEntityDao extends CrudDao<BaseAttachmentEntity> {

    /**
     * 查询附件
     * @param id
     * @return
     */
    BaseAttachmentEntity findById(@Param("id") Long id);

    /**
     * 查询附件
     * @param fkSid
     * @param type
     * @return
     */
    BaseAttachmentEntity findByFkSidAndType(@Param("fkSid") Long fkSid, @Param("type") Integer type);

    /**
     * 删除附件
     * @param id
     */
    void deleteByAttachmentId(@Param("id") Long id);
}