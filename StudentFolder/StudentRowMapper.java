public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setCountry(rs.getString("country"));
        student.setSalary(rs.getDouble("salary"));
        student.setCurrentTime(rs.getObject("currentTime", LocalDateTime.class));
        student.setUpdateTime(rs.getObject("updateTime", LocalDateTime.class));
        return student;
    }
}
