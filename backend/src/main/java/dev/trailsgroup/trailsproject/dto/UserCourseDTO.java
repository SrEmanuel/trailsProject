package dev.trailsgroup.trailsproject.dto;

public class UserCourseDTO {

    private Integer userId;
    private Integer courseId;

    public UserCourseDTO(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
