
package com.studentcourseregistrationsystem.service;

import com.studentcourseregistrationsystem.dao.CourseDAO;
import com.studentcourseregistrationsystem.dao.EnrollmentDAO;
import com.studentcourseregistrationsystem.dao.StudentDAO;
import com.studentcourseregistrationsystem.model.Course;
import com.studentcourseregistrationsystem.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentCourseService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    // Register student
    public void registerStudent(String name, String email, String phone) throws SQLException {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email are required.");
        }

        studentDAO.addStudent(new Student(name, email, phone));
    }

    // View all students
    public List<Student> getStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    // View student by ID
    public Student getStudent(int id) throws SQLException {
        return studentDAO.getStudentById(id);
    }

    // Update student
    public void updateStudent(int id, String name, String email, String phone)
            throws SQLException {

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and email are required.");
        }

        studentDAO.updateStudent(new Student(id, name, email, phone));
    }

    // Delete student
    public void deleteStudent(int id) throws SQLException {
        studentDAO.deleteStudent(id);
    }

    // Add course
    public void addCourse(String name, double fee) throws SQLException {

        if (name == null || name.trim().isEmpty() || fee <= 0
                || Double.isNaN(fee) || Double.isInfinite(fee)) {
            throw new IllegalArgumentException(
                    "Course name is required and fee must be positive.");
        }

        courseDAO.addCourse(new Course(name.trim(), fee));
    }

    // View all courses
    public List<Course> getCourses() throws SQLException {
        return courseDAO.getAllCourses();
    }

    // Update course
    public void updateCourse(int id, String name, double fee)
            throws SQLException {

        if (name == null || name.trim().isEmpty() || fee <= 0
                || Double.isNaN(fee) || Double.isInfinite(fee)) {
            throw new IllegalArgumentException("Invalid course details.");
        }

        courseDAO.updateCourse(new Course(id, name.trim(), fee));
    }

    // Delete course
    public void deleteCourse(int id) throws SQLException {
        courseDAO.deleteCourse(id);
    }

    // Enroll student in a course
    public void enroll(int studentId, int courseId) throws SQLException {
        enrollmentDAO.enrollStudent(studentId, courseId);
    }

    // Display students enrolled in a course
    public void showStudentsByCourse(int courseId) throws SQLException {
        enrollmentDAO.showStudentsByCourse(courseId);
    }

    // Display courses taken by a student
    public void showCoursesByStudent(int studentId) throws SQLException {
        enrollmentDAO.showCoursesByStudent(studentId);
    }

    // Generate student-course report
    public void showReport() throws SQLException {
        enrollmentDAO.showStudentCourseReport();
    }

    // Retrieve courses using stored procedure
    public void showCoursesUsingProcedure(int studentId) throws SQLException {
        enrollmentDAO.showCoursesUsingProcedure(studentId);
    }

    // Enroll multiple students
    public void enrollBatch(int[][] enrollments) throws SQLException {
        enrollmentDAO.enrollStudentsBatch(enrollments);
    }
}
