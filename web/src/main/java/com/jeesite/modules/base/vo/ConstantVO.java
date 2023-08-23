package com.jeesite.modules.base.vo;

import lombok.Data;

import java.util.List;

@Data
public class ConstantVO {
    /**
     * 主键
     */
    private Long cid;

    /**
     * 类型
     */
    private String ctype;

    /**
     * 代码
     */
    private String ccode;

    /**
     * 值
     */
    private String cvalue;

    /**
     * 父节点id
     */
    private String pid;

    /**
     * 父节点code
     */
    private String pcode;

    /**
     * 排序
     */
    private String orderNum;

    /**
     * 子节点
     */
    private List<ConstantVO> children;
}
