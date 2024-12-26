package org.sid.appbackser.services;

import java.util.Collection;
import java.util.Objects;

import lombok.Data;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.GroupeAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Data
public class UserDetailsImp implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private  GrantedAuthority authorities;

    public UserDetailsImp(Long id, String username, String email, String password,
                            GrantedAuthority authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities =  authorities;
    }

    public static UserDetailsImp build(Account user) {
        GrantedAuthority authorities = null;
        for( GroupeAccount g : user.getGroupeAccounts()){
            if(g.getRole().getRoleTypes().name() == "Default"){
                authorities  = new SimpleGrantedAuthority(g.getRole().getRoleTypes().name());
                break;
            }
        }
        /*List<GrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
*/
        return new UserDetailsImp(
                user.getId(),
                user.getUsername(),
                user.getMail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImp user = (UserDetailsImp) o;
        return Objects.equals(id, user.id);
    }
}
