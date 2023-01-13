package dev.trailsgroup.trailsproject.entities.pk;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.context.annotation.Bean;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserCoursePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public UserCoursePK(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public UserCoursePK() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCoursePK that = (UserCoursePK) o;
        return user.equals(that.user) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course);
    }
}
