package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.GptDiscordChannel;

/**
 * Discord 频道Service接口
 * 
 * @author GptAdmin
 * @date 2023-05-20
 */
public interface IGptDiscordChannelService 
{
    /**
     * 查询Discord 频道
     * 
     * @param id Discord 频道主键
     * @return Discord 频道
     */
    public GptDiscordChannel selectGptDiscordChannelById(Long id);

    /**
     * 查询Discord 频道列表
     * 
     * @param gptDiscordChannel Discord 频道
     * @return Discord 频道集合
     */
    public List<GptDiscordChannel> selectGptDiscordChannelList(GptDiscordChannel gptDiscordChannel);

    /**
     * 新增Discord 频道
     * 
     * @param gptDiscordChannel Discord 频道
     * @return 结果
     */
    public int insertGptDiscordChannel(GptDiscordChannel gptDiscordChannel);

    /**
     * 修改Discord 频道
     * 
     * @param gptDiscordChannel Discord 频道
     * @return 结果
     */
    public int updateGptDiscordChannel(GptDiscordChannel gptDiscordChannel);

    /**
     * 批量删除Discord 频道
     * 
     * @param ids 需要删除的Discord 频道主键集合
     * @return 结果
     */
    public int deleteGptDiscordChannelByIds(Long[] ids);

    /**
     * 删除Discord 频道信息
     * 
     * @param id Discord 频道主键
     * @return 结果
     */
    public int deleteGptDiscordChannelById(Long id);
}
