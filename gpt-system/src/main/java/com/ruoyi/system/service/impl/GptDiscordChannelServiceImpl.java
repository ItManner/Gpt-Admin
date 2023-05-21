package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GptDiscordChannelMapper;
import com.ruoyi.system.domain.GptDiscordChannel;
import com.ruoyi.system.service.IGptDiscordChannelService;

/**
 * Discord 频道Service业务层处理
 * 
 * @author GptAdmin
 * @date 2023-05-20
 */
@Service
public class GptDiscordChannelServiceImpl implements IGptDiscordChannelService 
{
    @Autowired
    private GptDiscordChannelMapper gptDiscordChannelMapper;

    /**
     * 查询Discord 频道
     * 
     * @param id Discord 频道主键
     * @return Discord 频道
     */
    @Override
    public GptDiscordChannel selectGptDiscordChannelById(Long id)
    {
        return gptDiscordChannelMapper.selectGptDiscordChannelById(id);
    }

    /**
     * 查询Discord 频道列表
     * 
     * @param gptDiscordChannel Discord 频道
     * @return Discord 频道
     */
    @Override
    public List<GptDiscordChannel> selectGptDiscordChannelList(GptDiscordChannel gptDiscordChannel)
    {
        return gptDiscordChannelMapper.selectGptDiscordChannelList(gptDiscordChannel);
    }

    /**
     * 新增Discord 频道
     * 
     * @param gptDiscordChannel Discord 频道
     * @return 结果
     */
    @Override
    public int insertGptDiscordChannel(GptDiscordChannel gptDiscordChannel)
    {
        return gptDiscordChannelMapper.insertGptDiscordChannel(gptDiscordChannel);
    }

    /**
     * 修改Discord 频道
     * 
     * @param gptDiscordChannel Discord 频道
     * @return 结果
     */
    @Override
    public int updateGptDiscordChannel(GptDiscordChannel gptDiscordChannel)
    {
        return gptDiscordChannelMapper.updateGptDiscordChannel(gptDiscordChannel);
    }

    /**
     * 批量删除Discord 频道
     * 
     * @param ids 需要删除的Discord 频道主键
     * @return 结果
     */
    @Override
    public int deleteGptDiscordChannelByIds(Long[] ids)
    {
        return gptDiscordChannelMapper.deleteGptDiscordChannelByIds(ids);
    }

    /**
     * 删除Discord 频道信息
     * 
     * @param id Discord 频道主键
     * @return 结果
     */
    @Override
    public int deleteGptDiscordChannelById(Long id)
    {
        return gptDiscordChannelMapper.deleteGptDiscordChannelById(id);
    }
}
