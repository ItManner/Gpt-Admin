package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 gpt_order
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public class GptOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 套餐ID */
    @Excel(name = "套餐ID")
    private Long packageId;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal amount;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderCode;

    /** 支付状态 0未支付 1已支付 */
    @Excel(name = "支付状态 0未支付 1已支付")
    private String payStatus;

    /** 商品标题 */
    @Excel(name = "商品标题")
    private String goodsTitle;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPackageId(Long packageId) 
    {
        this.packageId = packageId;
    }

    public Long getPackageId() 
    {
        return packageId;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setPayStatus(String payStatus) 
    {
        this.payStatus = payStatus;
    }

    public String getPayStatus() 
    {
        return payStatus;
    }
    public void setGoodsTitle(String goodsTitle) 
    {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsTitle() 
    {
        return goodsTitle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("packageId", getPackageId())
            .append("createTime", getCreateTime())
            .append("amount", getAmount())
            .append("orderCode", getOrderCode())
            .append("payStatus", getPayStatus())
            .append("goodsTitle", getGoodsTitle())
            .toString();
    }
}
