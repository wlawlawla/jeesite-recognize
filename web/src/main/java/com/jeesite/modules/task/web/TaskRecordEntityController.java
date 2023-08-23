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
import com.jeesite.modules.task.entity.TaskRecordEntity;
import com.jeesite.modules.task.service.TaskRecordEntityService;

/**
 * task_recordController
 * @author w
 * @version 2023-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskRecordEntity")
public class TaskRecordEntityController extends BaseController {

	@Autowired
	private TaskRecordEntityService taskRecordEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskRecordEntity get(Long tid, boolean isNewRecord) {
		return taskRecordEntityService.get(tid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskRecordEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskRecordEntity taskRecordEntity, Model model) {
		model.addAttribute("taskRecordEntity", taskRecordEntity);
		return "modules/task/taskRecordEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskRecordEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskRecordEntity> listData(TaskRecordEntity taskRecordEntity, HttpServletRequest request, HttpServletResponse response) {
		taskRecordEntity.setPage(new Page<>(request, response));
		Page<TaskRecordEntity> page = taskRecordEntityService.findPage(taskRecordEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskRecordEntity:view")
	@RequestMapping(value = "form")
	public String form(TaskRecordEntity taskRecordEntity, Model model) {
		model.addAttribute("taskRecordEntity", taskRecordEntity);
		return "modules/task/taskRecordEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskRecordEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskRecordEntity taskRecordEntity) {
		taskRecordEntityService.save(taskRecordEntity);
		return renderResult(Global.TRUE, text("保存task_record成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskRecordEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskRecordEntity taskRecordEntity) {
		taskRecordEntityService.delete(taskRecordEntity);
		return renderResult(Global.TRUE, text("删除task_record成功！"));
	}
	
}