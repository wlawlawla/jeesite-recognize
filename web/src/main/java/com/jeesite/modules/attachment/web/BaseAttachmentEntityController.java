package com.jeesite.modules.attachment.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.attachment.vo.AttachmentVO;
import com.jeesite.modules.file.entity.FileUploadParams;
import com.jeesite.modules.file.web.FileUploadController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.attachment.entity.BaseAttachmentEntity;
import com.jeesite.modules.attachment.service.BaseAttachmentEntityService;
import org.springframework.web.multipart.MultipartFile;

/**
 * base_attachmentController
 * @author w
 * @version 2023-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/attachment")
public class BaseAttachmentEntityController extends BaseController {

	@Autowired
	private BaseAttachmentEntityService baseAttachmentEntityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BaseAttachmentEntity get(Long tid, boolean isNewRecord) {
		return baseAttachmentEntityService.get(tid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("attachment:baseAttachmentEntity:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseAttachmentEntity baseAttachmentEntity, Model model) {
		model.addAttribute("baseAttachmentEntity", baseAttachmentEntity);
		return "modules/attachment/baseAttachmentEntityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("attachment:baseAttachmentEntity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BaseAttachmentEntity> listData(BaseAttachmentEntity baseAttachmentEntity, HttpServletRequest request, HttpServletResponse response) {
		baseAttachmentEntity.setPage(new Page<>(request, response));
		Page<BaseAttachmentEntity> page = baseAttachmentEntityService.findPage(baseAttachmentEntity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("attachment:baseAttachmentEntity:view")
	@RequestMapping(value = "form")
	public String form(BaseAttachmentEntity baseAttachmentEntity, Model model) {
		model.addAttribute("baseAttachmentEntity", baseAttachmentEntity);
		return "modules/attachment/baseAttachmentEntityForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("attachment:baseAttachmentEntity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BaseAttachmentEntity baseAttachmentEntity) {
		baseAttachmentEntityService.save(baseAttachmentEntity);
		return renderResult(Global.TRUE, text("保存base_attachment成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("attachment:baseAttachmentEntity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BaseAttachmentEntity baseAttachmentEntity) {
		baseAttachmentEntityService.delete(baseAttachmentEntity);
		return renderResult(Global.TRUE, text("删除base_attachment成功！"));
	}

/*	@RequestMapping({"upload"})
	@ResponseBody
	public AttachmentVO upload(@RequestParam("file") MultipartFile file) {
*//*		FileUploadController var10000 = this;
		FileUploadController var2 = var1;
		FileUploadParams params = var10000;*//*
		AttachmentVO attachmentVO = baseAttachmentEntityService.uploadAttachment(file, 1, 123l);
		return attachmentVO;
	}*/

	/**
	 * 下载文件
	 * @param attachmentId
	 * @param response
	 */
	@GetMapping("/{attachmentId}")
	public void downloadAttachment(@PathVariable Long attachmentId, HttpServletResponse response){
		baseAttachmentEntityService.downloadImageById(attachmentId, response);
	}
	
}