package com.jeesite.modules.attachment.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttachmentVO implements Serializable {

    /**
     * 主键
     */
    private Long tid;

    /**
     * 外键
     */
    private Long fkSid;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件类型
     */
    private Integer attachmentType;

    /**
     * 附件大小
     */
    private Double sizeinMb;
}
