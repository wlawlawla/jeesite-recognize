package com.jeesite.modules.screen.web;

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
import com.jeesite.modules.screen.entity.StrapScreenEntity;
import com.jeesite.modules.screen.service.StrapScreenEntityService;

/**
 * strap_screenController
 * @author w
 * @version 2023-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/screen/strapScreenEntity")
public class StrapScreenEntityController extends BaseController {

	@Autowired
	private StrapScreenEntityService strapScreenEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public StrapScreenEntity get(Long screenId, boolean isNewRecord) {
		return strapScreenEntityService.get(screenId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("screen:strapScreenEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(StrapScreenEntity strapScreenEntity, Model model) {
		model.addAttribute("strapScreenEntity", strapScreenEntity);
		return "modules/screen/strapScreenEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("screen:strapScreenEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<StrapScreenEntity> listData(StrapScreenEntity strapScreenEntity, HttpServletRequest request, HttpServletResponse response) {
		strapScreenEntity.setPage(new Page<>(request, response));
		Page<StrapScreenEntity> page = strapScreenEntityService.findPage(strapScreenEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("screen:strapScreenEntity:view")
	@RequestMapping(value = "form")
	public String form(StrapScreenEntity strapScreenEntity, Model model) {
		model.addAttribute("strapScreenEntity", strapScreenEntity);
		return "modules/screen/strapScreenEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("screen:strapScreenEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated StrapScreenEntity strapScreenEntity) {
		strapScreenEntityService.save(strapScreenEntity);
		return renderResult(Global.TRUE, text("保存strap_screen成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("screen:strapScreenEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(StrapScreenEntity strapScreenEntity) {
		strapScreenEntityService.delete(strapScreenEntity);
		return renderResult(Global.TRUE, text("删除strap_screen成功！"));
	}
	
}