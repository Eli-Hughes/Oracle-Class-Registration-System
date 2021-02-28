
import java.io.IOException;
import java.sql.*;

// class which creates a connection to Oracle server through the Database class and
// offers methods which can perform operations that would be found in a registration system.
public class RegistrationSystem {
    private String username;
    private String password;
    private Database database;
    private Connection connection;
    private Statement statement;

    public RegistrationSystem(String username, String password) throws SQLException, IOException{
        this.username = username;
        this.password = password;
        database = new Database(this.username, this.password);
        connection = database.getConnection();
        statement = database.getStatement();
    }

    // return true -> course successfully added
    // return false -> course wasn't added
    public boolean addCourse(String code, String title) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("select * from Course where code = '" + code + "'");
        // if course doesn't already exist, add course
        if (!rs.next()) {
            rs.close();
            statement.executeUpdate("insert into Course values('" + code + "', '" + title + "')");
            return true;
        // if course already exists, don't add the course
        } else {
            rs.close();
            return false;
        }
    }

    // return false -> course wasn't deleted
    // return true -> course was deleted
    public boolean deleteCourse(String code) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("select * from Course where code = '" + code + "'");
        // if the course doesn't exist, then it can't be deleted
        if (!rs.next()) {
            rs.close();
            return false;
        // if course exists, delete it
        } else {
            rs.close();
            statement.executeUpdate("delete from Registered where code = '" + code + "'");
            statement.executeUpdate("delete from Course where code = '" + code + "'");
            return true;
        }
    }

    // if return -1 -> problem with ssn
    // if return 0 -> student already exists in the system
    // if return 1 -> student successfully put in the system
    public int addStudent(String ssn, String name, String address, String major) throws SQLException, IOException {
        int verified_ssn = verify_ssn(ssn);
        // if problem with ssn, don't continue
        if (verified_ssn == -1) return -1;
        ResultSet rs = statement.executeQuery("select * from Student where ssn = '" + verified_ssn + "'");
        // if rs is empty, aka the student is not already in the system, add them to to the system. if rs isn't empty, don't add student
        if (!rs.next()) {
            rs.close();
            statement.executeUpdate("insert into Student values('" + verified_ssn + "', '" + name + "', '" + address + "', '" + major + "')");
        } else {
            rs.close();
            return 0;
        }
        return 1;
    }

    // return -1 -> problem with ssn
    // return 0 -> student not in the system
    // return 1 -> student successfully removed from the system
    public int deleteStudent (String ssn) throws SQLException, IOException{
        int verified_ssn = verify_ssn(ssn);
        // if problem with ssn, return -1
        if (verified_ssn == -1) return -1;
        ResultSet rs = statement.executeQuery("select * from Student where ssn = '" + verified_ssn + "'");
        // if student doesn't exist, can't delete them from the system
        if (!rs.next()) {
            rs.close();
            return 0;
        // if student does exist, delete them from the system
        } else {
            rs.close();
            statement.executeUpdate("delete from Registered where ssn = '" + verified_ssn + "'");
            statement.executeUpdate("delete from Student where ssn = '" + verified_ssn + "'");
            return 1;
        }
    }

    // return -1 -> problem with ssn
    // return 0 -> student doesn't exist in the system
    // return 1 -> course doesn't exist
    // return 2 -> student is already registered for the course
    // return 3 -> student successfully registered for the course
    public int registerCourse (String ssn, String code, String year, String semester) throws SQLException, IOException{
        int verified_ssn = verify_ssn(ssn);
        // if problem with ssn, return -1
        if (verified_ssn == -1) return  -1;
        ResultSet rs1 = statement.executeQuery("select * from Student where ssn = '" + verified_ssn + "'");
        // if student is not in the system, don't register them for a course
        if (!rs1.next()) {
            rs1.close();
            return 0;
        }
        rs1.close();
        // if the course doesn't exist, don't register student for the nonexistent course
        ResultSet rs2 = statement.executeQuery("select * from Course where code = '" + code + "'");
        if (!rs2.next()) {
            rs2.close();
            return 1;
        }
        ResultSet rs3 = statement.executeQuery("select * from Registered where ssn = '" + verified_ssn + "' and code = '" + code +
                "' and year = '" + year + "' and semester = '" + semester + "'");
        // if student is already registered for this course in the same year and semester, don't register them again
        if (rs3.next()) {
            rs3.close();
            return 2;
        }
        // else, register the student for the course
        else {
            rs3.close();
            statement.executeUpdate("insert into Registered(ssn, code, year, semester) values ('" + verified_ssn + "', '" + code +
                    "', '" + year + "', '" + semester + "')");
            return 3;
        }
    }

    // return -1 -> problem with ssn
    // return 0 -> student not in the system
    // return 1 -> student not registered for the course in the first place
    // return 2 -> student successfully dropped from the course
    public int dropCourse (String ssn, String code, String year, String semester) throws SQLException, IOException {
        int verified_ssn = verify_ssn(ssn);
        // if problem with ssn, don't continue
        if (verified_ssn == - 1) return -1;
        // if student is not in the system, don't drop them from a course
        ResultSet rs1 = statement.executeQuery("select * from Student where ssn = '" + verified_ssn + "'");
        if (!rs1.next()) {
            rs1.close();
            return 0;
        }
        rs1.close();
        ResultSet rs2 = statement.executeQuery("select * from Registered where ssn = '" + verified_ssn + "' and code = '" + code +
                "' and year = '" + year + "' and semester = '" + semester + "'");
        // if student is not registered for the course, you can't drop them from it
        if (!rs2.next()) {
            rs2.close();
            return 1;
        }
        else {
            rs2.close();
            statement.executeUpdate("delete from Registered where ssn = '" + verified_ssn + "' and code = '" + code + "' and year ='" +
                    year + "' and semester = '" + semester + "'");
            return 2;
        }
    }

    // WARNING:
    // before this method is called with an ssn, you should call verifyRegistration (String ssn) with the same ssn being passed into this method
    public ResultSet checkRegistration (String ssn) throws SQLException, IOException {
        // return the course codes for all of all the courses that this ssn is registered for
        return statement.executeQuery("select code, year, semester from Registered where ssn = '" + ssn + "'");
    }

    // For Driver: this method is intended to work with the result of checkRoll
    public void uploadGrade (String ssn, String code, String year, String semester, String grade) throws SQLException, IOException {
        statement.executeUpdate("update Registered set grade = '" + grade.toUpperCase() + "' where ssn = '" + ssn + "' and code = '" + code +
                "' and year = '" + year + "' and semester = '" + semester + "'");
    }

    // look up a student's grade in a course
    public ResultSet checkGrade (String ssn, String code, String year, String semester) throws SQLException, IOException {
        return statement.executeQuery("select grade from Registered where ssn = '" + ssn + "' and code = '" + code + "' and year = '" + year +
                "' and semester = '" + semester + "'");
    }

    public int verify_ssn (String ssn) throws SQLException, IOException{
        // if length of ssn == 9, proceed. Else, invalid ssn
        if (ssn.length() == 9) {
            // try to return a parsed integer. If if an error is caught -> invalid ssn
            try {
                return Integer.parseInt(ssn);
            } catch (NumberFormatException e) {
                return -1;
            }
        } else {
            return -1;
        }
    }

    // verify that an ssn is valid, that it is associated with a student in the system, and that that student is registered for courses
    public int verifyRegistration (String ssn) throws SQLException, IOException{
        int verified_ssn = verify_ssn(ssn);
        // if problem with ssn, return
        if (verified_ssn == -1) return -1;
        // if student doesn't exist return 0
        ResultSet rs = statement.executeQuery("select * from Student where ssn = '" + verified_ssn + "'");
        if (!rs.next()) {
            rs.close();
            return 0;
        }
        ResultSet rs2 = statement.executeQuery("select code from Registered where ssn = '" + verified_ssn + "'");
        // if student is not registered for any courses, return 1
        if (!rs2.next()) {
            rs2.close();
            return 1;
        }
        // if student exists and is registered for courses, return 2
        else {
            return 2;
        }
    }

    // returns all of the ssn registered for a course
    public ResultSet getRoll (String code, String year, String semester) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("select ssn from Registered where code = '" + code + "' and year = '" + year + "' and semester = '" +
                semester + "'");
        return rs;
    }

    // make sure a grade is valid
    public boolean verifyGrade (String grade) {
        if (!(grade.toUpperCase().equals("A") || grade.toUpperCase().equals("B") || grade.toUpperCase().equals("C") || grade.toUpperCase().equals("D")
                || grade.toUpperCase().equals("F"))) return false;
        else return true;
    }

    public boolean verifyYear (String year) {
        // if length of year==4, proceed. Else, invalid year
        if (year.length() == 4) {
            // try to return a parsed integer. If if an error is caught -> invalid year
            try {
                Integer.parseInt(year);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
} 