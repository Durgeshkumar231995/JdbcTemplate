package com.example.studentapp.service;

import com.example.studentapp.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO saveStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(int id, StudentDTO studentDTO);
    void deleteStudent(int id);
    StudentDTO getStudentById(int id);
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentByEmail(String email);
    StudentResponseDTO getStudentByEmail2(String email);
    List<String> getCountryByPincode(Long pincode);
    List<StudentPincodeResponseDTO> getStudentsByPincode(Long pincode);
    Map<String, LocalDateTime> getTimePeriodByPincode(Long pincode);


}
