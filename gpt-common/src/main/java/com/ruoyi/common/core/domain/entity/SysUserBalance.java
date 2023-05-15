package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户余额对象 sys_user_balance
 *
 * @author ruoyi
 * @date 2023-05-15
 */
public class SysUserBalance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 关联用户ID */
    @Excel(name = "关联用户ID")
    private Long userId;

    /** 用户余额 */
    @Excel(name = "用户余额")
    private BigDecimal balance;

    /** 余额过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "余额过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiredTime;

    /** 总的充值金额 */
    @Excel(name = "总的充值金额")
    private BigDecimal totalRecharge;

    /** 总的消费金额 */
    @Excel(name = "总的消费金额")
    private BigDecimal totalConsume;

    /** 消费次数 */
    @Excel(name = "消费次数")
    private Long consumptionTimes;

    /** 最后一次消费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次消费时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastConsumptionTime;

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
    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }
    public void setExpiredTime(Date expiredTime)
    {
        this.expiredTime = expiredTime;
    }

    public Date getExpiredTime()
    {
        return expiredTime;
    }
    public void setTotalRecharge(BigDecimal totalRecharge)
    {
        this.totalRecharge = totalRecharge;
    }

    public BigDecimal getTotalRecharge()
    {
        return totalRecharge;
    }
    public void setTotalConsume(BigDecimal totalConsume)
    {
        this.totalConsume = totalConsume;
    }

    public BigDecimal getTotalConsume()
    {
        return totalConsume;
    }
    public void setConsumptionTimes(Long consumptionTimes)
    {
        this.consumptionTimes = consumptionTimes;
    }

    public Long getConsumptionTimes()
    {
        return consumptionTimes;
    }
    public void setLastConsumptionTime(Date lastConsumptionTime)
    {
        this.lastConsumptionTime = lastConsumptionTime;
    }

    public Date getLastConsumptionTime()
    {
        return lastConsumptionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("balance", getBalance())
            .append("expiredTime", getExpiredTime())
            .append("totalRecharge", getTotalRecharge())
            .append("totalConsume", getTotalConsume())
            .append("consumptionTimes", getConsumptionTimes())
            .append("lastConsumptionTime", getLastConsumptionTime())
            .toString();
    }
}
