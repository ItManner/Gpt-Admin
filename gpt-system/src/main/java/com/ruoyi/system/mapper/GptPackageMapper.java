package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GptPackage;

/**
 * 套餐Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public interface GptPackageMapper 
{
    /**
     * 查询套餐
     * 
     * @param id 套餐主键
     * @return 套餐
     */
    public GptPackage selectGptPackageById(Long id);

    /**
     * 查询套餐列表
     * 
     * @param gptPackage 套餐
     * @return 套餐集合
     */
    public List<GptPackage> selectGptPackageList(GptPackage gptPackage);

    /**
     * 新增套餐
     * 
     * @param gptPackage 套餐
     * @return 结果
     */
    public int insertGptPackage(GptPackage gptPackage);

    /**
     * 修改套餐
     * 
     * @param gptPackage 套餐
     * @return 结果
     */
    public int updateGptPackage(GptPackage gptPackage);

    /**
     * 删除套餐
     * 
     * @param id 套餐主键
     * @return 结果
     */
    public int deleteGptPackageById(Long id);

    /**
     * 批量删除套餐
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGptPackageByIds(Long[] ids);
}
