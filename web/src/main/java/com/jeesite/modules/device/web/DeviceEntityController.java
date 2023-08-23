package com.jeesite.modules.device.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.device.entity.DeviceEntity;
import com.jeesite.modules.device.service.DeviceEntityService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备Controller
 * @author w
 * @version 2023-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/device/deviceEntity")
public class DeviceEntityController extends BaseController {

	@Autowired
	private DeviceEntityService deviceEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DeviceEntity get(Long deviceId, boolean isNewRecord) {
		return deviceEntityService.get(deviceId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("device:deviceEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeviceEntity deviceEntity, Model model) {
		model.addAttribute("deviceEntity", deviceEntity);
		return "modules/device/deviceEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("device:deviceEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DeviceEntity> listData(DeviceEntity deviceEntity, HttpServletRequest request, HttpServletResponse response) {
		deviceEntity.setPage(new Page<>(request, response));
		Page<DeviceEntity> page = deviceEntityService.findPage(deviceEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("device:deviceEntity:view")
	@RequestMapping(value = "form")
	public String form(DeviceEntity deviceEntity, Model model) {
		model.addAttribute("deviceEntity", deviceEntity);
		return "modules/device/deviceEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("device:deviceEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DeviceEntity deviceEntity) {
		deviceEntityService.save(deviceEntity);
		return renderResult(Global.TRUE, text("保存设备成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("device:deviceEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DeviceEntity deviceEntity) {
		deviceEntityService.delete(deviceEntity);
		return renderResult(Global.TRUE, text("删除设备成功！"));
	}

	@PostMapping("/import")
	public void importDevice(@RequestParam(value = "file") MultipartFile file) throws IOException {
		deviceEntityService.importDeviceInfo(file);
	}
	
}