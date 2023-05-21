package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * openai配置对象 gpt_openai_config
 * 
 * @author ruoyi
 * @date 2023-05-21
 */
public class GptOpenaiConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** openaikey */
    @Excel(name = "openaikey")
    private String openaiKey;

    /** 模型 */
    @Excel(name = "模型")
    private String model;

    /** 最大返回结果 */
    @Excel(name = "最大返回结果")
    private String maxToken;

    /** 思维发善程度 */
    @Excel(name = "思维发善程度")
    private String temperature;

    /** 超时时间 */
    @Excel(name = "超时时间")
    private String timeout;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOpenaiKey(String openaiKey) 
    {
        this.openaiKey = openaiKey;
    }

    public String getOpenaiKey() 
    {
        return openaiKey;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setMaxToken(String maxToken) 
    {
        this.maxToken = maxToken;
    }

    public String getMaxToken() 
    {
        return maxToken;
    }
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }
    public void setTimeout(String timeout) 
    {
        this.timeout = timeout;
    }

    public String getTimeout() 
    {
        return timeout;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openaiKey", getOpenaiKey())
            .append("model", getModel())
            .append("maxToken", getMaxToken())
            .append("temperature", getTemperature())
            .append("timeout", getTimeout())
            .toString();
    }
}
