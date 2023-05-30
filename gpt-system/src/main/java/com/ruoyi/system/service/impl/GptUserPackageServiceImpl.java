package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptUserPackageMapper;
import com.ruoyi.system.domain.GptUserPackage;
import com.ruoyi.system.service.IGptUserPackageService;
import org.springframework.util.ObjectUtils;

/**
 * 套餐和用户关联Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-26
 */
@Service
public class GptUserPackageServiceImpl implements IGptUserPackageService
{
    @Autowired
    private GptUserPackageMapper gptUserPackageMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 查询套餐和用户关联
     *
     * @param id 套餐和用户关联主键
     * @return 套餐和用户关联
     */
    @Override
    public GptUserPackage selectGptUserPackageById(Long id)
    {
        return gptUserPackageMapper.selectGptUserPackageById(id);
    }

    /**
     * 查询套餐和用户关联列表
     *
     * @param gptUserPackage 套餐和用户关联
     * @return 套餐和用户关联
     */
    @Override
    public List<GptUserPackage> selectGptUserPackageList(GptUserPackage gptUserPackage)
    {
        return gptUserPackageMapper.selectGptUserPackageList(gptUserPackage);
    }

    /**
     * 新增套餐和用户关联
     *
     * @param gptUserPackage 套餐和用户关联
     * @return 结果
     */
    @Override
    public int insertGptUserPackage(GptUserPackage gptUserPackage)
    {
        return gptUserPackageMapper.insertGptUserPackage(gptUserPackage);
    }

    /**
     * 修改套餐和用户关联
     *
     * @param gptUserPackage 套餐和用户关联
     * @return 结果
     */
    @Override
    public int updateGptUserPackage(GptUserPackage gptUserPackage)
    {
        return gptUserPackageMapper.updateGptUserPackage(gptUserPackage);
    }

    /**
     * 批量删除套餐和用户关联
     *
     * @param ids 需要删除的套餐和用户关联主键
     * @return 结果
     */
    @Override
    public int deleteGptUserPackageByIds(Long[] ids)
    {
        return gptUserPackageMapper.deleteGptUserPackageByIds(ids);
    }

    /**
     * 删除套餐和用户关联信息
     *
     * @param id 套餐和用户关联主键
     * @return 结果
     */
    @Override
    public int deleteGptUserPackageById(Long id)
    {
        return gptUserPackageMapper.deleteGptUserPackageById(id);
    }

    @Override
    public List<GptUserPackage> selectGptUserPackageListByUserId(Long userId) {
        return gptUserPackageMapper.selectGptUserPackageAllByUserId(userId);
    }

    @Override
    public Long myPackageNum(Long userId) {
        String packageNum = redisTemplate.opsForValue().get(userId + "packageNum");
        if (!ObjectUtils.isEmpty(packageNum)){
            return Long.valueOf(packageNum);
        }
        List<GptUserPackage> gptUserPackages = gptUserPackageMapper.selectGptUserPackageListByUserId(userId);
        Long remainingCountSum = gptUserPackages.stream()
                .mapToLong(GptUserPackage::getRemainingCount)
                .sum();
        redisTemplate.opsForValue().set(userId + "packageNum", String.valueOf(remainingCountSum),10, TimeUnit.MINUTES);
        return remainingCountSum;
    }
}
