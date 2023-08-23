package com.jeesite.modules.task.dto;

import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.sys.entity.EmpUser;
import lombok.Data;

import java.util.List;

@Data
public class TaskInfoDto {
    private boolean isNewRecord;
    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 站点id
     */
    private Long stationId;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务编号
     */
    private String taskNum;

    /**
     * 负责人id
     */
    private List<String> directorIds;

    /**
     * 工作组成员id
     */
    private List<String> workerIds;

    /**
     * 巡检周期
     */
    private String taskCycle;

    /**
     * 巡检状态
     */
    private String taskStatus;

    /**
     * 巡检设备id
     */
    private List<Long> deviceIds;

    /**
     * 备注
     */
    private String remark;


    /**
     * 设备
     */
    private List<DeviceEntity> devices;

    /**
     * 负责人
     */
    private List<EmpUser> directors;

    /**
     * 工作组成员
     */
    private List<EmpUser> workers;
}
