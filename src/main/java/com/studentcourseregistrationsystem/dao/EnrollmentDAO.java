
package com.studentcourseregistrationsystem.dao;

import com.studentcourseregistrationsystem.util.DBConnection;
import java.sql.*;

public class EnrollmentDAO {

    // Enroll a student in a course
    public void enrollStudent(int studentId, int courseId) throws SQLException {

        String checkStudent = "SELECT student_id FROM students WHERE student_id = ?";
        String checkCourse = "SELECT course_id FROM courses WHERE course_id = ?";
        String insert = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                // Validate student
                try (PreparedStatement ps = con.prepareStatement(checkStudent)) {
                    ps.setInt(1, studentId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Student ID does not exist.");
                        }
                    }
                }

                // Validate course
                try (PreparedStatement ps = con.prepareStatement(checkCourse)) {
                    ps.setInt(1, courseId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Course ID does not exist.");
                        }
                    }
                }

                // Insert enrollment
                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, courseId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Enrollment successful.");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // Display students enrolled in a particular course
    public void showStudentsByCourse(int courseId) throws SQLException {

        String sql = "SELECT s.student_id, s.name, s.email, c.course_name " +
                "FROM students s " +
                "JOIN enrollments e ON s.student_id = e.student_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "WHERE c.course_id = ? " +
                "ORDER BY s.name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "ID: " + rs.getInt("student_id") +
                                    " | Name: " + rs.getString("name") +
                                    " | Email: " + rs.getString("email") +
                                    " | Course: " + rs.getString("course_name")
                    );
                }

                if (!found) {
                    System.out.println("No enrolled students found for this course.");
                }
            }
        }
    }

    // Display courses taken by a particular student
    public void showCoursesByStudent(int studentId) throws SQLException {

        String sql = "SELECT c.course_id, c.course_name, c.fee " +
                "FROM courses c " +
                "JOIN enrollments e ON c.course_id = e.course_id " +
                "WHERE e.student_id = ? " +
                "ORDER BY c.course_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "Course ID: " + rs.getInt("course_id") +
                                    " | Course: " + rs.getString("course_name") +
                                    " | Fee: " + rs.getDouble("fee")
                    );
                }

                if (!found) {
                    System.out.println("No courses found for this student.");
                }
            }
        }
    }

    // Display student-course report using SQL JOIN
    public void showStudentCourseReport() throws SQLException {

        String sql = "SELECT s.student_id, s.name, c.course_name, e.enrollment_date " +
                "FROM students s " +
                "JOIN enrollments e ON s.student_id = e.student_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "ORDER BY s.name, c.course_name";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            System.out.println("\nID | Student Name | Course | Enrollment Date");
            System.out.println("-----------------------------------------------");

            while (rs.next()) {
                found = true;

                System.out.println(
                        rs.getInt("student_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("course_name") + " | " +
                                rs.getTimestamp("enrollment_date")
                );
            }

            if (!found) {
                System.out.println("No enrollment records found.");
            }
        }
    }

    // Display courses using a stored procedure
    public void showCoursesUsingProcedure(int studentId) throws SQLException {

        String sql = "{CALL GetStudentCourses(?)}";

        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, studentId);

            try (ResultSet rs = cs.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "Course ID: " + rs.getInt("course_id") +
                                    " | Course: " + rs.getString("course_name") +
                                    " | Fee: " + rs.getDouble("fee")
                    );
                }

                if (!found) {
                    System.out.println("No courses found for this student.");
                }
            }
        }
    }

    // Enroll multiple students using batch processing
    public void enrollStudentsBatch(int[][] enrollments) throws SQLException {

        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                for (int[] enrollment : enrollments) {
                    ps.setInt(1, enrollment[0]);
                    ps.setInt(2, enrollment[1]);
                    ps.addBatch();
                }

                ps.executeBatch();
                con.commit();

                System.out.println("Batch enrollment successful.");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
