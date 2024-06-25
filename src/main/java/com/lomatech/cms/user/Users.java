package com.lomatech.cms.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@TableName("users")
public class Users implements UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;


    public Users(String userName, String password, List<GrantedAuthority> authorities, boolean accountNonExpired) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Users withGrantedAuthorities(List<GrantedAuthority> authorities){
        this.authorities = authorities;
        return this;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public Users withPassword(String password){
        this.password =  password;
        return this;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public Users withUserName(String userName){
        this.userName = userName;
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public Users withAccountNonExpired(boolean isEnabled){
        this.accountNonExpired = isEnabled;
        return this;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
