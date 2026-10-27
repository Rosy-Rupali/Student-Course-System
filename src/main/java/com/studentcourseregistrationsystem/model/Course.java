package com.studentcourseregistrationsystem.model;

public class Course {
    private int courseId;
    private String courseName;
    private double fee;

    public Course(int courseId, String courseName, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    public Course(String courseName, double fee) {
        this.courseName = courseName;
        this.fee = fee;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getFee() {
        return fee;
    }
}
