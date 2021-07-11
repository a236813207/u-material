package com.ken.material.auth;

import com.ken.material.entity.SysUser;
import com.ken.material.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();

        SysUser user = this.userService.getByUsername(username);
        //没找到帐号
        if(user == null) {
            throw new UnknownAccountException("Account not exist");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //用户
                user,
                //密码
                user.getPassword(),
                //salt=username+salt
                ByteSource.Util.bytes(user.getSalt()),
                //realm name
                getName()
        );
        return authenticationInfo;
    }
}
