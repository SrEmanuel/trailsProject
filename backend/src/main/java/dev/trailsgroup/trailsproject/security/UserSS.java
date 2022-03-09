package dev.trailsgroup.trailsproject.security;

import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {

    private Integer id;
    private String email;
    private String name;
    private String password;
    private Boolean status;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(){

    }

    public UserSS(Integer id, String email, String name, String password, Set<UserProfiles> profiles, Boolean status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
        this.status = status;
    }

    public Integer getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
    public String toString() {
        return "{\"id\": " + id + ", "
                + "\"nome\": \""+ name+"\", "
                + "\"email\": \""+ email +"\", "
                + "\"profiles\": \""+ authorities.toString() +"\", "
                + "\"status\": "+ status+" }";
    }

}
