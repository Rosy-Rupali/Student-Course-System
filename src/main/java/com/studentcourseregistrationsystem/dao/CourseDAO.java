package com.studentcourseregistrationsystem.dao;

import com.studentcourseregistrationsystem.model.Course;
import com.studentcourseregistrationsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public void addCourse(Course c) throws SQLException {
        String sql = "INSERT INTO courses(course_name, fee) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseName());
            ps.setDouble(2, c.getFee());
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0)
                System.out.println("Course is added.");
            else System.err.println("Failed to add course.");
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();

        String sql = "SELECT course_id, course_name, fee FROM courses";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getDouble("fee")
                ));
            }
        }
        return courses;
    }

    public void updateCourse(Course c) throws SQLException {
        String sql = "UPDATE courses SET course_name = ?, fee = ? WHERE course_id = ? ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseName());
            ps.setDouble(2, c.getFee());
            ps.setInt(3, c.getCourseId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Course is updated.");
            else System.err.println("Failed to update course.");
        }
    }

    public void deleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Course is deleted.");
            else System.err.println("Failed to delete the course.");
        }
    }
}
