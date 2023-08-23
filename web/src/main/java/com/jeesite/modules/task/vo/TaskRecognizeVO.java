package com.jeesite.modules.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.modules.base.constant.BaseConstants;
import lombok.Data;

@Data
public class TaskRecognizeVO {
    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 屏幕id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long screenId;

    /**
     * 任务id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long taskId;

    /**
     * 压板数量
     */
    private Integer strapNumber;

    /**
     * 异常数量
     */
    private Integer errorNumber;

    /**
     * 屏幕id
     */
    private String imageUrl;

    public void setImageUrl(Long id){
        if (id != null){
            this.imageUrl = BaseConstants.IMAGE_URL_PREFIX + id;
        }
    }

}
