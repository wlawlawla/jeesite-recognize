package com.jeesite.modules.strap.web;

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
import com.jeesite.modules.strap.entity.StrapDetailEntity;
import com.jeesite.modules.strap.service.StrapDetailEntityService;

/**
 * 压板Controller
 * @author w
 * @version 2023-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/strap/strapDetailEntity")
public class StrapDetailEntityController extends BaseController {

	@Autowired
	private StrapDetailEntityService strapDetailEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public StrapDetailEntity get(Long strapId, boolean isNewRecord) {
		return strapDetailEntityService.get(strapId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("strap:strapDetailEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(StrapDetailEntity strapDetailEntity, Model model) {
		model.addAttribute("strapDetailEntity", strapDetailEntity);
		return "modules/strap/strapDetailEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("strap:strapDetailEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<StrapDetailEntity> listData(StrapDetailEntity strapDetailEntity, HttpServletRequest request, HttpServletResponse response) {
		strapDetailEntity.setPage(new Page<>(request, response));
		Page<StrapDetailEntity> page = strapDetailEntityService.findPage(strapDetailEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("strap:strapDetailEntity:view")
	@RequestMapping(value = "form")
	public String form(StrapDetailEntity strapDetailEntity, Model model) {
		model.addAttribute("strapDetailEntity", strapDetailEntity);
		return "modules/strap/strapDetailEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("strap:strapDetailEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated StrapDetailEntity strapDetailEntity) {
		strapDetailEntityService.save(strapDetailEntity);
		return renderResult(Global.TRUE, text("保存压板成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("strap:strapDetailEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(StrapDetailEntity strapDetailEntity) {
		strapDetailEntityService.delete(strapDetailEntity);
		return renderResult(Global.TRUE, text("删除压板成功！"));
	}
	
}