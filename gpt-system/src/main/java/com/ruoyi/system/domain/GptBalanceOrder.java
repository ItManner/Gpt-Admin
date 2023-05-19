package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 余额充值订单对象 gpt_balance_order
 *
 * @author GptAdmin
 * @date 2023-05-16
 */
@Data
public class GptBalanceOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long orderId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 充值金额 */
    @Excel(name = "充值金额")
    private BigDecimal rechargeAmount;

    /** 充值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "充值时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rechargeTime;

    /** 充值状态（0：待处理，1：已处理） */
    @Excel(name = "充值状态", readConverterExp = "0=：待处理，1：已处理")
    private Integer rechargeStatus;


    /** 商户订单号 */
    @Excel(name = "商户订单号")
    private String orderCode;

    /** 商品订单标题 */
    @Excel(name = "商品订单标题")
    private String goodsTitle;

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setRechargeAmount(BigDecimal rechargeAmount)
    {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRechargeAmount()
    {
        return rechargeAmount;
    }
    public void setRechargeTime(Date rechargeTime)
    {
        this.rechargeTime = rechargeTime;
    }

    public Date getRechargeTime()
    {
        return rechargeTime;
    }
    public void setRechargeStatus(Integer rechargeStatus)
    {
        this.rechargeStatus = rechargeStatus;
    }

    public Integer getRechargeStatus()
    {
        return rechargeStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("userId", getUserId())
            .append("rechargeAmount", getRechargeAmount())
            .append("rechargeTime", getRechargeTime())
            .append("rechargeStatus", getRechargeStatus())
            .toString();
    }
}
