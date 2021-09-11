package com.generation.model;

import com.generation.service.CourseService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person implements Evaluation {

    private final Map<String, Course> approvedCourses = new HashMap<>();
    private final Map<String, Integer> courseGrades = new HashMap<>();
    private final Map<String, Course> passedCourses = new HashMap<>();
//    private final Map<String, Double> averageGradePerCourse = new HashMap<>();
    private double average;

    public Student(String id, String name, String email, Date birthDate) {
        super(id, name, email, birthDate);
    }

    public void enrollToCourse(Course course) {
        //TODO implement this method
        String courseId = course.getCode();
        if (isCourseApproved(courseId)) {
            approvedCourses.put(course.getCode(), course);
        } else {
            System.out.println("Course is not approved");
        }
    }


    public void registerApprovedCourse(Course course) {
        approvedCourses.put(course.getCode(), course);
    }

    public boolean isCourseApproved(String courseCode) {
        //TODO implement this method
        CourseService courseService = new CourseService();
        // courseService.getCourse(courseCode) != null ? true : false;
        return courseService.getCourse(courseCode) != null;
    }

    public boolean isAttendingCourse(String courseCode) {
        //TODO implement this method
        return approvedCourses.containsKey(courseCode);
    }

    public void showEnrolledCourses() {
        approvedCourses.forEach((K, V) -> System.out.println(V));
    }

    public boolean isGradedBefore(String courseId) {
        return courseGrades.containsKey(courseId);
    }

    public void gradeCourses(String courseId, int grade) {
        if (isAttendingCourse(courseId)) {
            courseGrades.put(courseId, grade);
//            System.out.println(courseGrades);
        }
    }

    public Map<String, Integer> getCourseGrades() {
        return courseGrades;
    }

    public Map<String, Course> findPassedCourses() {
        //TODO implement this method
        CourseService courseService = new CourseService();
        courseGrades.forEach((K, V) -> {
            if (V >= 3) {
                passedCourses.put(K, courseService.getCourse(K));
            }
            if (V < 3) {
                passedCourses.remove(K);
            }
        });
        return passedCourses;
    }

    @Override
    public double getAverage() {
        return average;
    }

    @Override
    public Map<String, Course> getApprovedCourses() {
        //TODO implement this method
        return approvedCourses;
    }

    @Override
    public String toString() {
        return "Student {" + super.toString() + "}";
    }
}


/////////////////