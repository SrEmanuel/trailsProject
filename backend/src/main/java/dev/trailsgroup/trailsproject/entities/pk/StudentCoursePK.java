package dev.trailsgroup.trailsproject.entities.pk;

import dev.trailsgroup.trailsproject.entities.Course;
import dev.trailsgroup.trailsproject.entities.User;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class StudentCoursePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public StudentCoursePK(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    public StudentCoursePK() {

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
        StudentCoursePK that = (StudentCoursePK) o;
        return user.equals(that.user) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course);
    }
}
