package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptOrderMapper;
import com.ruoyi.system.domain.GptOrder;
import com.ruoyi.system.service.IGptOrderService;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-26
 */
@Service
public class GptOrderServiceImpl implements IGptOrderService
{
    @Autowired
    private GptOrderMapper gptOrderMapper;

    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public GptOrder selectGptOrderById(Long id)
    {
        return gptOrderMapper.selectGptOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param gptOrder 订单
     * @return 订单
     */
    @Override
    public List<GptOrder> selectGptOrderList(GptOrder gptOrder)
    {
        return gptOrderMapper.selectGptOrderList(gptOrder);
    }

    /**
     * 新增订单
     *
     * @param gptOrder 订单
     * @return 结果
     */
    @Override
    public int insertGptOrder(GptOrder gptOrder)
    {
        gptOrder.setCreateTime(DateUtils.getNowDate());
        return gptOrderMapper.insertGptOrder(gptOrder);
    }

    /**
     * 修改订单
     *
     * @param gptOrder 订单
     * @return 结果
     */
    @Override
    public int updateGptOrder(GptOrder gptOrder)
    {
        return gptOrderMapper.updateGptOrder(gptOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteGptOrderByIds(Long[] ids)
    {
        return gptOrderMapper.deleteGptOrderByIds(ids);
    }

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteGptOrderById(Long id)
    {
        return gptOrderMapper.deleteGptOrderById(id);
    }

    @Override
    public List<GptOrder> selectGptOrderListByUserId(Long userId) {
        return gptOrderMapper.selectGptOrderListByUserId(userId);
    }
}
