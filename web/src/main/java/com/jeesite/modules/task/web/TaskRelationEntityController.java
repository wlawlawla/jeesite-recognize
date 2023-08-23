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
import com.jeesite.modules.task.entity.TaskRelationEntity;
import com.jeesite.modules.task.service.TaskRelationEntityService;

/**
 * task_relationController
 * @author w
 * @version 2023-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/task/taskRelationEntity")
public class TaskRelationEntityController extends BaseController {

	@Autowired
	private TaskRelationEntityService taskRelationEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TaskRelationEntity get(Long tid, boolean isNewRecord) {
		return taskRelationEntityService.get(tid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("task:taskRelationEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskRelationEntity taskRelationEntity, Model model) {
		model.addAttribute("taskRelationEntity", taskRelationEntity);
		return "modules/task/taskRelationEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("task:taskRelationEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TaskRelationEntity> listData(TaskRelationEntity taskRelationEntity, HttpServletRequest request, HttpServletResponse response) {
		taskRelationEntity.setPage(new Page<>(request, response));
		Page<TaskRelationEntity> page = taskRelationEntityService.findPage(taskRelationEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("task:taskRelationEntity:view")
	@RequestMapping(value = "form")
	public String form(TaskRelationEntity taskRelationEntity, Model model) {
		model.addAttribute("taskRelationEntity", taskRelationEntity);
		return "modules/task/taskRelationEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("task:taskRelationEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TaskRelationEntity taskRelationEntity) {
		taskRelationEntityService.save(taskRelationEntity);
		return renderResult(Global.TRUE, text("保存task_relation成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("task:taskRelationEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TaskRelationEntity taskRelationEntity) {
		taskRelationEntityService.delete(taskRelationEntity);
		return renderResult(Global.TRUE, text("删除task_relation成功！"));
	}
	
}