package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套餐和用户关联对象 gpt_user_package
 *
 * @author ruoyi
 * @date 2023-05-26
 */
public class GptUserPackage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关联表ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 套餐ID */
    @Excel(name = "套餐ID")
    private Long packageId;

    /** 套餐剩余消费次数 */
    @Excel(name = "套餐剩余消费次数")
    private Long remainingCount;

    /** 套餐到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "套餐到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 是否过期 */
    @Excel(name = "是否过期 1有效 0失效")
    private String isExpire;

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
    public void setRemainingCount(Long remainingCount)
    {
        this.remainingCount = remainingCount;
    }

    public Long getRemainingCount()
    {
        return remainingCount;
    }
    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }
    public void setIsExpire(String isExpire)
    {
        this.isExpire = isExpire;
    }

    public String getIsExpire()
    {
        return isExpire;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("packageId", getPackageId())
            .append("remainingCount", getRemainingCount())
            .append("expireTime", getExpireTime())
            .append("isExpire", getIsExpire())
            .toString();
    }
}
