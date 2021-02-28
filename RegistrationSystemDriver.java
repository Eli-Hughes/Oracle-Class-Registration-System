
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// Driver class used to create an implementation of the RegistrationSystem class
public class RegistrationSystemDriver {

    public static void main(String[] args) throws SQLException, IOException {

        System.out.println("\nMenu:");
        System.out.println("***************************************************************");
        System.out.println("***                                                         ***");
        System.out.println("***          Welcome to Online Registration System          ***");
        System.out.println("***                                                         ***");
        System.out.println("***************************************************************");
        System.out.println("\n    1. Add a Course");
        System.out.println("    2. Delete a Course");
        System.out.println("    3. Add a Student");
        System.out.println("    4. Delete a Student");
        System.out.println("    5. Register a Course");
        System.out.println("    6. Drop a Course");
        System.out.println("    7. Check Student Registration");
        System.out.println("    8. Upload Grades");
        System.out.println("    9. Check Grade");
        System.out.println("    10. Quit");

        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        // replace parameters with actual username/password
        RegistrationSystem regSys = new RegistrationSystem("username", "password");

        while (true) {

            System.out.print("\nEnter a Command: ");
            String input = scan.next();

            if (input.equals("1")) {
                System.out.print("Command \"1\" recognized as \"Add a Course\"");
                System.out.print("\nEnter *Course Code* of new course: ");
                String code = scan.next();
                System.out.print("Enter *Title* of new course: ");
                String title = scan.next();
                if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else if (title.length() > 50) System.out.println("Error: *title* exceeds maximum length");
                else {
                    boolean exists = regSys.addCourse(code, title);
                    if (!exists) {
                        System.out.println(title + " has not been added since it already exists as a course.");
                    } else {
                        System.out.println(title + " has been added to the list of available courses!");
                    }
                }
            }

            else if (input.equals("2")) {
                System.out.print("Command \"2\" recognized as \"Delete a Course\"");
                System.out.print("\nEnter *Course Code* of course to be deleted: ");
                String code = scan.next();
                if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else {
                    boolean exists = regSys.deleteCourse(code);
                    if (!exists) {
                        System.out.println(code + " has not been deleted since it does not exist as a course.");
                    } else {
                        System.out.println(code + " has been deleted from the registration system.");
                    }
                }
            }

            else if (input.equals("3")) {
                System.out.print("Command \"3\" recognized as \"Add a Student\"");
                System.out.print("\nEnter *ssn* for new student: ");
                String ssn = scan.next();
                System.out.print("Enter *name* for new student: ");
                String name = scan.next();
                System.out.print("Enter *address* for new student: ");
                String address = scan.next();
                System.out.print("Enter *major* for new student: ");
                String major = scan.next();
                if (name.length() > 50) System.out.println("Error: *name* exceeds maximum length");
                else if (address.length() > 100) System.out.println("Error: *address* exceeds maximum length");
                else if (major.length() > 10) System.out.println("Error: *major* exceeds maximum length");
                else {
                    int verify = regSys.addStudent(ssn, name, address, major);
                    if (verify == -1) {
                        System.out.println("Error: invalid *ssn*");
                    } else if (verify == 0) {
                        System.out.println("Error: Student already exists in the system");
                    } else {
                        // verify == 1
                        System.out.println("Student was successfully put into the system");
                    }
                }
            }

            else if (input.equals("4")) {
                System.out.print("Command \"4\" recognized as \"Delete a Student\"");
                System.out.print("\nEnter *ssn* of the Student to be deleted: ");
                String ssn = scan.next();
                int verify = regSys.deleteStudent(ssn);
                if (verify == -1) {
                    System.out.println("Error: invalid *ssn*");
                } else if (verify == 0) {
                    System.out.println("Error: Student does not exist in the system");
                } else {
                    // verify == 1
                    System.out.println("Student was successfully deleted from the system");
                }
            }

            else if (input.equals("5")) {
                System.out.print("Command \"5\" recognized as \"Register a Course\"");
                System.out.print("\nEnter *ssn* of student being registered: ");
                String ssn = scan.next();
                System.out.print("Enter *course code* of course being registered for: ");
                String code = scan.next();
                System.out.print("Enter *year* for when the course is being taken: ");
                String year = scan.next();
                System.out.print("Enter *semester* for when the course is being taken: ");
                String semester = scan.next();
                if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else if (!regSys.verifyYear(year)) System.out.println("Error: invalid *year*");
                else if (semester.length() > 10) System.out.println("Error: *year* exceeds maximum length");
                else {
                    int verify = regSys.registerCourse(ssn, code, year, semester);
                    if (verify == -1) System.out.println("Error: invalid *ssn*");
                    else if (verify == 0) System.out.println("Error: Student does not exist in the system");
                    else if (verify == 1) System.out.println("Error: Course does not exist in the system");
                    else if (verify == 2) System.out.println("Error: Student is already registered for this course");
                    else if (verify == 3) System.out.println("Student was successfully registered for " + code);
                }
            }

            else if (input.equals("6")) {
                System.out.print("Command \"6\" recognized as \"Drop a Course\"");
                System.out.print("\nEnter *ssn* of student being dropped: ");
                String ssn = scan.next();
                System.out.print("Enter *course code* of course being dropped: ");
                String code = scan.next();
                System.out.print("Enter *year* for when the course is being taken: ");
                String year = scan.next();
                System.out.print("Enter *semester* for when the course is being taken: ");
                String semester = scan.next();
                if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else if (!regSys.verifyYear(year)) System.out.println("Error: invalid *year*");
                else if (semester.length() > 10) System.out.println("Error: *semester* exceeds maximum length");
                else {
                    int verify = regSys.dropCourse(ssn, code, year, semester);
                    if (verify == -1) System.out.println("Error: Invalid *ssn*");
                    else if (verify == 0) System.out.println("Error: Student does not exist in the system");
                    else if (verify == 1) System.out.println("Error: Student not registered for this course");
                    else if (verify == 2) System.out.println("Student successfully dropped from the course");
                }
            }

            else if (input.equals("7")) {
                System.out.print("Command \"7\" recognized as \"Check Student Registration\"");
                System.out.print("\nEnter *ssn* for student to view their registered courses: ");
                String ssn = scan.next();
                int verify = regSys.verifyRegistration(ssn);
                if (verify == -1) System.out.println("Error: invalid *ssn*");
                else if (verify == 0) System.out.println("Error: Student does not exist in the system");
                else if (verify == 1) System.out.println("Error: Student is not registered for any courses");
                else if (verify == 2) {
                    System.out.println("Courses that this student is registered for: ");
                    System.out.println("CODE        YEAR        SEMESTER");
                    System.out.println("********************************");
                    ResultSet rs = regSys.checkRegistration(ssn);
                    while (rs.next()) {
                        System.out.println(rs.getString("code") + "  " + rs.getString("semester") +
                                "   " + rs.getString("year"));
                    }
                }
            }

            else if (input.equals("8")) {
                System.out.print("Command \"8\" recognized as \"Upload Grades\"");
                System.out.print("\nEnter *ssn* for student whose grades are to be updated: ");
                String ssn = scan.next();
                System.out.print("Enter *course code* for course you wish to upload grades for: ");
                String code = scan.next();
                System.out.print("Enter *year* for when this course was offered: ");
                String year = scan.next();
                System.out.print("Enter *semester* for when this course offered: ");
                String semester = scan.next();
                int verify = regSys.verify_ssn(ssn);
                if (verify == -1) System.out.println("Error: invalid *ssn*");
                else if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else if (!regSys.verifyYear(year)) System.out.println("Error: invalid *year*");
                else if (semester.length() > 10) System.out.println("Error: *semester* exceeds maximum length");
                else {
                    System.out.print("Enter *grade* the student made in " + code + ": ");
                    String grade = scan.next();
                    if (regSys.verifyGrade(grade)) regSys.uploadGrade(ssn, code, year, semester, grade);
                    else System.out.println("Error: invalid *grade*");
                }
            }

            else if (input.equals("9")) {
                System.out.print("Command \"9\" recognized as \"Check Grade\"");
                System.out.print("\nEnter *ssn* for student whose grade is being looked up: ");
                String ssn = scan.next();
                System.out.print("Enter *course code* for class which the student is taking: ");
                String code = scan.next();
                System.out.print("Enter *year* for when the student took the course: ");
                String year = scan.next();
                System.out.print("Enter *semester* for when the student took the course: ");
                String semester = scan.next();
                if (code.length() > 10) System.out.println("Error: *code* exceeds maximum length");
                else if (!regSys.verifyYear(year)) System.out.println("Error: invalid *year*");
                else if (semester.length() > 10) System.out.println("Error: *semester* exceeds maximum length");
                else {
                    int verify = regSys.verify_ssn(ssn);
                    if (verify == -1) System.out.println("Error: invalid *ssn*");
                    else {
                        ResultSet rs = regSys.checkGrade(ssn, code, year, semester);
                        rs.next();
                        if (rs.getString("grade") == null)
                            System.out.println("Student's grade in " + code + " is not available");
                        else System.out.println("Student's grade in " + code + " is: " + rs.getString("grade"));
                    }
                }
            }

            else if (input.equals("10")) {
                System.out.println("Command \"10\" recognized as \"Quit\"");
                scan.close();
                regSys.getStatement().close();
                regSys.getConnection().close();
                System.out.println("Logout Successful");
                return;
            }

            else {
                System.out.println("Error: Unrecognized Command");
            }
        }
    }
}
