package dev.trailsgroup.trailsproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trailsgroup.trailsproject.entities.enums.UserProfiles;
import dev.trailsgroup.trailsproject.services.StaticFileService;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String image;

    @JsonIgnore
    private String password;

    @Column(unique=true)
    private String email;

    private Boolean status;

    @OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfessorCourse> professorCourses =  new HashSet<>();

    @OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentCourse> studentCourses =  new HashSet<>();

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="tb_profiles")    
    private Set<Integer> profiles =  new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentSubject> studentSubject = new ArrayList<>();

    public User(){
        addProfile(UserProfiles.USER);
        addProfile(UserProfiles.PROFESSOR);
    }

    public User(Integer id, String name, String password, String email, String image, Boolean status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email.toLowerCase();
        this.image = image;
        this.status = status == null || status;
        addProfile(UserProfiles.USER);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserProfiles> getProfiles(){
        return profiles.stream().map(x -> UserProfiles.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(UserProfiles profile){
        profiles.add(profile.getCod());
    }
    public void removeProfile(UserProfiles profile){
        profiles.remove(profile.getCod());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImagePath() {
        return  StaticFileService.getInstance().getIp() + "/uploads/" + image;
    }

    @JsonIgnore
    public String getImageName(){
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    public Instant getCreatedDate() {
        return createdDate;
    }

    @JsonIgnore
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @JsonIgnore
    public Set<Course> getCourses(){
        Set<Course> set = new HashSet<>();
        for(ProfessorCourse x : professorCourses){
            set.add(x.getCourse());
        }
        return set;
    }

}
