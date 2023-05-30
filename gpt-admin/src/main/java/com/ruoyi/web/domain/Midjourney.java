package com.ruoyi.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Lan HuaZeng
 * @Date: 2023/5/29 16:51
 */
@Data
@ApiModel(value = "Midjourney对象")
public class Midjourney {
    @ApiModelProperty(value = "动作: 必传，IMAGINE（绘图）、UPSCALE（选中放大）、VARIATION（选中变换）", example = "IMAGINE", required = true)
    private String action;
    @ApiModelProperty(value = "绘图参数: IMAGINE时必传", example = "a dog", required = false)
    private String prompt;
    @ApiModelProperty(value = "任务ID: UPSCALE、VARIATION时必传（查询任务接口返回的taskId）", example = "12345688888", required = false)
    private String taskId;
    @ApiModelProperty(value = "图序号: 1～4，UPSCALE、VARIATION时必传，表示第几张图", example = "4", required = false)
    private String index;
    @ApiModelProperty(value = "自定义字符串: 非必传，供回调到业务系统里使用", example = "", required = false)
    private String state;
    @ApiModelProperty(value = "支持每个任务配置不同回调地址，非必传", example = "https://www.baidu.com", required = false)
    private String notifyHook;
}
