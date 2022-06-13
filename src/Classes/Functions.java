package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Functions {

    public static Scanner input = new Scanner(System.in);

    /*
    this method reads the lines from the database and stores them into a List of Strings.
    It selects all from the table registrario and we prepare this statement using a PreparedStatement
    Then we use a ResultSet to execute the query, these values are stored into variables accordingly
    These variables are then stored into a string, and this string into the list (all of this in a while loop)
     */
    public static List<String> readFromDB(PreparedStatement z, Connection x) throws SQLException {
        String str = "select * from registrario";
        List<String> lines = new ArrayList<>();
        z = x.prepareStatement(str);
        ResultSet rs = z.executeQuery(str);
        while (rs.next()){
            int id = rs.getInt(1);
            String full_name = rs.getString(2);
            String password = rs.getString(3);
            int balance = rs.getInt(4);
            String line = id + " " + full_name + " " + password + " " + balance;
            lines.add(line);
        }
        return lines;
    }

    /*
    this method uses the same principle as the method above, but it does not read the table registrario,
    but the table employers. and because we do not have multiple employers but only one,
    this method returns a single String and not a List of Strings
     */
    public static String admin(PreparedStatement z, Connection x) throws SQLException{
        String str = "select * from employers";
        z = x.prepareStatement(str);
        ResultSet rs = z.executeQuery(str);
        int id = 0;
        String full_name = "";
        String password = "";
        String text = "";
        while (rs.next()){
            id = rs.getInt(1);
            full_name = rs.getString(2);
            String[] parts_of_name = full_name.split(" ");
            String name = parts_of_name[0];
            String last_name = parts_of_name[1];
            password = rs.getString(3);
            text = id + " " + name + "-" + last_name + " " + password;
        }
        return text;
    }

    /*
    this method asks the employer to enter the customer's credentials
    after entering an input, the program validates this input (name, last name and balance) except the password because
    it can have numbers and letters and symbols not in a certain order. The name and last name are concatenated into one String,
    this String, the password and the balance are stored into the database table registrario useind a PreparedStatement
     */
    public static void add_Customer(PreparedStatement z, Connection x) throws SQLException {
        printRegistrario(z, x);
        System.out.println("To register a new costumer , you should add his/her name, surname, password and balance");
        System.out.println("Enter name: ");
        try{
            String name = input.next();
            if (Validations.validateUserName(name)){
                System.out.println("Enter surname: ");
                String surname = input.next();
                if (Validations.validateUserLastName(surname)){
                    System.out.println("Enter a password: ");
                    String password = input.next();
                    String full_name = name + " " + surname;
                    System.out.println("Enter " + full_name +"'s balance: ");
                    int balance = input.nextInt();
                    if (balance > 0){
                        String add_query = "insert into registrario (full_name, password,  balance) values (?, ?, ?)";
                        z = x.prepareStatement(add_query);
                        z.setString(1, full_name);
                        z.setString(2, password);
                        z.setInt(3, balance);
                        z.execute();
                        System.out.println("Registration successful\nDo you want to add another customer (y/n)");
                        String response = input.next();
                        if (response.equalsIgnoreCase("y")){
                            add_Customer(z, x);
                        }else{
                            System.out.println("Going Back");
                            Menus.employer_menu(z, x);
                        }
                    }
                }else{
                    System.err.println("Surname should be a capitalised word: <<Surname>>");add_Customer(z, x);
                }
            }else{
                System.err.println("Name should be a capitalised word: <<Name>>");add_Customer(z, x);
            }
        }catch (InputMismatchException e){
            System.err.println("Invalid Input\nTry Again"); add_Customer(z, x);
        }catch (Error e){
            System.err.println(e.getMessage());
            System.exit(1);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /*
    this method asks the employer for a customer's full name, and in the same way as the other methods work, this method
    gets this input and deletes it from the database table registrario, if it exists
     */
    public static void delete_Customer(PreparedStatement z, Connection x) throws SQLException {
        printRegistrario(z, x);
        System.out.println("This system deletes a customer by his/her name\nEnter Full Name: ");
        try{
            String full_name = input.nextLine();
            String delete_query = "delete from registrario where full_name=?";
            z = x.prepareStatement(delete_query);
            z.setString(1, full_name);
            z.execute();
            System.out.println("DELETION successful\nDo you want to delete another customer? (y/n)");
            String answer = input.next();
            if (answer.equalsIgnoreCase("y")){
                delete_Customer(z, x);
            }else {
                System.out.println("Going Back");
                Menus.employer_menu(z, x);
            }
        }catch (InputMismatchException e){
            System.err.println("Invalid Input\nTry again");delete_Customer(z, x);
        }catch (Error e){
            System.err.println(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /*
    this method iterates from the database table registrario rows, and gets only the balances.
    all the balances are stored into one variable and in the end it prints the total amount of money stored in this bank
     */
    public static void balances(PreparedStatement z, Connection x){
        try{
            printRegistrario(z, x);
            String balance_query = "select full_name,balance from registrario";
            z = x.prepareStatement(balance_query);
            ResultSet rs = z.executeQuery(balance_query);
            int balance_sum = 0;
            int count = 0;
            System.out.println("Full Name\t\t\tBalance");
            while (rs.next()){
                String full_name = rs.getString(1);
                int balance = rs.getInt(2);
                balance_sum += rs.getInt(2);
                count++;
                System.out.println(full_name + "->\t\t" + balance);
            }
            System.out.printf("\nTotal amount of money from %d customers is %d ", count, balance_sum);
            Menus.employer_menu(z, x);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /*
    this method reads the database table registrario and prints a table of all the customers stored into that table
    the method reads the table using a ResultSet
     */
    public static void printRegistrario(PreparedStatement z, Connection x) throws SQLException{
        String str = "select id, full_name, balance from registrario";
        z =x.prepareStatement(str);
        ResultSet rs = z.executeQuery(str);
        System.out.println("Id\t\tFull Name\t\tBalance\n");
        while (rs.next()){
            int id = rs.getInt(1);
            String full_name = rs.getString(2);
            int balance = rs.getInt(3);
            System.out.println(id + "\t\t" + full_name + "\t\t"  + balance);
        }
    }

}
