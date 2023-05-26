package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptPackageMapper;
import com.ruoyi.system.domain.GptPackage;
import com.ruoyi.system.service.IGptPackageService;

/**
 * 套餐Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@Service
public class GptPackageServiceImpl implements IGptPackageService 
{
    @Autowired
    private GptPackageMapper gptPackageMapper;

    /**
     * 查询套餐
     * 
     * @param id 套餐主键
     * @return 套餐
     */
    @Override
    public GptPackage selectGptPackageById(Long id)
    {
        return gptPackageMapper.selectGptPackageById(id);
    }

    /**
     * 查询套餐列表
     * 
     * @param gptPackage 套餐
     * @return 套餐
     */
    @Override
    public List<GptPackage> selectGptPackageList(GptPackage gptPackage)
    {
        return gptPackageMapper.selectGptPackageList(gptPackage);
    }

    /**
     * 新增套餐
     * 
     * @param gptPackage 套餐
     * @return 结果
     */
    @Override
    public int insertGptPackage(GptPackage gptPackage)
    {
        gptPackage.setCreateTime(DateUtils.getNowDate());
        return gptPackageMapper.insertGptPackage(gptPackage);
    }

    /**
     * 修改套餐
     * 
     * @param gptPackage 套餐
     * @return 结果
     */
    @Override
    public int updateGptPackage(GptPackage gptPackage)
    {
        return gptPackageMapper.updateGptPackage(gptPackage);
    }

    /**
     * 批量删除套餐
     * 
     * @param ids 需要删除的套餐主键
     * @return 结果
     */
    @Override
    public int deleteGptPackageByIds(Long[] ids)
    {
        return gptPackageMapper.deleteGptPackageByIds(ids);
    }

    /**
     * 删除套餐信息
     * 
     * @param id 套餐主键
     * @return 结果
     */
    @Override
    public int deleteGptPackageById(Long id)
    {
        return gptPackageMapper.deleteGptPackageById(id);
    }
}
