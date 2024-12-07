package com.example.studentapp.util;

public class SQLConstants {
    public static final String INSERT_STUDENT = "INSERT INTO students (email, name, age, salary, department, pincode, town, state, country, current_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_STUDENT = "UPDATE students SET email = ?, name = ?, age = ?, salary = ?, department = ?, pincode = ?, town = ?, state = ?, country = ?, update_time = ? WHERE id = ?";
    public static final String DELETE_STUDENT = "DELETE FROM students WHERE id = ?";
    public static final String SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ?";
    public static final String SELECT_ALL_STUDENTS = "SELECT * FROM students";
    public static final String SELECT_STUDENT_BY_EMAIL = "SELECT * FROM students WHERE email = ?";
    public static final String SELECT_COUNTRY_BY_PINCODE = "SELECT DISTINCT country FROM students WHERE pincode = ?";
    public static final String SELECT_COUNTRY_BY_PINCODE = "SELECT DISTINCT country FROM students WHERE pincode = ?";

    public static final String SELECT_STUDENTS_BY_COUNTRY = "SELECT name, country FROM students WHERE country = ?";
    public static final String SELECT_STUDENT_DETAILS_BY_PINCODE = "SELECT country, salary, currentTime, updateTime FROM students WHERE pincode = ?";


public static final String SELECT_UPDATE_TIME_BY_PINCODE =  "SELECT updateTime FROM students WHERE pincode = ?";



}
