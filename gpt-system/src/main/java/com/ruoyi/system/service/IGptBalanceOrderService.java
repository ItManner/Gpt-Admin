package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.GptBalanceOrder;

/**
 * 余额充值订单Service接口
 * 
 * @author GptAdmin
 * @date 2023-05-16
 */
public interface IGptBalanceOrderService 
{
    /**
     * 查询余额充值订单
     * 
     * @param orderId 余额充值订单主键
     * @return 余额充值订单
     */
    public GptBalanceOrder selectGptBalanceOrderByOrderId(Long orderId);

    /**
     * 查询余额充值订单列表
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 余额充值订单集合
     */
    public List<GptBalanceOrder> selectGptBalanceOrderList(GptBalanceOrder gptBalanceOrder);

    /**
     * 新增余额充值订单
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 结果
     */
    public int insertGptBalanceOrder(GptBalanceOrder gptBalanceOrder);

    /**
     * 修改余额充值订单
     * 
     * @param gptBalanceOrder 余额充值订单
     * @return 结果
     */
    public int updateGptBalanceOrder(GptBalanceOrder gptBalanceOrder);

    /**
     * 批量删除余额充值订单
     * 
     * @param orderIds 需要删除的余额充值订单主键集合
     * @return 结果
     */
    public int deleteGptBalanceOrderByOrderIds(Long[] orderIds);

    /**
     * 删除余额充值订单信息
     * 
     * @param orderId 余额充值订单主键
     * @return 结果
     */
    public int deleteGptBalanceOrderByOrderId(Long orderId);
}
