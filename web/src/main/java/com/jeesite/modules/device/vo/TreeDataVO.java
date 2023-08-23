package com.jeesite.modules.device.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TreeDataVO {
    private String id;
    @JsonProperty("pId")
    private String pId;
    private String code;
    private String name;
    private String title;
    private Boolean isParent;

    TreeDataVO(){}
    public TreeDataVO(String id, String pId, String name, Boolean isParent){
        this.id = id;
        this.code = id;
        this.pId = pId;
        this.name = name;
        this.title = name;
        this.isParent = isParent;
    }
}
