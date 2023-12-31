package com.jeesite.modules.attachment.common;

public enum AttachmentType {
    /**
     * 系统文件
     */
    SYSTEM(-1),

    /**
     * 默认
     */
    DEFAULT(0),

    /**
     * 设备二维码
     */
    DEVICE_IMAGE(1),

    /**
     * 巡检工作图片
     */
    CHECK_TASK_IMAGE(2),

    /**
     * 压板屏幕图片
     */
    SCREEN_IMAGE(3),

    STATION_IMAGE(4),
    ;

    private int type;

    AttachmentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
