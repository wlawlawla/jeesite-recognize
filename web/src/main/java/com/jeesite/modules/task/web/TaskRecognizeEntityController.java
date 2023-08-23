package com.jeesite.modules.task.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.task.entity.TaskRecognizeEntity;
import com.jeesite.modules.task.service.TaskRecognizeEntityService;

/**
 * task_recognizeController
 * @author w
 * @version 2023-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskRecognizeEntity")
public class TaskRecognizeEntityController extends BaseController {

	@Autowired
	private TaskRecognizeEntityService taskRecognizeEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskRecognizeEntity get(Long tid, boolean isNewRecord) {
		return taskRecognizeEntityService.get(tid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskRecognizeEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskRecognizeEntity taskRecognizeEntity, Model model) {
		model.addAttribute("taskRecognizeEntity", taskRecognizeEntity);
		return "modules/task/taskRecognizeEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskRecognizeEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskRecognizeEntity> listData(TaskRecognizeEntity taskRecognizeEntity, HttpServletRequest request, HttpServletResponse response) {
		taskRecognizeEntity.setPage(new Page<>(request, response));
		Page<TaskRecognizeEntity> page = taskRecognizeEntityService.findPage(taskRecognizeEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskRecognizeEntity:view")
	@RequestMapping(value = "form")
	public String form(TaskRecognizeEntity taskRecognizeEntity, Model model) {
		model.addAttribute("taskRecognizeEntity", taskRecognizeEntity);
		return "modules/task/taskRecognizeEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskRecognizeEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskRecognizeEntity taskRecognizeEntity) {
		taskRecognizeEntityService.save(taskRecognizeEntity);
		return renderResult(Global.TRUE, text("保存task_recognize成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskRecognizeEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskRecognizeEntity taskRecognizeEntity) {
		taskRecognizeEntityService.delete(taskRecognizeEntity);
		return renderResult(Global.TRUE, text("删除task_recognize成功！"));
	}
	
}