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
import com.ruoyi.system.domain.GptOpenaiConfig;
import com.ruoyi.system.service.IGptOpenaiConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * openai配置Controller
 * 
 * @author ruoyi
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/system/openai")
public class GptOpenaiConfigController extends BaseController
{
    @Autowired
    private IGptOpenaiConfigService gptOpenaiConfigService;

    /**
     * 查询openai配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:openai:list')")
    @GetMapping("/list")
    public TableDataInfo list(GptOpenaiConfig gptOpenaiConfig)
    {
        startPage();
        List<GptOpenaiConfig> list = gptOpenaiConfigService.selectGptOpenaiConfigList(gptOpenaiConfig);
        return getDataTable(list);
    }

    /**
     * 导出openai配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:openai:export')")
    @Log(title = "openai配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GptOpenaiConfig gptOpenaiConfig)
    {
        List<GptOpenaiConfig> list = gptOpenaiConfigService.selectGptOpenaiConfigList(gptOpenaiConfig);
        ExcelUtil<GptOpenaiConfig> util = new ExcelUtil<GptOpenaiConfig>(GptOpenaiConfig.class);
        util.exportExcel(response, list, "openai配置数据");
    }

    /**
     * 获取openai配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:openai:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gptOpenaiConfigService.selectGptOpenaiConfigById(id));
    }

    /**
     * 新增openai配置
     */
    @PreAuthorize("@ss.hasPermi('system:openai:add')")
    @Log(title = "openai配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GptOpenaiConfig gptOpenaiConfig)
    {
        gptOpenaiConfig.setModel("gpt-3.5-turbo");
        gptOpenaiConfig.setTemperature("0");
        return toAjax(gptOpenaiConfigService.insertGptOpenaiConfig(gptOpenaiConfig));
    }

    /**
     * 修改openai配置
     */
    @PreAuthorize("@ss.hasPermi('system:openai:edit')")
    @Log(title = "openai配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GptOpenaiConfig gptOpenaiConfig)
    {
        gptOpenaiConfig.setModel("gpt-3.5-turbo");
        gptOpenaiConfig.setTemperature("0");
        return toAjax(gptOpenaiConfigService.updateGptOpenaiConfig(gptOpenaiConfig));
    }

    /**
     * 删除openai配置
     */
    @PreAuthorize("@ss.hasPermi('system:openai:remove')")
    @Log(title = "openai配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gptOpenaiConfigService.deleteGptOpenaiConfigByIds(ids));
    }
}
