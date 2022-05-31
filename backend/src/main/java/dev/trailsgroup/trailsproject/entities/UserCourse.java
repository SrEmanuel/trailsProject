package dev.trailsgroup.trailsproject.entities;

import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_usercourse")
public class UserCourse implements Serializable {

    @EmbeddedId
    private UserCoursePK id = new UserCoursePK();

    public UserCourse(){}


    private Integer countCompleted;

    public UserCourse(Course course, User user) {
        id.setCourse(course);
        id.setUser(user);
        this.countCompleted = 0;
    }

    public Course getCourse(){
        return id.getCourse();
    }

    public void setCourse(Course course){
        id.setCourse(course);
    }

    public User getUser(){
        return id.getUser();
    }

    public void setUser(User user){
        id.setUser(user);
    }


    public Integer getCountCompleted() {
        return countCompleted;
    }

    public void setCountCompleted(Integer countCompleted) {
        this.countCompleted = countCompleted;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourse that = (UserCourse) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
