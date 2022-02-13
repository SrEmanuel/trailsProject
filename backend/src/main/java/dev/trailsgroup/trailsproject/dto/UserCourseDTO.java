package dev.trailsgroup.trailsproject.dto;

public class UserCourseDTO {

    private Integer courseId;
    private Integer userId;


    public UserCourseDTO() {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
