package com.jeesite.modules.task.web;

import com.jeesite.modules.task.service.TaskRecognizeEntityService;
import com.jeesite.modules.task.service.TaskRelationEntityService;
import com.jeesite.modules.task.vo.TaskRecognizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${adminPath}/recognize")
public class TaskRecognizeController {
    @Autowired
    private TaskRecognizeEntityService taskRecognizeService;

    @PostMapping
    public ResponseEntity<TaskRecognizeVO> recognize(@RequestParam("taskId") Long taskId,
                                                     @RequestParam("screenId") Long screenId,
                                                     @RequestParam(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(taskRecognizeService.analysisScreenImage(taskId, screenId, file), HttpStatus.OK);
    }
}
