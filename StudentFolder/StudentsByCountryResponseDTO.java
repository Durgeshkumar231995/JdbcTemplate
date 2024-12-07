package com.example.studentapp.dto;

import java.util.List;

public class StudentsByCountryResponseDTO {
    private long totalStudents;
    private List<StudentCountryDTO> students;

    // Getters and Setters
    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public List<StudentCountryDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentCountryDTO> students) {
        this.students = students;
    }
}

class StudentCountryDTO {
    private String name;
    private String country;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
