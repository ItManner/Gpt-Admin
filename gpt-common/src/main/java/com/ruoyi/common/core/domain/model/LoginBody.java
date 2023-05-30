package com.ruoyi.common.core.domain.model;

import com.ruoyi.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@ApiModel(value = "登录对象")
public class LoginBody
{

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码结果", example = "5", required = true)
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "验证码UUID", example = "aaa-bbb-5555-vvv", required = true)
    private String uuid;


    /**
     * 设置部门  后台好统计和区分 方便其他功能拓展
     */
    @ApiModelProperty(value = "登录不需要传,注册时只传dept.deptId即可 固定值200", example = "200", required = true)
    private SysDept dept;

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
