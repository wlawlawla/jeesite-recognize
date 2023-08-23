package com.jeesite.modules.task.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.task.dto.TaskInfoDto;
import com.jeesite.modules.task.vo.TaskInfoVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.task.entity.TaskDetailEntity;
import com.jeesite.modules.task.service.TaskDetailEntityService;

/**
 * 任务Controller
 * @author w
 * @version 2023-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskDetailEntity")
public class TaskDetailEntityController extends BaseController {

	@Autowired
	private TaskDetailEntityService taskDetailEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskDetailEntity get(Long taskId, boolean isNewRecord) {
		TaskDetailEntity taskDetailEntity = taskDetailEntityService.get(taskId, isNewRecord);
		return taskDetailEntity;
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskDetailEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskDetailEntity taskDetailEntity, Model model) {
		model.addAttribute("taskDetailEntity", taskDetailEntity);
		return "modules/task/taskDetailEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskDetailEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskInfoVO> listData(TaskDetailEntity taskDetailEntity, HttpServletRequest request, HttpServletResponse response) {
		taskDetailEntity.setPage(new Page<>(request, response));
		Page<TaskInfoVO> page = taskDetailEntityService.findPageVO(taskDetailEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskDetailEntity:view")
	@RequestMapping(value = "form")
	public String form(TaskDetailEntity taskDetailEntity, Model model) {
		model.addAttribute("taskDetailEntity", taskDetailEntity);
		return "modules/task/taskDetailEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskDetailEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskInfoDto taskInfoDto) {
		taskDetailEntityService.saveDto(taskInfoDto);
		return renderResult(Global.TRUE, text("保存任务成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskDetailEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskDetailEntity taskDetailEntity) {
		taskDetailEntityService.delete(taskDetailEntity);
		return renderResult(Global.TRUE, text("删除任务成功！"));
	}
	
}