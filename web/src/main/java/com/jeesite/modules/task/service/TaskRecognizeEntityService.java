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
import com.jeesite.modules.task.entity.TaskRecognizeEntity;
import com.jeesite.modules.task.dao.TaskRecognizeEntityDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

/**
 * task_recognizeService
 * @author w
 * @version 2023-08-22
 */
@Service
@Transactional
@Slf4j
public class TaskRecognizeEntityService extends CrudService<TaskRecognizeEntityDao, TaskRecognizeEntity> {
	
	/**
	 * 获取单条数据
	 * @param taskRecognizeEntity
	 * @return
	 */
	@Override
	public TaskRecognizeEntity get(TaskRecognizeEntity taskRecognizeEntity) {
		return super.get(taskRecognizeEntity);
	}
	
	/**
	 * 查询分页数据
	 * @param taskRecognizeEntity 查询条件
	 * @param taskRecognizeEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<TaskRecognizeEntity> findPage(TaskRecognizeEntity taskRecognizeEntity) {
		return super.findPage(taskRecognizeEntity);
	}
	
	/**
	 * 查询列表数据
	 * @param taskRecognizeEntity
	 * @return
	 */
	@Override
	public List<TaskRecognizeEntity> findList(TaskRecognizeEntity taskRecognizeEntity) {
		return super.findList(taskRecognizeEntity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param taskRecognizeEntity
	 */
	@Override
	@Transactional
	public void save(TaskRecognizeEntity taskRecognizeEntity) {
		super.save(taskRecognizeEntity);
	}
	
	/**
	 * 更新状态
	 * @param taskRecognizeEntity
	 */
	@Override
	@Transactional
	public void updateStatus(TaskRecognizeEntity taskRecognizeEntity) {
		super.updateStatus(taskRecognizeEntity);
	}
	
	/**
	 * 删除数据
	 * @param taskRecognizeEntity
	 */
	@Override
	@Transactional
	public void delete(TaskRecognizeEntity taskRecognizeEntity) {
		super.delete(taskRecognizeEntity);
	}


	@Autowired
	private TaskRecordEntityDao taskRecognizeMapper;

	@Autowired
	private TaskRecordEntityService taskRecordEntityService;

	@Autowired
	private IDetectionService detectionService;

	@Autowired
	private StrapDetailEntityService strapService;

	@Autowired
	private BaseAttachmentEntityService attachmentService;

	@Autowired
	private BaseConstantEntityService constantService;

	private Map<String, String> strapValueMap = new HashMap<>();

	@PostConstruct
	private void init(){
		List<ConstantVO> strapStatusList = constantService.getConstantByType(BaseConstants.HARD_STRAP_STATUS);
		strapValueMap.putAll(strapStatusList.stream().collect(Collectors.toMap(ConstantVO::getCcode, ConstantVO::getCvalue)));
	}


	public TaskRecognizeVO analysisScreenImage(Long taskId, Long screenId, MultipartFile file) throws IOException {
		if (taskId == null || screenId == null){
			log.error("taskId or screenId is null");
			return null;
		}

		StrapScreenVO strapScreenVO = strapService.getStrapScreenInfo(screenId);
		if (strapScreenVO == null){
			log.error("can not find screen by id");
			return null;
		}

		Integer size = strapScreenVO.getStrapNum();
		if (CollectionUtils.isNotEmpty(strapScreenVO.getStrapDetailVOList())){
			//压板数量以有定值设定的数量为准
			size = strapScreenVO.getStrapDetailVOList().size();
		}

		List<StrapDetailVO> strapDetailVOList = strapScreenVO.getStrapDetailVOList();
		strapDetailVOList.sort(Comparator.comparing(StrapDetailVO::getStrapPosition));

		byte[] bytes = file.getBytes();
		Mat img = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_COLOR);
		List<DetectionSortVO> result = detectionService.recognize(img, size, strapScreenVO.getScreenType());
		if (strapScreenVO.getRowNum() != null && strapScreenVO.getRowNum() != result.size()){
			analysisResult(result, strapScreenVO.getRowNum());
		}
		List<Detection> drawList = result.stream().flatMap(detectionSortVO -> detectionSortVO.getDetectionList().stream()).collect(Collectors.toList());
		List<Detection> successDetectionList = new ArrayList<>();
		//todo 目前认为所有压板的行列数都不超过9，即压板位置是两位数字,第一位是行，第二位是列
		strapDetailVOList.forEach(strapDetailVO -> {
			Integer strapPosition = strapDetailVO.getStrapPosition();
			try {
				int rowNumber = strapPosition / 10 - 1;
				int colNumber = strapPosition % 10 - 1;
				Detection detection = result.get(rowNumber).getDetectionList().get(colNumber);
				if (strapValueMap.get(detection.getLabel()
						.replace("hard_", "")
						.replace("soft_", "")
						.replace("_v2", "")
						.replace("_v3", "")
						.replace("on", "open")
						.replace("off", "close"))
						.equals(strapDetailVO.getStrapValue())) {
					successDetectionList.add(detection);
				}
			} catch (Exception e) {
				log.error("识别校对异常");
			}
		});


		drawList.removeAll(successDetectionList);

		String name = DateUtils.NUMBER_DATE_TIME_FORMAT.format(LocalDateTime.now()) + ".jpg";
		detectionService.drawAndWriteImage(img, name, strapScreenVO.getScreenType(), drawList);

		TaskRecognizeEntity taskRecognizeEntity = new TaskRecognizeEntity();
		taskRecognizeEntity.setTaskId(taskId);
		taskRecognizeEntity.setRecognizeNum(drawList.size() + successDetectionList.size());
		taskRecognizeEntity.setErrorNum(drawList.size());
		taskRecognizeEntity.setScreenId(screenId);
		taskRecognizeEntity.setDeviceId(strapScreenVO.getDeviceId());
		taskRecognizeEntity.setStrapNum(size);
		taskRecognizeEntity.setIsNewRecord(true);
		save(taskRecognizeEntity);

		File image = FileUtils.getFile(name);
		AttachmentVO attachmentVO = attachmentService.uploadAttachment(image, AttachmentType.CHECK_TASK_IMAGE.getType(), taskRecognizeEntity.getTid());

		taskRecognizeEntity.setAttachmentId(attachmentVO.getTid());
		taskRecognizeEntity.setIsNewRecord(false);
		save(taskRecognizeEntity);

		return getTaskRecognizeVOList(Arrays.asList(taskRecognizeEntity)).get(0);
	}

	/**
	 * 转换成vo对象
	 * @param taskRecognizeEntityList
	 * @return
	 */
	private List<TaskRecognizeVO> getTaskRecognizeVOList(List<TaskRecognizeEntity> taskRecognizeEntityList){
		if (CollectionUtils.isEmpty(taskRecognizeEntityList)){
			return Collections.emptyList();
		}

		List<TaskRecognizeVO> taskRecognizeVOList = new ArrayList<>();
		taskRecognizeEntityList.forEach(taskRecognizeEntity -> {
			TaskRecognizeVO taskRecognizeVO = VOUtil.getVO(TaskRecognizeVO.class, taskRecognizeEntity);
			taskRecognizeVO.setImageUrl(taskRecognizeEntity.getAttachmentId());
			taskRecognizeVOList.add(taskRecognizeVO);
		});
		return taskRecognizeVOList;
	}

	private List<DetectionSortVO> analysisResult(List<DetectionSortVO> result, Integer rowNum){
		if (result.size() > rowNum){
			int indexA = 0;
			int indexB = 1;
			Float betweenY = result.get(1).getMaxY() - result.get(0).getMaxY();
			for (int i = 2; i < result.size(); i++){
				Float betweenCurrent = result.get(i).getMaxY() - result.get(i-1).getMaxY();
				if (betweenCurrent < betweenY){
					betweenY = betweenCurrent;
					indexA = i - 1;
					indexB = i;
				}
			}
			result.get(indexA).getDetectionList().addAll(result.get(indexB).getDetectionList());
			result.get(indexA).setMaxY(Math.max(result.get(indexA).getMaxY(), result.get(indexB).getMaxY()));
			result.get(indexA).setMinY(Math.min(result.get(indexA).getMinY(), result.get(indexB).getMinY()));
			result.get(indexA).getDetectionList().sort(Comparator.comparing(detection -> detection.getBbox()[0]));
			result.remove(indexB);
			return analysisResult(result, rowNum);
		}
		return result;
	}
}