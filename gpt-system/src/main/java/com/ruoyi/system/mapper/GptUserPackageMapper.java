package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GptUserPackage;

/**
 * 套餐和用户关联Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public interface GptUserPackageMapper 
{
    /**
     * 查询套餐和用户关联
     * 
     * @param id 套餐和用户关联主键
     * @return 套餐和用户关联
     */
    public GptUserPackage selectGptUserPackageById(Long id);

    /**
     * 查询套餐和用户关联列表
     * 
     * @param gptUserPackage 套餐和用户关联
     * @return 套餐和用户关联集合
     */
    public List<GptUserPackage> selectGptUserPackageList(GptUserPackage gptUserPackage);

    /**
     * 新增套餐和用户关联
     * 
     * @param gptUserPackage 套餐和用户关联
     * @return 结果
     */
    public int insertGptUserPackage(GptUserPackage gptUserPackage);

    /**
     * 修改套餐和用户关联
     * 
     * @param gptUserPackage 套餐和用户关联
     * @return 结果
     */
    public int updateGptUserPackage(GptUserPackage gptUserPackage);

    /**
     * 删除套餐和用户关联
     * 
     * @param id 套餐和用户关联主键
     * @return 结果
     */
    public int deleteGptUserPackageById(Long id);

    /**
     * 批量删除套餐和用户关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGptUserPackageByIds(Long[] ids);
}
