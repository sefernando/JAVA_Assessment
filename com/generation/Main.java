package com.generation;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.text.ParseException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {

        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            PrinterHelper.showMainMenu();
            option = scanner.next();
            switch (option) {
                case "1":
                    registerStudent(studentService, scanner);
                    break;
                case "2":
                    findStudent(studentService, scanner);
                    break;
                case "3":
                    gradeStudent(studentService, courseService, scanner);
                    break;
                case "4":
                    enrollStudentToCourse(studentService, courseService, scanner);
                    break;
                case "5":
                    showStudentsSummary(studentService, scanner);
                    break;
                case "6":
                    showCoursesSummary(courseService);
                    break;
                case "7":
                    showPassedCourses(studentService, courseService, scanner);
                    break;
                case "8":
                    averageGrade(courseService);
                    break;
                case "9":
                    System.out.println("Thanks for visiting");
                    break;
                default:
                    System.out.println("Invalid Input: Please select the correct option");
                    break;
            }
        }
        while (!option.equals("9"));
    }

    //########################## option 1 ##############################
    private static void registerStudent(StudentService studentService, Scanner scanner) throws ParseException {
        Student student = PrinterHelper.createStudentMenu(scanner);
        studentService.subscribeStudent(student);
    }

    //########################## option 2 ##############################
    private static void findStudent(StudentService studentService, Scanner scanner) {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student != null) {
            System.out.println("Student Found: ");
            System.out.println(student);
        } else {
            System.out.println("Student with Id = " + studentId + " not found");
        }
    }

    //########################## option 3 ##############################
    private static void gradeStudent(StudentService studentService, CourseService courseService, Scanner scanner) {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        // CourseService courseService = new CourseService();

        //checking if the student is registered
        if (student == null) {
            System.out.println("No student found for student Id: " + studentId);
            return;
        }

        if (student.getApprovedCourses().isEmpty()) {
            System.out.println("Student is not attending to any courses");
            System.out.println("Select option 4 to enroll to a course");
            return;
        }

        System.out.println("Enrolled Courses: ");
        student.showEnrolledCourses();

        System.out.println("Insert the course Id to be graded: ");
        String courseId = scanner.next();

        if (courseService.getCourse(courseId) == null) {
            System.out.println("Invalid course Id");
            return;
        }

        //checking if the student is attending the course or if the input is invalid
        if (!student.isAttendingCourse(courseId)) {
            System.out.println("Student, with Id = " + studentId + ", is not attending to " + courseId);
            return;
        }

        //checking if the student is previously graded for this course and check whether it wants to update
        if (student.isGradedBefore(courseId)) {
            System.out.println("This course has been previouly graded: Do you update?");
            System.out.println("y - Yes \t n - No ");
            String input = scanner.next();
            if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                System.out.println("Invalid input");
                return;
            } else if (input.equalsIgnoreCase("n")) {
                System.out.println("Please select other options");
                return;
            }
        }

        System.out.print("Insert course grade between 0 to 6 for: ");
        System.out.println(courseService.getCourseName(courseId));
        int grade = scanner.nextInt();
        if (grade < 0 || grade > 6) {
            System.out.println("Invalid Grade");
            return;
        }
        student.gradeCourses(courseId, grade);
        System.out.println(student.getName() + " was sucsessfully graded for " + courseService.getCourseName(courseId));
    }

    //########################## option 4 ##############################
    private static void enrollStudentToCourse(StudentService studentService, CourseService courseService, Scanner scanner) {

        System.out.println("Insert student ID");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            System.out.println("No student found for student Id: " + studentId);
            return;
        }
        System.out.println(student);
        System.out.println("Insert course ID");
        String courseId = scanner.next();
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            System.out.println("Invalid Course ID");
            return;
        }

        if (courseService.isStudentEnrollerd(courseId, student)) {
            System.out.println("The student has already enrolled into " + courseId);
            return;
        }

        System.out.println(course);
        courseService.enrollStudent(courseId, student);
        studentService.enrollToCourse(studentId, course);
        System.out.println("Student with ID: " + studentId + " - Enrolled successfully to " + courseService.getCourseName(courseId));
    }

    //########################## option 5 ##############################
    private static void showStudentsSummary(StudentService studentService, Scanner scanner) {
        studentService.showSummary();
    }

    //########################## option 6 ##############################
    private static void showCoursesSummary(CourseService courseService) {
        courseService.showSummary();
    }

    //########################## option 7 ##############################
    public static void showPassedCourses(StudentService studentService, CourseService courseService, Scanner scanner) {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            System.out.println("Invalid student Id");
            return;
        }

        Map<String, Course> passedCourses = student.findPassedCourses();
        if (passedCourses.isEmpty()) {
            System.out.println("Sorry, you have not enrolled to a course or not passed any modules");
            return;
        } else {
            System.out.println("You have passed the following courses: ");
            passedCourses.forEach((K, V) -> System.out.println("\t\t- " + courseService.getCourseName(K)));
        }
    }


    //########################## option 8 ##############################
    public static void averageGrade(CourseService courseService) {

        if (courseService.getEnrolledStudents().isEmpty()) {
            System.out.println("No student has enrolled into any courses");
            return;
        }
        courseService.displayAverageGrade();
    }


}
