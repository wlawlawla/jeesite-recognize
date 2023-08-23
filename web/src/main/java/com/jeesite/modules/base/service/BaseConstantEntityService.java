package com.jeesite.modules.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeesite.modules.base.vo.ConstantVO;
import com.jeesite.modules.utils.VOUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.base.entity.BaseConstantEntity;
import com.jeesite.modules.base.dao.BaseConstantEntityDao;

import javax.annotation.PostConstruct;

/**
 * base_constantService
 * @author w
 * @version 2023-08-22
 */
@Service
public class BaseConstantEntityService extends CrudService<BaseConstantEntityDao, BaseConstantEntity> {


	private Map<String, List<ConstantVO>> constantMap = new HashMap<>(16);

	/**
	 * 获取单条数据
	 * @param baseConstantEntity
	 * @return
	 */
	@Override
	public BaseConstantEntity get(BaseConstantEntity baseConstantEntity) {
		return super.get(baseConstantEntity);
	}
	
	/**
	 * 查询分页数据
	 * @param baseConstantEntity 查询条件
	 * @param baseConstantEntity page 分页对象
	 * @return
	 */
	@Override
	public Page<BaseConstantEntity> findPage(BaseConstantEntity baseConstantEntity) {
		return super.findPage(baseConstantEntity);
	}
	
	/**
	 * 查询列表数据
	 * @param baseConstantEntity
	 * @return
	 */
	@Override
	public List<BaseConstantEntity> findList(BaseConstantEntity baseConstantEntity) {
		return super.findList(baseConstantEntity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param baseConstantEntity
	 */
	@Override
	@Transactional
	public void save(BaseConstantEntity baseConstantEntity) {
		super.save(baseConstantEntity);
	}
	
	/**
	 * 更新状态
	 * @param baseConstantEntity
	 */
	@Override
	@Transactional
	public void updateStatus(BaseConstantEntity baseConstantEntity) {
		super.updateStatus(baseConstantEntity);
	}
	
	/**
	 * 删除数据
	 * @param baseConstantEntity
	 */
	@Override
	@Transactional
	public void delete(BaseConstantEntity baseConstantEntity) {
		super.delete(baseConstantEntity);
	}

	@PostConstruct
	public void refresh(){
		constantMap.clear();
		List<BaseConstantEntity> baseConstantEntityList = dao.findAll();
		if (CollectionUtils.isEmpty(baseConstantEntityList)){
			return;
		}

		List<ConstantVO> constantVOList = VOUtil.getVOList(ConstantVO.class, baseConstantEntityList);

		constantMap.putAll(constantVOList.stream().collect(Collectors.groupingBy(ConstantVO::getCtype)));
	}

	public List<ConstantVO> getConstantByType(String type){
		return constantMap.get(type);
	}

}