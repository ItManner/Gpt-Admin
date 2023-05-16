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
import com.ruoyi.system.domain.GptDiscordChannel;
import com.ruoyi.system.service.IGptDiscordChannelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Discord 频道Controller
 *
 * @author GptAdmin
 * @date 2023-05-16
 */
@RestController
@RequestMapping("/system/discord")
public class GptDiscordChannelController extends BaseController
{
    @Autowired
    private IGptDiscordChannelService gptDiscordChannelService;

    /**
     * 查询Discord 频道列表
     */
    @PreAuthorize("@ss.hasPermi('system:discord:list')")
    @GetMapping("/list")
    public TableDataInfo list(GptDiscordChannel gptDiscordChannel)
    {
        startPage();
        List<GptDiscordChannel> list = gptDiscordChannelService.selectGptDiscordChannelList(gptDiscordChannel);
        return getDataTable(list);
    }

    /**
     * 导出Discord 频道列表
     */
    @PreAuthorize("@ss.hasPermi('system:discord:export')")
    @Log(title = "Discord 频道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GptDiscordChannel gptDiscordChannel)
    {
        List<GptDiscordChannel> list = gptDiscordChannelService.selectGptDiscordChannelList(gptDiscordChannel);
        ExcelUtil<GptDiscordChannel> util = new ExcelUtil<GptDiscordChannel>(GptDiscordChannel.class);
        util.exportExcel(response, list, "Discord 频道数据");
    }

    /**
     * 获取Discord 频道详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:discord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gptDiscordChannelService.selectGptDiscordChannelById(id));
    }

    /**
     * 新增Discord 频道
     */
    @PreAuthorize("@ss.hasPermi('system:discord:add')")
    @Log(title = "Discord 频道", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GptDiscordChannel gptDiscordChannel)
    {
        return toAjax(gptDiscordChannelService.insertGptDiscordChannel(gptDiscordChannel));
    }

    /**
     * 修改Discord 频道
     */
    @PreAuthorize("@ss.hasPermi('system:discord:edit')")
    @Log(title = "Discord 频道", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GptDiscordChannel gptDiscordChannel)
    {
        return toAjax(gptDiscordChannelService.updateGptDiscordChannel(gptDiscordChannel));
    }

    /**
     * 删除Discord 频道
     */
    @PreAuthorize("@ss.hasPermi('system:discord:remove')")
    @Log(title = "Discord 频道", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gptDiscordChannelService.deleteGptDiscordChannelByIds(ids));
    }
}
