package kg.neobis.fms.models.security;

import kg.neobis.fms.entity.Role;
import kg.neobis.fms.entity.User;
import kg.neobis.fms.entity.enums.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private Set<GrantedAuthority> authorities;
    private User user;

    public MyUserDetails(User user, Role role) {
        this.user = user;
        authorities = new HashSet<>();
        for(Permission permission: role.getPermissions())
            authorities.add(new SimpleGrantedAuthority(permission.toString()));
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public User getUserDetails() {
        return user;
    }
}