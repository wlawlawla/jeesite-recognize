package com.jeesite.modules.task.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.sys.entity.EmpUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TaskInfoVO {

    /**
     * 任务id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long taskId;

    /**
     * 任务类型
     */
    private String taskType;

    private String taskTypeStr;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务编号
     */
    private String taskNum;

    /**
     * 巡检周期
     */
    private String taskCycle;

    private String taskCycleStr;

    /**
     * 备注
     */
    private String remark;

    /**
     * 站点id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long stationId;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 任务状态
     */
    private String status;

    private String statusStr;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务结束时间
     */
    private Date endTime;

    /**
     * 异常数量
     */
    private Integer errorNumber;

    /**
     * 巡检结果
     */
    private String taskResult;

    /**
     * 任务创建人id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createBy;

    /**
     * 负责人
     */
    private List<EmpUser> directors;

    private String directorNames;

    /**
     * 工作组成员
     */
    private List<EmpUser> workers;

    private String workerNames;

    /**
     * 设备
     */
    private List<DeviceEntity> devices;

    /**
     * 负责人id
     */
    private List<String> directorIds;

    /**
     * 工作组成员id
     */
    private List<String> workerIds;

    /**
     * 巡检设备id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> deviceIds;

    private String createUser;

    private String createUserName;

}
