package com.jeesite.modules.station.web;

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
import com.jeesite.modules.station.entity.StationEntity;
import com.jeesite.modules.station.service.StationEntityService;

/**
 * station_infoController
 * @author w
 * @version 2023-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/station/stationEntity")
public class StationEntityController extends BaseController {

	@Autowired
	private StationEntityService stationEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public StationEntity get(Long stationId, boolean isNewRecord) {
		return stationEntityService.get(stationId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("station:stationEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(StationEntity stationEntity, Model model) {
		model.addAttribute("stationEntity", stationEntity);
		return "modules/station/stationEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("station:stationEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<StationEntity> listData(StationEntity stationEntity, HttpServletRequest request, HttpServletResponse response) {
		stationEntity.setPage(new Page<>(request, response));
		Page<StationEntity> page = stationEntityService.findPage(stationEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("station:stationEntity:view")
	@RequestMapping(value = "form")
	public String form(StationEntity stationEntity, Model model) {
		model.addAttribute("stationEntity", stationEntity);
		return "modules/station/stationEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("station:stationEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated StationEntity stationEntity) {
		stationEntityService.save(stationEntity);
		return renderResult(Global.TRUE, text("保存station_info成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("station:stationEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(StationEntity stationEntity) {
		stationEntityService.delete(stationEntity);
		return renderResult(Global.TRUE, text("删除station_info成功！"));
	}
	
}