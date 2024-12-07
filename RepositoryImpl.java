SELECT s.* 
FROM Student s 
JOIN Department d ON s.department_id = d.id 
WHERE d.id = ?;
// Fetch All Students in a Department
public List<StudentDTO> fetchStudentsByDepartmentId(int departmentId) {
    String sql = "SELECT s.* FROM Student s JOIN Department d ON s.department_id = d.id WHERE d.id = ?";
    return jdbcTemplate.query(sql, new Object[]{departmentId}, new BeanPropertyRowMapper<>(StudentDTO.class));
}

//Fetch All Courses Offered by a Department
SELECT c.* 
FROM Courses c 
JOIN Department d ON c.department_id = d.id 
WHERE d.id = ?;

public List<CourseDTO> fetchCoursesByDepartmentId(int departmentId) {
    String sql = "SELECT c.* FROM Courses c JOIN Department d ON c.department_id = d.id WHERE d.id = ?";
    return jdbcTemplate.query(sql, new Object[]{departmentId}, new BeanPropertyRowMapper<>(CourseDTO.class));
}

//Fetch Students Enrolled in Specific Courses

SELECT s.* 
FROM Student s 
JOIN Student_Courses sc ON s.id = sc.student_id 
WHERE sc.course_id = ?;


public List<StudentDTO> fetchStudentsByCourseId(int courseId) {
    String sql = "SELECT s.* FROM Student s JOIN Student_Courses sc ON s.id = sc.student_id WHERE sc.course_id = ?";
    return jdbcTemplate.query(sql, new Object[]{courseId}, new BeanPropertyRowMapper<>(StudentDTO.class));
}

//Fetch Course Details with Associated Departments and Students
SELECT c.*, d.name AS department_name, s.name AS student_name 
FROM Courses c
LEFT JOIN Department d ON c.department_id = d.id
LEFT JOIN Student_Courses sc ON c.courseid = sc.course_id
LEFT JOIN Student s ON sc.student_id = s.id
WHERE c.courseid = ?;

public List<Map<String, Object>> fetchCourseDetails(int courseId) {
    String sql = "SELECT c.*, d.name AS department_name, s.name AS student_name " +
                 "FROM Courses c " +
                 "LEFT JOIN Department d ON c.department_id = d.id " +
                 "LEFT JOIN Student_Courses sc ON c.courseid = sc.course_id " +
                 "LEFT JOIN Student s ON sc.student_id = s.id " +
                 "WHERE c.courseid = ?";
    return jdbcTemplate.queryForList(sql, courseId);
}

//Fetch Departments Without Any Students
SELECT d.* 
FROM Department d 
LEFT JOIN Student s ON d.id = s.department_id 
WHERE s.id IS NULL;

public List<DepartmentDTO> fetchDepartmentsWithoutStudents() {
    String sql = "SELECT d.* FROM Department d LEFT JOIN Student s ON d.id = s.department_id WHERE s.id IS NULL";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DepartmentDTO.class));
}

//Fetch Courses Without Any Students Enrolled
SELECT c.* 
FROM Courses c 
LEFT JOIN Student_Courses sc ON c.courseid = sc.course_id 
WHERE sc.student_id IS NULL;


public List<CourseDTO> fetchCoursesWithoutStudents() {
    String sql = "SELECT c.* FROM Courses c LEFT JOIN Student_Courses sc ON c.courseid = sc.course_id WHERE sc.student_id IS NULL";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CourseDTO.class));
}


// Fetch All Students and Their Enrolled Courses with Department Details
SELECT s.name AS student_name, c.course_name, d.name AS department_name 
FROM Student s 
JOIN Student_Courses sc ON s.id = sc.student_id 
JOIN Courses c ON sc.course_id = c.courseid 
JOIN Department d ON s.department_id = d.id;

public List<Map<String, Object>> fetchStudentsCoursesAndDepartments() {
    String sql = "SELECT s.name AS student_name, c.course_name, d.name AS department_name " +
                 "FROM Student s " +
                 "JOIN Student_Courses sc ON s.id = sc.student_id " +
                 "JOIN Courses c ON sc.course_id = c.courseid " +
                 "JOIN Department d ON s.department_id = d.id";
    return jdbcTemplate.queryForList(sql);
}


//Fetch the Department with the Maximum Students
SELECT d.*, COUNT(s.id) AS student_count 
FROM Department d 
JOIN Student s ON d.id = s.department_id 
GROUP BY d.id 
ORDER BY student_count DESC 
LIMIT 1;

public Map<String, Object> fetchDepartmentWithMaxStudents() {
    String sql = "SELECT d.*, COUNT(s.id) AS student_count " +
                 "FROM Department d " +
                 "JOIN Student s ON d.id = s.department_id " +
                 "GROUP BY d.id " +
                 "ORDER BY student_count DESC " +
                 "LIMIT 1";
    return jdbcTemplate.queryForMap(sql);
}

//Fetch the Course with the Maximum Enrollment
SELECT c.*, COUNT(sc.student_id) AS enrollment_count 
FROM Courses c 
JOIN Student_Courses sc ON c.courseid = sc.course_id 
GROUP BY c.courseid 
ORDER BY enrollment_count DESC 
LIMIT 1;

public Map<String, Object> fetchCourseWithMaxEnrollment() {
    String sql = "SELECT c.*, COUNT(sc.student_id) AS enrollment_count " +
                 "FROM Courses c " +
                 "JOIN Student_Courses sc ON c.courseid = sc.course_id " +
                 "GROUP BY c.courseid " +
                 "ORDER BY enrollment_count DESC " +
                 "LIMIT 1";
    return jdbcTemplate.queryForMap(sql);
}

// Fetch All Departments With Student Count
SELECT d.*, COUNT(s.id) AS student_count 
FROM Department d 
LEFT JOIN Student s ON d.id = s.department_id 
GROUP BY d.id;

public List<Map<String, Object>> fetchDepartmentsWithStudentCount() {
    String sql = "SELECT d.*, COUNT(s.id) AS student_count " +
                 "FROM Department d " +
                 "LEFT JOIN Student s ON d.id = s.department_id " +
                 "GROUP BY d.id";
    return jdbcTemplate.queryForList(sql);
}

