package com.ruoyi.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Lan HuaZeng
 * @Date: 2023/5/30 11:01
 */
@Data
@ApiModel(value = "Pay对象")
public class Pay {
    @ApiModelProperty(value = "套餐列表页面发起支付需要填", example = "1", required = false)
    private String packageId;
    @ApiModelProperty(value = "订单列表页面发起支付需要填", example = "1", required = false)
    private String orderId;

}
