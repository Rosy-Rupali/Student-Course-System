package com.studentcourseregistrationsystem.dao;

import com.studentcourseregistrationsystem.model.Student;
import com.studentcourseregistrationsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student s) throws SQLException {
        String sql = " INSERT INTO students(name, email, phone) VALUES (?, ?, ?) ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("Student added successfully!!!");
            else
                System.err.println("Failed to add student.");
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        String sql = " SELECT student_id, name, email, phone FROM students ORDER BY student_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        }
        return students;
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT student_id, name, email, phone FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }
        }
        return null;
    }

    public void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, phone = ? WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setInt(4, s.getStudentId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("Student is updated.");
            else System.err.println("Failed to update student.");
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Student is deleted.");
            else System.err.println("Failed to delete the student.");
        }
    }
}
