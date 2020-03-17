package com.zzw.config;

import com.zzw.pojo.User;
import com.zzw.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义一个 Realm
public class UserRealm extends AuthorizingRealm {
    // 注入 service
    @Autowired
    private UserService userService;

    // 执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑~");
        // 对资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加资源的授权字符串，正常操作需要从数据库中获取需要授权的字符串数据
        // info.addStringPermission("user:add");
        // 获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        // subject.getPrincipal(); 从执行认证逻辑中第一个参数传来的，拿到当前用户对象
        User currentUser = (User) subject.getPrincipal();
        // 设置当前用户是否允许访问某些资源权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    // 执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行认证逻辑~");
        // 获取当前登录的用户 token
        UsernamePasswordToken userToken = (UsernamePasswordToken) arg0;
        // 查询数据库
        User user = userService.findByName(userToken.getUsername());

        // 用户名不存在，注意 null 写前面，避免写的时候不小心写成 user = null
        if (null == user) {
            // shiro 底层会抛出 UnKnowAccountException
            return null;
        }
        // 设置 session 保存用户登录状态
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser", user);
        // 剩下的密码认证交给 shiro 底层去完成
        // 第一个参数 principal：需要传入当前登录的用户 user 才能在授权操作时对资源进行访问！
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
