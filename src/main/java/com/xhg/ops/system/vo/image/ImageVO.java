package com.xhg.ops.system.vo.image;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("图片信息")
public class ImageVO implements Serializable {
    @ApiModelProperty("图片地址")
    private String url;
    @ApiModelProperty("图片名称")
    private String imgName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

}