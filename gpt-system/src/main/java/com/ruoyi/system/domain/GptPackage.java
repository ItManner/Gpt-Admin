package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套餐对象 gpt_package
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public class GptPackage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐ID */
    private Long id;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String name;

    /** 套餐限制使用次数 */
    @Excel(name = "套餐限制使用次数")
    private Long limitCount;

    /** 套餐金额 */
    @Excel(name = "套餐金额")
    private BigDecimal amount;

    /** 套餐有效期 */
    @Excel(name = "套餐有效期")
    private Long validTime;

    /** 套餐描述 */
    @Excel(name = "套餐描述")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setLimitCount(Long limitCount) 
    {
        this.limitCount = limitCount;
    }

    public Long getLimitCount() 
    {
        return limitCount;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setValidTime(Long validTime) 
    {
        this.validTime = validTime;
    }

    public Long getValidTime() 
    {
        return validTime;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("limitCount", getLimitCount())
            .append("createTime", getCreateTime())
            .append("amount", getAmount())
            .append("validTime", getValidTime())
            .append("description", getDescription())
            .toString();
    }
}
