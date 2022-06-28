package dev.trailsgroup.trailsproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trailsgroup.trailsproject.dto.CompetencePointsDTO;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSSDTO implements UserDetails {

    private Integer id;
    private String email;
    private String name;
    private String image;

    private List<CompetencePointsDTO> competencePoints = new ArrayList<>();

    @JsonIgnore
    private String password;

    private Boolean status;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSSDTO() {

    }

    public UserSSDTO(Integer id, String email, String name, String password, Set<UserProfiles> profiles, Boolean status, String image) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
        this.status = status;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CompetencePointsDTO> getCompetencePoints() {
        return competencePoints;
    }

    public void setCompetencePoints(List<CompetencePointsDTO> competencePoints) {
        this.competencePoints = competencePoints;
    }

    public boolean hasRole(UserProfiles profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
    }

    public String getImagePath() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getRoles(){
        return authorities.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
