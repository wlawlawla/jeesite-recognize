package com.jeesite.modules.task.web;


import com.jeesite.modules.task.dto.TaskInfoDto;
import com.jeesite.modules.task.service.TaskDetailEntityService;
import com.jeesite.modules.task.service.TaskRecognizeEntityService;
import com.jeesite.modules.task.vo.TaskInfoVO;
import com.jeesite.modules.task.vo.TaskRecognizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${adminPath}/task")
public class TaskController {

    @Autowired
    private TaskDetailEntityService taskDetailEntityService;

    /**
     * 查询当前用户的关联任务
     * @param relationType
     * @return
     */
    @GetMapping("/current")
    public ResponseEntity<List<TaskInfoVO>> getCurrentTask(@RequestParam(value = "relationType", required = false) Integer relationType){
        return new ResponseEntity<>(taskDetailEntityService.getCurrentTaskList(relationType), HttpStatus.OK);
    }

    /**
     * 新建任务
     * @param taskInfoDto
     * @return
     */
    @PostMapping
    public void addTask(@RequestBody TaskInfoDto taskInfoDto){
        taskDetailEntityService.saveDto(taskInfoDto);
    }

    /**
     * 根据taskId获取任务信息
     * @param taskId
     * @return
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskInfoVO> getTaskInfo(@PathVariable Long taskId){
        return new ResponseEntity<>(taskDetailEntityService.getTaskInfo(taskId), HttpStatus.OK);
    }

}
