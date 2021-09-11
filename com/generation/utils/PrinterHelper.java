package com.generation.utils;

import com.generation.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrinterHelper
{

    public static void showMainMenu(){
        System.out.println( "|-------------------------------|" );
        System.out.println( "| Welcome to StudentGen         |" );
        System.out.println( "|-------------------------------|" );
        System.out.println( "| Select 1 option:              |" );
        System.out.println( "| . 1 Register Student          |" );
        System.out.println( "| . 2 Find Student              |" );
        System.out.println( "| . 3 Grade Student             |" );
        System.out.println( "| . 4 Enroll Student to Course  |" );
        System.out.println( "| . 5 Show Students Summary     |" );
        System.out.println( "| . 6 Show Courses Summary      |" );
        System.out.println( "| . 7 Show passed Courses       |" );
        System.out.println( "| . 8 Show Average Grade        |" );
        System.out.println( "| . 9 Exit                      |" );
        System.out.println( "|-------------------------------|" );
    }

    public static Student createStudentMenu(Scanner scanner ) throws ParseException {

        System.out.println( "|-------------------------------------|" );
        System.out.println( "| . 1 Register Student                |" );
        System.out.println( "|-------------------------------------|" );
        System.out.println( "| Enter student name:                 |" );
        String name = scanner.next();
        System.out.println( "| Enter student ID:                   |" );
        String id = scanner.next();
        System.out.println( "| Enter student email:                |" );
        String email = scanner.next();
        System.out.println( "| Enter student birth date(mm/dd/yyyy)|" );

        //TODO validate date format and catch exception to avoid crash
        DateFormat formatter = new SimpleDateFormat( "mm/dd/yyyy");
        Date birthDate = null;
        boolean isValidDate = false;

        do {
            try{
                birthDate = formatter.parse( scanner.next());
                isValidDate = true;
            } catch (ParseException e){
                System.out.println("Invalid date format. make sure you type the date using following format: MM/DD/YYYY");
            }
        } while (!isValidDate);

        System.out.println( "|-------------------------------------|" );
        Student student = new Student( id, name, email, birthDate );
        System.out.println( "Student Successfully Registered! " );
        System.out.println(student);
        return student;
    }

}
