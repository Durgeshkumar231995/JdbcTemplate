package com.example.studentapp.controller;

import com.example.studentapp.dto.StudentDTO;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(service.saveStudent(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(service.updateStudent(id, studentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable int id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    @GetMapping("/email/{email}")
public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email) {
    return ResponseEntity.ok(service.getStudentByEmail(email));
}

@GetMapping("/v2/email/{email}")
    public ResponseEntity<StudentResponseDTO> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getStudentByEmail(email));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<StudentsByCountryResponseDTO> getStudentsByCountry(@PathVariable String country) {
        return ResponseEntity.ok(service.getStudentsByCountry(country));
    }

     @GetMapping("/pincode/{pincode}")
    public ResponseEntity<List<String>> getCountryByPincode(@PathVariable Long pincode) {
        return ResponseEntity.ok(service.getCountryByPincode(pincode));
    }

    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<List<StudentPincodeResponseDTO>> getStudentsByPincode(@PathVariable Long pincode) {
        return ResponseEntity.ok(service.getStudentsByPincode(pincode));
    }

     @GetMapping("/time-period/{pincode}")
    public ResponseEntity<Map<String, LocalDateTime>> getTimePeriodByPincode(@PathVariable Long pincode) {
        return ResponseEntity.ok(service.getTimePeriodByPincode(pincode));
    }

   @GetMapping("/duration-period/{pincode}/{date}")
    public ResponseEntity<Map<String, LocalDate>> findDurationPeriod(
        @PathVariable Long pincode, @PathVariable String date) {
        return ResponseEntity.ok(service.findDurationPeriod(pincode, date));
    }

}
