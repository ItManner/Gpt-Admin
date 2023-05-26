package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GptOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 订单Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-26
 */
public interface GptOrderMapper
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
     * 删除订单
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteGptOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGptOrderByIds(Long[] ids);

    GptOrder selectGptOrderByOrderCode(@Param("orderCode") String mchOrderNo);
}
