public class SQLQueries {
    public static final String INSERT_DEPARTMENT = "INSERT INTO Department (id, email, name, department, pincode, time) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_DEPARTMENT = "UPDATE Department SET email = ?, name = ?, department = ?, pincode = ?, time = ? WHERE id = ?";
    public static final String SELECT_DEPARTMENT = "SELECT * FROM Department WHERE id = ?";
    public static final String DELETE_DEPARTMENT = "DELETE FROM Department WHERE id = ?";
    // Additional queries for relationships and complex fetches.
}
