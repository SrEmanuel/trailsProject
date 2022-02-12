package dev.trailsgroup.trailsproject.entities;

import dev.trailsgroup.trailsproject.entities.pk.UserCoursePK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_user_course")
public class UserCourse implements Serializable {

    @EmbeddedId
    private UserCoursePK id = new UserCoursePK();

    public UserCourse(){}

    public UserCourse(UserCoursePK id, Course course, User user) {
        id.setCourse(course);
        id.setUser(user);
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