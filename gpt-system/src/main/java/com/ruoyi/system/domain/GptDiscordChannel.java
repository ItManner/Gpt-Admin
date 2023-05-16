package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Discord 频道对象 gpt_discord_channel
 * 
 * @author GptAdmin
 * @date 2023-05-16
 */
public class GptDiscordChannel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** Discord 服务器ID */
    @Excel(name = "Discord 服务器ID")
    private String discordServerId;

    /** Discord 频道ID */
    @Excel(name = "Discord 频道ID")
    private String discordChannelId;

    /** Discord Bot Token */
    @Excel(name = "Discord Bot Token")
    private String discordToken;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 删除标识 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDiscordServerId(String discordServerId) 
    {
        this.discordServerId = discordServerId;
    }

    public String getDiscordServerId() 
    {
        return discordServerId;
    }
    public void setDiscordChannelId(String discordChannelId) 
    {
        this.discordChannelId = discordChannelId;
    }

    public String getDiscordChannelId() 
    {
        return discordChannelId;
    }
    public void setDiscordToken(String discordToken) 
    {
        this.discordToken = discordToken;
    }

    public String getDiscordToken() 
    {
        return discordToken;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("discordServerId", getDiscordServerId())
            .append("discordChannelId", getDiscordChannelId())
            .append("discordToken", getDiscordToken())
            .append("createdTime", getCreatedTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
