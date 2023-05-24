package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserBalanceMapper;
import com.ruoyi.common.core.domain.entity.SysUserBalance;
import com.ruoyi.system.service.ISysUserBalanceService;

/**
 * 用户余额Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-15
 */
@Service
public class SysUserBalanceServiceImpl implements ISysUserBalanceService
{
    @Autowired
    private SysUserBalanceMapper sysUserBalanceMapper;

    /**
     * 查询用户余额
     *
     * @param id 用户余额主键
     * @return 用户余额
     */
    @Override
    public SysUserBalance selectSysUserBalanceById(Long id)
    {
        return sysUserBalanceMapper.selectSysUserBalanceById(id);
    }

    /**
     * 查询用户余额列表
     *
     * @param sysUserBalance 用户余额
     * @return 用户余额
     */
    @Override
    public List<SysUserBalance> selectSysUserBalanceList(SysUserBalance sysUserBalance)
    {
        return sysUserBalanceMapper.selectSysUserBalanceList(sysUserBalance);
    }

    /**
     * 新增用户余额
     *
     * @param sysUserBalance 用户余额
     * @return 结果
     */
    @Override
    public int insertSysUserBalance(SysUserBalance sysUserBalance)
    {
        return sysUserBalanceMapper.insertSysUserBalance(sysUserBalance);
    }

    /**
     * 修改用户余额
     *
     * @param sysUserBalance 用户余额
     * @return 结果
     */
    @Override
    public int updateSysUserBalance(SysUserBalance sysUserBalance)
    {
        return sysUserBalanceMapper.updateSysUserBalance(sysUserBalance);
    }

    /**
     * 批量删除用户余额
     *
     * @param ids 需要删除的用户余额主键
     * @return 结果
     */
    @Override
    public int deleteSysUserBalanceByIds(Long[] ids)
    {
        return sysUserBalanceMapper.deleteSysUserBalanceByIds(ids);
    }

    /**
     * 删除用户余额信息
     *
     * @param id 用户余额主键
     * @return 结果
     */
    @Override
    public int deleteSysUserBalanceById(Long id)
    {
        return sysUserBalanceMapper.deleteSysUserBalanceById(id);
    }

    @Override
    public void initUserBalance(SysUser sysUser) {
        SysUserBalance sysUserBalance = new SysUserBalance();
        sysUserBalance.setUserId(sysUser.getUserId());
        sysUserBalance.setBalance(BigDecimal.ZERO);
        sysUserBalance.setExpiredTime(new Date());
        sysUserBalance.setTotalRecharge(BigDecimal.ZERO);
        sysUserBalance.setTotalConsume(BigDecimal.ZERO);
        sysUserBalance.setConsumptionTimes(0L);
        sysUserBalanceMapper.insertSysUserBalance(sysUserBalance);
    }

    @Override
    public SysUserBalance selectSysUserBalanceByUserId(Long userId) {
        return sysUserBalanceMapper.selectSysUserBalanceById(userId);
    }
}
