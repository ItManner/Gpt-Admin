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
import com.ruoyi.common.core.domain.entity.SysUserBalance;
import com.ruoyi.system.service.ISysUserBalanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户余额Controller
 *
 * @author ruoyi
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/system/balance")
public class SysUserBalanceController extends BaseController
{
    @Autowired
    private ISysUserBalanceService sysUserBalanceService;

    /**
     * 查询用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('system:balance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserBalance sysUserBalance)
    {
        startPage();
        List<SysUserBalance> list = sysUserBalanceService.selectSysUserBalanceList(sysUserBalance);
        return getDataTable(list);
    }

    /**
     * 导出用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('system:balance:export')")
    @Log(title = "用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserBalance sysUserBalance)
    {
        List<SysUserBalance> list = sysUserBalanceService.selectSysUserBalanceList(sysUserBalance);
        ExcelUtil<SysUserBalance> util = new ExcelUtil<SysUserBalance>(SysUserBalance.class);
        util.exportExcel(response, list, "用户余额数据");
    }

    /**
     * 获取用户余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:balance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysUserBalanceService.selectSysUserBalanceById(id));
    }

    /**
     * 新增用户余额
     */
    @PreAuthorize("@ss.hasPermi('system:balance:add')")
    @Log(title = "用户余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserBalance sysUserBalance)
    {
        return toAjax(sysUserBalanceService.insertSysUserBalance(sysUserBalance));
    }

    /**
     * 修改用户余额
     */
    @PreAuthorize("@ss.hasPermi('system:balance:edit')")
    @Log(title = "用户余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserBalance sysUserBalance)
    {
        return toAjax(sysUserBalanceService.updateSysUserBalance(sysUserBalance));
    }

    /**
     * 删除用户余额
     */
    @PreAuthorize("@ss.hasPermi('system:balance:remove')")
    @Log(title = "用户余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysUserBalanceService.deleteSysUserBalanceByIds(ids));
    }
}
