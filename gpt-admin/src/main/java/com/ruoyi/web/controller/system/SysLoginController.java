package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUserBalance;
import com.ruoyi.system.service.ISysUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private ISysUserBalanceService iSysUserBalanceService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        JSONObject result = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, result.getString("token"));
        return ajax;
    }

    /**
     * 兼容GPT登录方法
     *
     * @param params 登录信息
     * @return 结果
     */
    @PostMapping("/gptLogin")
    public R gptLogin(@RequestBody JSONObject params)
    {
        LoginBody loginBody = new LoginBody();
        loginBody.setCode(params.getJSONObject("captchaComp").getString("captchaCode"));
        loginBody.setUsername(params.getString("username"));
        loginBody.setPassword(params.getString("password"));
        loginBody.setUuid(params.getJSONObject("captchaComp").getString("captchaKey"));
        AjaxResult ajax = AjaxResult.success();

        // 生成令牌
        JSONObject result = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());

        ajax.put(Constants.TOKEN,   result.getString("token"));
        return R.ok(result);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getGptInfo")
    public R getGptInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
//        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(user);
//        // 权限集合
//        Set<String> permissions = permissionService.getMenuPermission(user);
//        AjaxResult ajax = AjaxResult.success();
        JSONObject userInfo = new JSONObject();
        userInfo.put("id", user.getUserId());
        userInfo.put("account", user.getUserName());
        userInfo.put("nickname", user.getNickName());
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("role", ObjectUtils.isEmpty(user.getRoles()) ? "user":user.getRoles().get(0).getRoleKey());
        userInfo.put("status", user.getStatus());
        SysUserBalance balance = iSysUserBalanceService.selectSysUserBalanceByUserId(user.getUserId());
        userInfo.put("integral", balance.getBalance());
        R<Object> objectR = new R<>();
        objectR.setData(userInfo);
        return objectR;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
