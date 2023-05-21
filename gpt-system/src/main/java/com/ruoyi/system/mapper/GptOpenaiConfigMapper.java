package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GptOpenaiConfig;

/**
 * openai配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-21
 */
public interface GptOpenaiConfigMapper 
{
    /**
     * 查询openai配置
     * 
     * @param id openai配置主键
     * @return openai配置
     */
    public GptOpenaiConfig selectGptOpenaiConfigById(Long id);

    /**
     * 查询openai配置列表
     * 
     * @param gptOpenaiConfig openai配置
     * @return openai配置集合
     */
    public List<GptOpenaiConfig> selectGptOpenaiConfigList(GptOpenaiConfig gptOpenaiConfig);

    /**
     * 新增openai配置
     * 
     * @param gptOpenaiConfig openai配置
     * @return 结果
     */
    public int insertGptOpenaiConfig(GptOpenaiConfig gptOpenaiConfig);

    /**
     * 修改openai配置
     * 
     * @param gptOpenaiConfig openai配置
     * @return 结果
     */
    public int updateGptOpenaiConfig(GptOpenaiConfig gptOpenaiConfig);

    /**
     * 删除openai配置
     * 
     * @param id openai配置主键
     * @return 结果
     */
    public int deleteGptOpenaiConfigById(Long id);

    /**
     * 批量删除openai配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGptOpenaiConfigByIds(Long[] ids);


    List<GptOpenaiConfig> selectList();
}
