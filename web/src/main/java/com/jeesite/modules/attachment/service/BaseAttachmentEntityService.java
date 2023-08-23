package com.jeesite.modules.attachment.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.jeesite.modules.attachment.common.AttachmentType;
import com.jeesite.modules.attachment.vo.AttachmentVO;
import com.jeesite.modules.utils.VOUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.attachment.entity.BaseAttachmentEntity;
import com.jeesite.modules.attachment.dao.BaseAttachmentEntityDao;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * base_attachmentService
 *
 * @author w
 * @version 2023-08-22
 */
@Service
public class BaseAttachmentEntityService extends CrudService<BaseAttachmentEntityDao, BaseAttachmentEntity> {

    public void downloadImageById(Long attachmentId, HttpServletResponse response){
        if (attachmentId == null){
            return;
        }
        downloadImage(dao.findById(attachmentId), response);
    }

    /**
     * 下载图片
     * @param baseAttachmentEntity
     * @param response
     */
    private void downloadImage(BaseAttachmentEntity baseAttachmentEntity, HttpServletResponse response){
        if (baseAttachmentEntity == null){
            return;
        }
        try {
            response.setContentType("image/png");
            response.setHeader("Content-disposition", "attachment; filename=" + baseAttachmentEntity.getAttachmentName());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            OutputStream out = response.getOutputStream();
            out.write(baseAttachmentEntity.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AttachmentVO uploadAttachment(File file, Integer type, Long fId){
        if (file == null){
            return null;
        }

        BaseAttachmentEntity baseAttachmentEntity = new BaseAttachmentEntity();
        String name = file.getName();
        Double size = BigDecimal.valueOf((FileUtil.size(file) * 1.00) / (1024 * 1024)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        try {
            baseAttachmentEntity.setContent(FileUtil.readBytes(file));
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }

        baseAttachmentEntity.setAttachmentName(name);
        baseAttachmentEntity.setAttachmentType(type == null ? AttachmentType.DEFAULT.getType() : type);
        baseAttachmentEntity.setSizeinMb(size);
        baseAttachmentEntity.setFkSid(fId);

        save(baseAttachmentEntity);
        return VOUtil.getVO(AttachmentVO.class, baseAttachmentEntity);
    }

    public AttachmentVO uploadAttachment(MultipartFile file, Integer type, Long fId) {
        if (file == null) {
            return null;
        }

        BaseAttachmentEntity baseAttachmentEntity = new BaseAttachmentEntity();
        String name = file.getOriginalFilename();
        Double size = BigDecimal.valueOf((file.getSize() * 1.00) / (1024 * 1024)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        try {
            baseAttachmentEntity.setContent(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        baseAttachmentEntity.setAttachmentName(name);
        baseAttachmentEntity.setAttachmentType(type == null ? AttachmentType.DEFAULT.getType() : type);
        baseAttachmentEntity.setSizeinMb(size);
        baseAttachmentEntity.setFkSid(fId);

        save(baseAttachmentEntity);
        return VOUtil.getVO(AttachmentVO.class, baseAttachmentEntity);
    }


    /**
     * 获取单条数据
     *
     * @param baseAttachmentEntity
     * @return
     */
    @Override
    public BaseAttachmentEntity get(BaseAttachmentEntity baseAttachmentEntity) {
        return super.get(baseAttachmentEntity);
    }

    /**
     * 查询分页数据
     *
     * @param baseAttachmentEntity 查询条件
     * @param baseAttachmentEntity page 分页对象
     * @return
     */
    @Override
    public Page<BaseAttachmentEntity> findPage(BaseAttachmentEntity baseAttachmentEntity) {
        return super.findPage(baseAttachmentEntity);
    }

    /**
     * 查询列表数据
     *
     * @param baseAttachmentEntity
     * @return
     */
    @Override
    public List<BaseAttachmentEntity> findList(BaseAttachmentEntity baseAttachmentEntity) {
        return super.findList(baseAttachmentEntity);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param baseAttachmentEntity
     */
    @Override
    @Transactional
    public void save(BaseAttachmentEntity baseAttachmentEntity) {
        super.save(baseAttachmentEntity);
    }

    /**
     * 更新状态
     *
     * @param baseAttachmentEntity
     */
    @Override
    @Transactional
    public void updateStatus(BaseAttachmentEntity baseAttachmentEntity) {
        super.updateStatus(baseAttachmentEntity);
    }

    /**
     * 删除数据
     *
     * @param baseAttachmentEntity
     */
    @Override
    @Transactional
    public void delete(BaseAttachmentEntity baseAttachmentEntity) {
        super.delete(baseAttachmentEntity);
    }

}