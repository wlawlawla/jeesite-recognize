package com.jeesite.modules.base.web;

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
import com.jeesite.modules.base.entity.BaseConstantEntity;
import com.jeesite.modules.base.service.BaseConstantEntityService;

/**
 * base_constantController
 * @author w
 * @version 2023-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/base/baseConstantEntity")
public class BaseConstantEntityController extends BaseController {

	@Autowired
	private BaseConstantEntityService baseConstantEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BaseConstantEntity get(Long cid, boolean isNewRecord) {
		return baseConstantEntityService.get(cid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("base:baseConstantEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseConstantEntity baseConstantEntity, Model model) {
		model.addAttribute("baseConstantEntity", baseConstantEntity);
		return "modules/base/baseConstantEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("base:baseConstantEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BaseConstantEntity> listData(BaseConstantEntity baseConstantEntity, HttpServletRequest request, HttpServletResponse response) {
		baseConstantEntity.setPage(new Page<>(request, response));
		Page<BaseConstantEntity> page = baseConstantEntityService.findPage(baseConstantEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("base:baseConstantEntity:view")
	@RequestMapping(value = "form")
	public String form(BaseConstantEntity baseConstantEntity, Model model) {
		model.addAttribute("baseConstantEntity", baseConstantEntity);
		return "modules/base/baseConstantEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("base:baseConstantEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BaseConstantEntity baseConstantEntity) {
		baseConstantEntityService.save(baseConstantEntity);
		return renderResult(Global.TRUE, text("保存base_constant成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("base:baseConstantEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BaseConstantEntity baseConstantEntity) {
		baseConstantEntityService.delete(baseConstantEntity);
		return renderResult(Global.TRUE, text("删除base_constant成功！"));
	}
	
}