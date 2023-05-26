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
import com.ruoyi.system.domain.GptUserPackage;
import com.ruoyi.system.service.IGptUserPackageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 套餐和用户关联Controller
 *
 * @author ruoyi
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/system/userPackage")
public class GptUserPackageController extends BaseController
{
    @Autowired
    private IGptUserPackageService gptUserPackageService;

    /**
     * 查询套餐和用户关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:list')")
    @GetMapping("/list")
    public TableDataInfo list(GptUserPackage gptUserPackage)
    {
        startPage();
        List<GptUserPackage> list = gptUserPackageService.selectGptUserPackageList(gptUserPackage);
        return getDataTable(list);
    }

    /**
     * 导出套餐和用户关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:export')")
    @Log(title = "套餐和用户关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GptUserPackage gptUserPackage)
    {
        List<GptUserPackage> list = gptUserPackageService.selectGptUserPackageList(gptUserPackage);
        ExcelUtil<GptUserPackage> util = new ExcelUtil<GptUserPackage>(GptUserPackage.class);
        util.exportExcel(response, list, "套餐和用户关联数据");
    }

    /**
     * 获取套餐和用户关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gptUserPackageService.selectGptUserPackageById(id));
    }

    /**
     * 新增套餐和用户关联
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:add')")
    @Log(title = "套餐和用户关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GptUserPackage gptUserPackage)
    {
        return toAjax(gptUserPackageService.insertGptUserPackage(gptUserPackage));
    }

    /**
     * 修改套餐和用户关联
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:edit')")
    @Log(title = "套餐和用户关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GptUserPackage gptUserPackage)
    {
        return toAjax(gptUserPackageService.updateGptUserPackage(gptUserPackage));
    }

    /**
     * 删除套餐和用户关联
     */
    @PreAuthorize("@ss.hasPermi('system:userPackage:remove')")
    @Log(title = "套餐和用户关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gptUserPackageService.deleteGptUserPackageByIds(ids));
    }
}
