package com.jeesite.modules.screen.vo;

import com.jeesite.modules.base.constant.BaseConstants;
import com.jeesite.modules.strap.vo.StrapDetailVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StrapScreenVO {

    /**
     * 屏幕id
     */
    private Long screenId;

    /**
     * 设备id
     */
    @NotNull
    private Long deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 屏幕类型
     */
    private String screenType;

    /**
     * 屏幕类型
     */
    private String screenTypeStr;

    /**
     * 屏幕名称
     */
    @NotNull
    private String screenName;

    /**
     * 软压板类别
     */
    private String softType;

    /**
     * 软压板类别
     */
    private String softTypeStr;

    /**
     * 软压板页码
     */
    private Integer softPage;

    /**
     * 压板行数
     */
    private Integer rowNum;

    /**
     * 压板列数
     */
    private Integer colNum;

    /**
     * 压板数量
     */
    private Integer strapNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片id
     */
    private Long attachmentId;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 压板详细信息
     */
    private List<StrapDetailVO> strapDetailVOList;

    public void setImageUrl(){
        if (this.attachmentId != null){
            this.imageUrl = BaseConstants.IMAGE_URL_PREFIX + this.attachmentId;
        }
    }

}
