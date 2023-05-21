package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptBalanceOrderMapper;
import com.ruoyi.system.domain.GptBalanceOrder;
import com.ruoyi.system.service.IGptBalanceOrderService;

/**
 * 余额充值订单Service业务层处理
 * 
 * @author GptAdmin
 * @date 2023-05-20
 */
@Service
public class GptBalanceOrderServiceImpl implements IGptBalanceOrderService 
{
    @Autowired
    private GptBalanceOrderMapper gptBalanceOrderMapper;

    /**
     * 查询余额充值订单
     * 
     * @param orderId 余额充值订单主键
     * @return 余额充值订单
     */
    @Override
    public GptBalanceOrder selectGptBalanceOrderByOrderId(Long orderId)
    {
        return gptBalanceOrderMapper.selectGptBalanceOrderByOrderId(orderId);
    }

    /**
     * 查询余额充值订单列表
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 余额充值订单
     */
    @Override
    public List<GptBalanceOrder> selectGptBalanceOrderList(GptBalanceOrder gptBalanceOrder)
    {
        return gptBalanceOrderMapper.selectGptBalanceOrderList(gptBalanceOrder);
    }

    /**
     * 新增余额充值订单
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 结果
     */
    @Override
    public int insertGptBalanceOrder(GptBalanceOrder gptBalanceOrder)
    {
        return gptBalanceOrderMapper.insertGptBalanceOrder(gptBalanceOrder);
    }

    /**
     * 修改余额充值订单
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 结果
     */
    @Override
    public int updateGptBalanceOrder(GptBalanceOrder gptBalanceOrder)
    {
        return gptBalanceOrderMapper.updateGptBalanceOrder(gptBalanceOrder);
    }

    /**
     * 批量删除余额充值订单
     * 
     * @param orderIds 需要删除的余额充值订单主键
     * @return 结果
     */
    @Override
    public int deleteGptBalanceOrderByOrderIds(Long[] orderIds)
    {
        return gptBalanceOrderMapper.deleteGptBalanceOrderByOrderIds(orderIds);
    }

    /**
     * 删除余额充值订单信息
     * 
     * @param orderId 余额充值订单主键
     * @return 结果
     */
    @Override
    public int deleteGptBalanceOrderByOrderId(Long orderId)
    {
        return gptBalanceOrderMapper.deleteGptBalanceOrderByOrderId(orderId);
    }
}
