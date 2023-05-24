package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserBalance;

/**
 * 用户余额Service接口
 *
 * @author ruoyi
 * @date 2023-05-15
 */
public interface ISysUserBalanceService
{
    /**
     * 查询用户余额
     *
     * @param id 用户余额主键
     * @return 用户余额
     */
    public SysUserBalance selectSysUserBalanceById(Long id);

    /**
     * 查询用户余额列表
     *
     * @param sysUserBalance 用户余额
     * @return 用户余额集合
     */
    public List<SysUserBalance> selectSysUserBalanceList(SysUserBalance sysUserBalance);

    /**
     * 新增用户余额
     *
     * @param sysUserBalance 用户余额
     * @return 结果
     */
    public int insertSysUserBalance(SysUserBalance sysUserBalance);

    /**
     * 修改用户余额
     *
     * @param sysUserBalance 用户余额
     * @return 结果
     */
    public int updateSysUserBalance(SysUserBalance sysUserBalance);

    /**
     * 批量删除用户余额
     *
     * @param ids 需要删除的用户余额主键集合
     * @return 结果
     */
    public int deleteSysUserBalanceByIds(Long[] ids);

    /**
     * 删除用户余额信息
     *
     * @param id 用户余额主键
     * @return 结果
     */
    public int deleteSysUserBalanceById(Long id);

    void initUserBalance(SysUser sysUser);

    SysUserBalance selectSysUserBalanceByUserId(Long userId);
}
