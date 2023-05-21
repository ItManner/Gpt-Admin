package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptOpenaiConfigMapper;
import com.ruoyi.system.domain.GptOpenaiConfig;
import com.ruoyi.system.service.IGptOpenaiConfigService;

/**
 * openai配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-21
 */
@Service
public class GptOpenaiConfigServiceImpl implements IGptOpenaiConfigService 
{
    @Autowired
    private GptOpenaiConfigMapper gptOpenaiConfigMapper;

    /**
     * 查询openai配置
     * 
     * @param id openai配置主键
     * @return openai配置
     */
    @Override
    public GptOpenaiConfig selectGptOpenaiConfigById(Long id)
    {
        return gptOpenaiConfigMapper.selectGptOpenaiConfigById(id);
    }

    /**
     * 查询openai配置列表
     * 
     * @param gptOpenaiConfig openai配置
     * @return openai配置
     */
    @Override
    public List<GptOpenaiConfig> selectGptOpenaiConfigList(GptOpenaiConfig gptOpenaiConfig)
    {
        return gptOpenaiConfigMapper.selectGptOpenaiConfigList(gptOpenaiConfig);
    }

    /**
     * 新增openai配置
     * 
     * @param gptOpenaiConfig openai配置
     * @return 结果
     */
    @Override
    public int insertGptOpenaiConfig(GptOpenaiConfig gptOpenaiConfig)
    {
        return gptOpenaiConfigMapper.insertGptOpenaiConfig(gptOpenaiConfig);
    }

    /**
     * 修改openai配置
     * 
     * @param gptOpenaiConfig openai配置
     * @return 结果
     */
    @Override
    public int updateGptOpenaiConfig(GptOpenaiConfig gptOpenaiConfig)
    {
        return gptOpenaiConfigMapper.updateGptOpenaiConfig(gptOpenaiConfig);
    }

    /**
     * 批量删除openai配置
     * 
     * @param ids 需要删除的openai配置主键
     * @return 结果
     */
    @Override
    public int deleteGptOpenaiConfigByIds(Long[] ids)
    {
        return gptOpenaiConfigMapper.deleteGptOpenaiConfigByIds(ids);
    }

    /**
     * 删除openai配置信息
     * 
     * @param id openai配置主键
     * @return 结果
     */
    @Override
    public int deleteGptOpenaiConfigById(Long id)
    {
        return gptOpenaiConfigMapper.deleteGptOpenaiConfigById(id);
    }
}
