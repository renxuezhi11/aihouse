package com.aihouse.aihousesys.config;

import com.aihouse.aihousedao.bean.SysAuth;
import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihouseservice.SysAuthService;
import com.aihouse.aihouseservice.SysRoleService;
import com.aihouse.aihouseservice.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysAuthService sysAuthService;

    @Resource
    private SysRoleService sysRoleService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo  = (SysUser)principals.getPrimaryPrincipal();
        List<SysRole> roleList=sysRoleService.queryByUser(userInfo);
        if(roleList!=null&&roleList.size()>0){
            for(SysRole role:roleList){
                authorizationInfo.addRole(role.getName());
                List<SysAuth> authList=sysAuthService.queryByRole(role);
                if(authList!=null&&authList.size()>0){
                    for(SysAuth auth :authList){
                        if(auth.getHref()!=null&&!auth.getHref().equals("")){
                            authorizationInfo.addStringPermission(auth.getHref());
                        }
                    }
                }
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        String username = (String)utoken.getUsername();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        List<SysUser> user=sysUserService.selectByUsername(username);
        if(user!=null&&user.size()>0){
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.get(0), //用户名
                    user.get(0).getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }else{
            return null;
        }

    }
}
