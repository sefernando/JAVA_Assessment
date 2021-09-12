package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Module;
import com.generation.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, List<Student>> enrolledStudents = new HashMap<>();

    public CourseService() {
        Module module = new Module("INTRO-CS", "Introduction to Computer Science",
                "Introductory module for the generation technical programs");
        registerCourse(new Course("INTRO-CS-1", "Introduction to Computer Science", 9, module));
        registerCourse(new Course("INTRO-CS-2", "Introduction to Algorithms", 9, module));
        registerCourse(new Course("INTRO-CS-3", "Algorithm Design and Problem Solving - Introduction ", 9, module));
        registerCourse(new Course("INTRO-CS-4", "Algorithm Design and Problem Solving - Advanced", 9, module));
        registerCourse(new Course("INTRO-CS-5", "Terminal Fundamentals", 9, module));
        registerCourse(new Course("INTRO-CS-6", "Source Control Using Git and Github", 9, module));
        registerCourse(new Course("INTRO-CS-7", "Agile Software Development with SCRUM", 9, module));

        Module moduleWebFundamentals = new Module("INTRO-WEB", "Web Development Fundamentals",
                "Introduction to fundamentals of web development");
        registerCourse(new Course("INTRO-WEB-1", "Introduction to Web Applications", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-2", "Introduction to HTML", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-3", "Introduction to CSS", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-4", "Advanced HTML", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-5", "Advanced CSS", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-6", "Introduction to Bootstrap Framework", 9, moduleWebFundamentals));
        registerCourse(
                new Course("INTRO-WEB-7", "Introduction to JavaScript for Web Development", 9, moduleWebFundamentals));

    }

    public Map<String, List<Student>> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void registerCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String code) {
        if (courses.containsKey(code)) {
            return courses.get(code);
        }
        return null;
    }

    public String getCourseName(String courseId) {
        return courses.get(courseId).getName();
    }

    public void enrollStudent(String courseId, Student student) {
        if (!enrolledStudents.containsKey(courseId)) {
            enrolledStudents.put(courseId, new ArrayList<>());
        }
        if(!enrolledStudents.get(courseId).contains(student)){
            enrolledStudents.get(courseId).add(student);
        }
    }

    public boolean isStudentEnrollerd(String courseId, Student student){
        try {
            enrolledStudents.get(courseId).contains(student);
            return true;
        } catch (java.lang.NullPointerException e) {
            return false;
        }
    }

    public void showEnrolledStudents(String courseId) {
        if (enrolledStudents.containsKey(courseId)) {
            List<Student> students = enrolledStudents.get(courseId);
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }


    public void showSummary() {
        String courseId;
        System.out.println("Available Courses:");
        for (String key : courses.keySet()) {
            Course course = courses.get(key);
            System.out.println(course);
        }
        System.out.println("Enrolled Students");
        for (String key : enrolledStudents.keySet()) {
            List<Student> students = enrolledStudents.get(key);
            System.out.println("Students on Course " + key + ": ");
            courseId = key;
            for (Student student : students) {
                System.out.print(student);
                //displaying the grading for each course per student
                String grade =  student.isGradedBefore(courseId) ? Integer.toString(student.getCourseGrades().get(courseId)) : "Not Graded";
                System.out.println(" : Grade - " + grade);
            }
        }
    }

    public void displayAverageGrade() {

        for (String key : enrolledStudents.keySet()) {   //get set of courses ids for enrolled courses
            int noOfNotGradedStudents = 0;
            double totalGrade = 0;
            double averageGrade = 0;

            List<Student> students = enrolledStudents.get(key);   //no of students who enrolled to this course

            for (Student student : students) {
                try {
                    if(!student.isGradedBefore(key)){
                        noOfNotGradedStudents++;
                    }
                    totalGrade += student.getCourseGrades().get(key);
                } catch (java.lang.NullPointerException ignored) {}
            }

            if(noOfNotGradedStudents == students.size()){
                System.out.println("Gradings are not available for " + key);
                continue;
            }

            try{
                averageGrade = totalGrade / (students.size() - noOfNotGradedStudents);
            } catch (ArithmeticException e){
                System.out.println("This exception should never reached. Something went wrong...");
            }

            System.out.print("Average grade for " + getCourseName(key) + " (" + key + ") - "  );
            System.out.println(String.format("%.2f",  averageGrade));

        }
    }
}


//