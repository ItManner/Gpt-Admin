package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.system.domain.GptBalanceOrder;
import com.ruoyi.system.service.IGptBalanceOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 余额充值订单Controller
 * 
 * @author GptAdmin
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/system/order")
public class GptBalanceOrderController extends BaseController
{
    @Autowired
    private IGptBalanceOrderService gptBalanceOrderService;

    /**
     * 查询余额充值订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(GptBalanceOrder gptBalanceOrder)
    {
        startPage();
        List<GptBalanceOrder> list = gptBalanceOrderService.selectGptBalanceOrderList(gptBalanceOrder);
        return getDataTable(list);
    }

    /**
     * 导出余额充值订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "余额充值订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GptBalanceOrder gptBalanceOrder)
    {
        List<GptBalanceOrder> list = gptBalanceOrderService.selectGptBalanceOrderList(gptBalanceOrder);
        ExcelUtil<GptBalanceOrder> util = new ExcelUtil<GptBalanceOrder>(GptBalanceOrder.class);
        util.exportExcel(response, list, "余额充值订单数据");
    }

    /**
     * 获取余额充值订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(gptBalanceOrderService.selectGptBalanceOrderByOrderId(orderId));
    }

    /**
     * 新增余额充值订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "余额充值订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GptBalanceOrder gptBalanceOrder)
    {
        return toAjax(gptBalanceOrderService.insertGptBalanceOrder(gptBalanceOrder));
    }

    /**
     * 修改余额充值订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "余额充值订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GptBalanceOrder gptBalanceOrder)
    {
        return toAjax(gptBalanceOrderService.updateGptBalanceOrder(gptBalanceOrder));
    }

    /**
     * 删除余额充值订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "余额充值订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(gptBalanceOrderService.deleteGptBalanceOrderByOrderIds(orderIds));
    }
}
