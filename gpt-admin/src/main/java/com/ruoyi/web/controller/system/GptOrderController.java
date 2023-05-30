package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.GptPackage;
import com.ruoyi.system.mapper.GptPackageMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.GptOrder;
import com.ruoyi.system.service.IGptOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author ruoyi
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/system/order")
@Api(tags = "订单功能")
public class GptOrderController extends BaseController
{
    @Autowired
    private IGptOrderService gptOrderService;
    @Autowired
    private GptPackageMapper gptPackageMapper;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(GptOrder gptOrder)
    {
        startPage();
        List<GptOrder> list = gptOrderService.selectGptOrderList(gptOrder);
        return getDataTable(list);
    }

    @GetMapping("/myOrderList")
    @ApiOperation("查询我的订单列表")
    public TableDataInfo myOrderList()
    {
        startPage();
        List<GptOrder> list = gptOrderService.selectGptOrderListByUserId(SecurityUtils.getUserId());
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GptOrder gptOrder)
    {
        List<GptOrder> list = gptOrderService.selectGptOrderList(gptOrder);
        ExcelUtil<GptOrder> util = new ExcelUtil<GptOrder>(GptOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("查询订单详情")
    public AjaxResult getInfo(@PathVariable("id") @ApiParam("订单ID") Long id)
    {
        return success(gptOrderService.selectGptOrderById(id));
    }

    /**
     * 新增订单
     */
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/createOrder")
    public AjaxResult add(@RequestBody GptOrder gptOrder)
    {
        GptPackage gptPackage = gptPackageMapper.selectGptPackageById(gptOrder.getPackageId());
        if (ObjectUtils.isEmpty(gptPackage)){
            return AjaxResult.error("创建订单失败，套餐不存在");
        }
        gptOrder.setOrderCode("Gpt" + "_" + UUID.randomUUID());
        //设置未支付状态
        gptOrder.setPayStatus("0");
        gptOrder.setUserId(SecurityUtils.getUserId());
        gptOrder.setCreateBy(SecurityUtils.getUsername());
        return toAjax(gptOrderService.insertGptOrder(gptOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GptOrder gptOrder)
    {
        return toAjax(gptOrderService.updateGptOrder(gptOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gptOrderService.deleteGptOrderByIds(ids));
    }
}
