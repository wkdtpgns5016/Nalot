package com.example.nalot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 174726374856727L;

    UserDto userDto;
    boolean locked;	//계정 잠김 여부
    Collection<GrantedAuthority> authorities;	//권한 목록

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userDto.getId();
    }

    @Override
    public String getPassword() { return userDto.getPassword(); }

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
    public boolean isEnabled() { return true; }
}
