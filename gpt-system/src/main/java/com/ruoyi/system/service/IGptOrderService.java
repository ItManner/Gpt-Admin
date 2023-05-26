package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.GptOrder;

/**
 * 订单Service接口
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public interface IGptOrderService 
{
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    public GptOrder selectGptOrderById(Long id);

    /**
     * 查询订单列表
     * 
     * @param gptOrder 订单
     * @return 订单集合
     */
    public List<GptOrder> selectGptOrderList(GptOrder gptOrder);

    /**
     * 新增订单
     * 
     * @param gptOrder 订单
     * @return 结果
     */
    public int insertGptOrder(GptOrder gptOrder);

    /**
     * 修改订单
     * 
     * @param gptOrder 订单
     * @return 结果
     */
    public int updateGptOrder(GptOrder gptOrder);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteGptOrderByIds(Long[] ids);

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    public int deleteGptOrderById(Long id);
}
