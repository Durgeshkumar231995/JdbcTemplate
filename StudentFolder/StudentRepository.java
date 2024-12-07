package com.example.studentapp.repository;

import com.example.studentapp.entity.Student;
import com.example.studentapp.util.SQLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Student student) {
        return jdbcTemplate.update(SQLConstants.INSERT_STUDENT, student.getEmail(), student.getName(), student.getAge(),
                student.getSalary(), student.getDepartment(), student.getPincode(), student.getTown(), student.getState(),
                student.getCountry(), student.getCurrentTime(), student.getUpdateTime());
    }

    public int update(Student student) {
        return jdbcTemplate.update(SQLConstants.UPDATE_STUDENT, student.getEmail(), student.getName(), student.getAge(),
                student.getSalary(), student.getDepartment(), student.getPincode(), student.getTown(), student.getState(),
                student.getCountry(), student.getUpdateTime(), student.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update(SQLConstants.DELETE_STUDENT, id);
    }

    public Optional<Student> findById(int id) {
        return jdbcTemplate.query(SQLConstants.SELECT_STUDENT_BY_ID, new BeanPropertyRowMapper<>(Student.class), id)
                .stream().findFirst();
    }

    public List<Student> findAll() {
        return jdbcTemplate.query(SQLConstants.SELECT_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
    }

    public Optional<Student> findByEmail(String email) {
    return jdbcTemplate.query(SQLConstants.SELECT_STUDENT_BY_EMAIL, 
            new BeanPropertyRowMapper<>(Student.class), email)
            .stream()
            .findFirst();
}

public Optional<Student> findByEmail(String email) {
        return jdbcTemplate.query(SQLConstants.SELECT_STUDENT_BY_EMAIL, 
                new BeanPropertyRowMapper<>(Student.class), email)
                .stream()
                .findFirst();
    }

    public List<Student> findByCountry(String country) {
        return jdbcTemplate.query(SQLConstants.SELECT_STUDENTS_BY_COUNTRY,
                new BeanPropertyRowMapper<>(Student.class), country);
    }
    public List<String> findCountryByPincode(Long pincode) {
        return jdbcTemplate.queryForList(SQLConstants.SELECT_COUNTRY_BY_PINCODE, String.class, pincode);
    }

    public List<Student> findByPincode(Long pincode) {
        return jdbcTemplate.query(SQLConstants.SELECT_STUDENT_DETAILS_BY_PINCODE,
                (rs, rowNum) -> {
                    Student student = new Student();
                    student.setCountry(rs.getString("country"));
                    student.setSalary(rs.getDouble("salary"));
                    student.setCurrentTime(rs.getObject("currentTime", java.time.LocalDateTime.class));
                    student.setUpdateTime(rs.getObject("updateTime", java.time.LocalDateTime.class));
                    return student;
                }, pincode);
    }

    // Or
    public List<Student> findByPincode(Long pincode) {
        try {
            return jdbcTemplate.query(SQLConstants.SELECT_STUDENT_DETAILS_BY_PINCODE, 
                                      new StudentRowMapper(), pincode);
        } catch (Exception e) {
            // Log the exception or handle accordingly
            throw new RuntimeException("Error retrieving students by pincode", e);
        }
    }

    public LocalDateTime findUpdateTimeByPincode(Long pincode) {
        return jdbcTemplate.queryForObject(SQLConstants.SELECT_UPDATE_TIME_BY_PINCODE, 
                                            LocalDateTime.class, pincode);
    }


  // Assuming the pincode is unique to the student, otherwise you may need another key like studentId
    private static final String SELECT_UPDATE_TIME_BY_PINCODE = 
        "SELECT updateTime FROM students WHERE pincode = ?";

    public LocalDateTime findUpdateTimeByPincode(Long pincode) {
        // Fetch the updateTime from the database for the student with the given pincode
        return jdbcTemplate.queryForObject(SELECT_UPDATE_TIME_BY_PINCODE, 
                                            LocalDateTime.class, pincode);
    }

}
