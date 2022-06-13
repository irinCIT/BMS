package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    // this class just creates a driver and a PreparedStatement and runs the program
    public static void main(String[] args) throws SQLException {

        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "12345678");
        PreparedStatement z = null;
        LogIn.logIn_menu(z, myConn);

    }

}