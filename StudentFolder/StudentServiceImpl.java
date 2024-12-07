package com.example.studentapp.service;

import com.example.studentapp.dto.StudentDTO;
import com.example.studentapp.entity.Student;
import com.example.studentapp.exception.ResourceNotFoundException;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setCurrentTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        repository.save(student);
        return studentDTO;
    }

    @Override
    public StudentDTO updateStudent(int id, StudentDTO studentDTO) {
        Student existingStudent = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with ID " + id + " not found"));

        if (studentDTO.getEmail() != null) existingStudent.setEmail(studentDTO.getEmail());
        if (studentDTO.getName() != null) existingStudent.setName(studentDTO.getName());
        if (studentDTO.getAge() != null) existingStudent.setAge(studentDTO.getAge());
        if (studentDTO.getSalary() != 0) existingStudent.setSalary(studentDTO.getSalary());
        if (studentDTO.getDepartment() != null) existingStudent.setDepartment(studentDTO.getDepartment());
        if (studentDTO.getPincode() != null) existingStudent.setPincode(studentDTO.getPincode());
        if (studentDTO.getTown() != null) existingStudent.setTown(studentDTO.getTown());
        if (studentDTO.getState() != null) existingStudent.setState(studentDTO.getState());
        if (studentDTO.getCountry() != null) existingStudent.setCountry(studentDTO.getCountry());

        existingStudent.setUpdateTime(LocalDateTime.now());
        repository.update(existingStudent);
        return studentDTO;
    }

    @Override
    public void deleteStudent(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found"));
        repository.deleteById(id);
    }

    @Override
    public StudentDTO getStudentById(int id) {
        Student student = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with ID " + id + " not found"));
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return repository.findAll().stream().map(student -> {
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student, studentDTO);
            return studentDTO;
        }).collect(Collectors.toList());
    }

    @Override
public StudentDTO getStudentByEmail(String email) {
    Student student = repository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Student with email " + email + " not found"));
    StudentDTO studentDTO = new StudentDTO();
    BeanUtils.copyProperties(student, studentDTO);
    return studentDTO;
}

  @Override
    public StudentResponseDTO getStudentByEmail2(String email) {
        Student student = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student with email " + email + " not found"));

        // Map to StudentResponseDTO
        StudentResponseDTO responseDTO = new StudentResponseDTO();
        responseDTO.setName(student.getName());
        responseDTO.setAge(student.getAge());
        responseDTO.setSalary(student.getSalary());
        responseDTO.setCurrentTime(student.getCurrentTime());

        return responseDTO;
    }

    package com.example.studentapp.service;

import com.example.studentapp.dto.StudentCountryDTO;
import com.example.studentapp.dto.StudentsByCountryResponseDTO;
import com.example.studentapp.entity.Student;
import com.example.studentapp.exception.ResourceNotFoundException;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Override
    public StudentsByCountryResponseDTO getStudentsByCountry(String country) {
        List<Student> students = repository.findByCountry(country);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found in country: " + country);
        }

        // Map students to DTO
        List<StudentCountryDTO> studentCountryDTOs = students.stream().map(student -> {
            StudentCountryDTO dto = new StudentCountryDTO();
            dto.setName(student.getName());
            dto.setCountry(student.getCountry());
            return dto;
        }).collect(Collectors.toList());

        // Create response DTO
        StudentsByCountryResponseDTO response = new StudentsByCountryResponseDTO();
        response.setTotalStudents(studentCountryDTOs.size());
        response.setStudents(studentCountryDTOs);

        return response;
    }
}

@Override
    public List<String> getCountryByPincode(Long pincode) {
        List<String> countries = repository.findCountryByPincode(pincode);
        if (countries.isEmpty()) {
            throw new ResourceNotFoundException("No students found with pincode: " + pincode);
        }
        return countries;
    }

    @Override
    public List<StudentPincodeResponseDTO> getStudentsByPincode(Long pincode) {
        List<Student> students = repository.findByPincode(pincode);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found with pincode: " + pincode);
        }

        // Map students to StudentPincodeResponseDTO
        return students.stream().map(student -> {
            StudentPincodeResponseDTO dto = new StudentPincodeResponseDTO();
            dto.setCountry(student.getCountry());
            dto.setSalary(student.getSalary());
            dto.setCurrentTime(student.getCurrentTime());
            dto.setUpdateTime(student.getUpdateTime());
            return dto;
        }).collect(Collectors.toList());
    }

@Override
    public Map<String, LocalDateTime> getTimePeriodByPincode(Long pincode) {
        LocalDateTime updateTime = repository.findUpdateTimeByPincode(pincode);
        
        if (updateTime == null) {
            throw new ResourceNotFoundException("No students found with pincode: " + pincode);
        }
        
        // Subtract 2 weeks and 4 weeks
        LocalDateTime twoWeeksBefore = updateTime.minusWeeks(2);
        LocalDateTime fourWeeksBefore = updateTime.minusWeeks(4);
        
        // Prepare the response map
        Map<String, LocalDateTime> response = new HashMap<>();
        response.put("2_weeks_before", twoWeeksBefore);
        response.put("4_weeks_before", fourWeeksBefore);
        
        return response;
    }

    @Override
    public Map<String, LocalDate> findDurationPeriod(Long pincode, String date) {
        // Convert the input string date to LocalDate
        LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE); 

        // Fetch the student's updateTime based on the pincode
        LocalDateTime updateTime = repository.findUpdateTimeByPincode(pincode);

        if (updateTime == null) {
            throw new ResourceNotFoundException("No student found with pincode: " + pincode);
        }

        // Subtract the provided date from updateTime and calculate 2 weeks, 1 day, and 4 weeks
        LocalDateTime twoWeeksBefore = updateTime.minusWeeks(2);
        LocalDateTime oneDayBefore = updateTime.minusDays(1);
        LocalDateTime fourWeeksBefore = updateTime.minusWeeks(4);

        // Convert LocalDateTime to LocalDate (as requested)
        LocalDate twoWeeksBeforeDate = twoWeeksBefore.toLocalDate();
        LocalDate oneDayBeforeDate = oneDayBefore.toLocalDate();
        LocalDate fourWeeksBeforeDate = fourWeeksBefore.toLocalDate();

        // Prepare the result map
        Map<String, LocalDate> result = new HashMap<>();
        result.put("2_weeks_before", twoWeeksBeforeDate);
        result.put("1_day_before", oneDayBeforeDate);
        result.put("4_weeks_before", fourWeeksBeforeDate);

        return result;
    }


}
