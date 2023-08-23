package com.jeesite.modules.task.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeesite.modules.attachment.common.AttachmentType;
import com.jeesite.modules.attachment.service.BaseAttachmentEntityService;
import com.jeesite.modules.attachment.vo.AttachmentVO;
import com.jeesite.modules.base.constant.BaseConstants;
import com.jeesite.modules.base.service.BaseConstantEntityService;
import com.jeesite.modules.base.vo.ConstantVO;
import com.jeesite.modules.onnx.service.IDetectionService;
import com.jeesite.modules.onnx.vo.DetectionSortVO;
import com.jeesite.modules.onnx.yolo.Detection;
import com.jeesite.modules.screen.vo.StrapScreenVO;
import com.jeesite.modules.strap.service.StrapDetailEntityService;
import com.jeesite.modules.strap.vo.StrapDetailVO;
import com.jeesite.modules.task.dao.TaskRecordEntityDao;
import com.jeesite.modules.task.entity.TaskRecognizeEntity;
import com.jeesite.modules.task.vo.TaskRecognizeVO;
import com.jeesite.modules.utils.DateUtils;
import com.jeesite.modules.utils.VOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.task.entity.TaskRelationEntity;
import com.jeesite.modules.task.dao.TaskRelationEntityDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

/**
 * task_relationService
 * @author w
 * @version 2023-08-22
 */
@Service
@Slf4j
@Transactional
public class TaskRelationEntityService extends CrudService<TaskRelationEntityDao, TaskRelationEntity> {
	
	/**
	 * 获取单条数据
	 * @param taskRelationEntity
	 * @return
	 */
	@Override
	public TaskRelationEntity get(TaskRelationEntity taskRelationEntity) {
		return super.get(taskRelationEntity);
	}
	
	/**
	 * 查询分页数据
	 * @param taskRelationEntity 查询条件
	 * @param taskRelationEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskRelationEntity> findPage(TaskRelationEntity taskRelationEntity) {
		return super.findPage(taskRelationEntity);
	}
	
	/**
	 * 查询列表数据
	 * @param taskRelationEntity
	 * @return
	 */
	@Override
	public List<TaskRelationEntity> findList(TaskRelationEntity taskRelationEntity) {
		return super.findList(taskRelationEntity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taskRelationEntity
	 */
	@Override
	@Transactional
	public void save(TaskRelationEntity taskRelationEntity) {
		super.save(taskRelationEntity);
	}
	
	/**
	 * 更新状态
	 * @param taskRelationEntity
	 */
	@Override
	@Transactional
	public void updateStatus(TaskRelationEntity taskRelationEntity) {
		super.updateStatus(taskRelationEntity);
	}
	
	/**
	 * 删除数据
	 * @param taskRelationEntity
	 */
	@Override
	@Transactional
	public void delete(TaskRelationEntity taskRelationEntity) {
		super.delete(taskRelationEntity);
	}




}