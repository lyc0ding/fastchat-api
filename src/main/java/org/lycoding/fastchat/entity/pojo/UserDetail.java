package org.lycoding.fastchat.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: lycoding
 * @Description: 实现UserDetails，作为LoginService中验证通过返回的UserDetails
 * @DateTime: 2024/9/8 22:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements UserDetails {
    private UserInfo userInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override

    public String getPassword() {
        return userInfo.getPassword();
    }
//    判断用户是用的啥登录
    @Override
    public String getUsername() {
        return userInfo.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
