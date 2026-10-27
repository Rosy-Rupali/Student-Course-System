package com.studentcourseregistrationsystem.main;

import com.studentcourseregistrationsystem.model.Course;
import com.studentcourseregistrationsystem.model.Student;
import com.studentcourseregistrationsystem.service.StudentCourseService;
import com.studentcourseregistrationsystem.util.DBConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentCourseApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentCourseService service = new StudentCourseService();

    public static void main(String[] args) {
        boolean running = true;
        try {
            while (running) {
                System.out.println("\n===== STUDENT COURSE MANAGEMENT =====");
                System.out.println("1. Register student");
                System.out.println("2. View all students");
                System.out.println("3. Search student by ID");
                System.out.println("4. Update student");
                System.out.println("5. Delete student");
                System.out.println("6. Add course");
                System.out.println("7. View all courses");
                System.out.println("8. Update course");
                System.out.println("9. Delete course");
                System.out.println("10. Enroll student in course");
                System.out.println("11. Display students by course");
                System.out.println("12. Display courses by student");
                System.out.println("13. Student-course JOIN report");
                System.out.println("14. Get courses using stored procedure");
                System.out.println("15. Batch enroll students");
                System.out.println("0. Exit");
                System.out.println("=====================================");

                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(sc.nextLine());

                try {
                    switch (choice) {

                        case 1:
                            System.out.print("Student name: ");
                            String name = sc.nextLine().trim();

                            System.out.print("Email: ");
                            String email = sc.nextLine().trim();

                            System.out.print("Phone: ");
                            String phone = sc.nextLine().trim();

                            service.registerStudent(name, email, phone);
                            System.out.println("Student registered successfully.");
                            break;

                        case 2:
                            List<Student> students = service.getStudents();

                            if (students.isEmpty()) {
                                System.out.println("No students registered.");
                            } else {
                                for (Student s : students) {
                                    System.out.printf("%d | %s | %s | %s%n",
                                            s.getStudentId(),
                                            s.getName(),
                                            s.getEmail(),
                                            s.getPhone());
                                }
                            }
                            break;

                        case 3:
                            System.out.print("Student ID: ");
                            int studentId = Integer.parseInt(sc.nextLine().trim());

                            Student student = service.getStudent(studentId);

                            if (student == null) {
                                System.out.println("Student not found.");
                            } else {
                                System.out.printf("%d | %s | %s | %s%n",
                                        student.getStudentId(),
                                        student.getName(),
                                        student.getEmail(),
                                        student.getPhone());
                            }
                            break;

                        case 4:
                            System.out.print("Student ID to update: ");
                            int updateId = Integer.parseInt(sc.nextLine().trim());

                            Student existing = service.getStudent(updateId);

                            if (existing == null) {
                                System.out.println("Student not found.");
                                break;
                            }

                            System.out.print("New name: ");
                            String newName = sc.nextLine().trim();

                            System.out.print("New email: ");
                            String newEmail = sc.nextLine().trim();

                            System.out.print("New phone: ");
                            String newPhone = sc.nextLine().trim();

                            service.updateStudent(updateId, newName, newEmail, newPhone);

                            System.out.println("Student update operation completed.");
                            break;

                        case 5:
                            System.out.print("Student ID to delete: ");
                            int deleteId = Integer.parseInt(sc.nextLine().trim());

                            if (service.getStudent(deleteId) == null) {
                                System.out.println("Student not found.");
                            } else {
                                service.deleteStudent(deleteId);
                                System.out.println("Student delete operation completed.");
                            }
                            break;

                        case 6:
                            System.out.print("Course name: ");
                            String courseName = sc.nextLine().trim();

                            System.out.print("Course fee: ");
                            double fee = Double.parseDouble(sc.nextLine().trim());

                            if (courseName.isEmpty() ||
                                    !Double.isFinite(fee) || fee <= 0) {
                                System.out.println(
                                        "Enter a valid course name and positive fee.");
                                break;
                            }

                            service.addCourse(courseName, fee);
                            System.out.println("Course added successfully.");
                            break;

                        case 7:
                            List<Course> courses = service.getCourses();

                            if (courses.isEmpty()) {
                                System.out.println("No courses available.");
                            } else {
                                for (Course c : courses) {
                                    System.out.printf("%d | %s | %.2f%n",
                                            c.getCourseId(),
                                            c.getCourseName(),
                                            c.getFee());
                                }
                            }
                            break;

                        case 8:
                            System.out.print("Course ID to update: ");
                            int courseId = Integer.parseInt(sc.nextLine().trim());

                            System.out.print("New course name: ");
                            String updatedCourseName = sc.nextLine().trim();

                            System.out.print("New course fee: ");
                            double updatedFee =
                                    Double.parseDouble(sc.nextLine().trim());

                            if (updatedCourseName.isEmpty() ||
                                    !Double.isFinite(updatedFee) ||
                                    updatedFee <= 0) {
                                System.out.println("Invalid course details.");
                                break;
                            }

                            service.updateCourse(courseId, updatedCourseName, updatedFee);

                            System.out.println("Course update operation completed.");
                            break;

                        case 9:
                            System.out.print("Course ID to delete: ");
                            int removeCourseId = Integer.parseInt(sc.nextLine().trim());

                            service.deleteCourse(removeCourseId);
                            System.out.println("Course delete operation completed.");
                            break;

                        case 10:
                            System.out.print("Student ID: ");
                            int enrollStudentId = Integer.parseInt(sc.nextLine().trim());

                            System.out.print("Course ID: ");
                            int enrollCourseId = Integer.parseInt(sc.nextLine().trim());

                            service.enroll(enrollStudentId, enrollCourseId);
                            break;

                        case 11:
                            System.out.print("Course ID: ");
                            int searchCourseId = Integer.parseInt(sc.nextLine().trim());

                            service.showStudentsByCourse(searchCourseId);
                            break;

                        case 12:
                            System.out.print("Student ID: ");
                            int searchStudentId = Integer.parseInt(sc.nextLine().trim());

                            service.showCoursesByStudent(searchStudentId);
                            break;

                        case 13:
                            service.showReport();
                            break;

                        case 14:
                            System.out.print("Student ID: ");
                            int procedureStudentId = Integer.parseInt(sc.nextLine().trim());

                            service.showCoursesUsingProcedure(procedureStudentId);
                            break;

                        case 15:
                            System.out.print("Number of enrollments: ");
                            int count = Integer.parseInt(sc.nextLine().trim());

                            if (count < 1 || count > 100) {
                                System.out.println("Enter a count between 1 and 100.");
                                break;
                            }

                            int[][] enrollments = new int[count][2];

                            for (int i = 0; i < count; i++) {
                                System.out.println("Enrollment " + (i + 1));

                                System.out.print("Student ID: ");
                                enrollments[i][0] = Integer.parseInt(sc.nextLine().trim());

                                System.out.print("Course ID: ");
                                enrollments[i][1] = Integer.parseInt(sc.nextLine().trim());
                            }

                            service.enrollBatch(enrollments);
                            break;

                        case 0:
                            running = false;
                            System.out.println("Application closed.Thank You!!!");
                            break;

                        default:
                            System.out.println("Invalid choice. Try again.");
                            break;
                    }

                } catch (SQLException e) {
                    System.out.println("Database operation failed: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric choice.");
        } finally {
            sc.close();
            DBConnection.closePool();
        }
    }
}