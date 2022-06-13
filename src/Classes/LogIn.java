package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LogIn {

    public static Scanner input = new Scanner(System.in);

    /*
    this method has the role of the main interface
    it asks the user if he/she will be logged in as an employer or as an employee
    if the user wants to get logged in as an employee the program will ask for his/her name, last name and password
    after performing validation, if any of the lines from the method readFromDB() of the Functions class contains all the inputs
    the program stores this employee's balance and id and then calls the method employee_menu() from the Menus class

    if the user wants to get logged in as an employer the program will ask only for the password
    if this password matches the one that we get from the admin() method of the class Functions then we call the employer_menu
     */
    public static void logIn_menu(PreparedStatement z, Connection x){
        try{
            System.out.println("WELCOME TO BMS\nChoose below your position:\nEmployee (A)\nEmployer (B)\nEXIT (E)");
            String answer = input.next();
            if (answer.equalsIgnoreCase("a")){
                System.out.println("Enter your name: ");
                String user_name = input.next();
                if (Validations.validateUserName(user_name)){
                    System.out.println("Enter your Last name: ");
                    String user_last_name = input.next();
                    if (Validations.validateUserLastName(user_last_name)){
                        System.out.println("Enter your password: ");
                        String user_password = input.next();
                        String user_full_name = user_name.trim() + " " + user_last_name.trim();
                        for (String line: Functions.readFromDB(z, x)){
                            if (line.contains(user_full_name)
                                    &&
                                    line.contains(user_password)
                            ){
                                String[] parts = line.split(" ");
                                Transactions.setSubject_id(Integer.parseInt(parts[0]));
                                Transactions.setBalance(Integer.parseInt(parts[4]));
                                Menus.employee_menu(z, x);
                                break;
                            }else{
                                continue;
                            }
                        }
                    }else{
                        System.err.println("The last name should be capitalised: <<Surname>>");
                    }
                }else{
                    System.err.println("The name should be capitalised: <<Name>>");
                }
            }else if (answer.equalsIgnoreCase("b")){
                System.out.println("Enter your password: ");
                String admin_password = input.next();
                String[] parts = Functions.admin(z, x).split(" ");
                if (admin_password.equals(parts[2])){
                    Menus.employer_menu(z, x);
                }else{
                    System.err.println("You are not the CEO of our bank!\nTry again");
                    logIn_menu(z, x);
                }
            }else if (answer.equalsIgnoreCase("e")){
                System.exit(1);
            }else{
                System.err.println("That was not an option");
                logIn_menu(z, x);
            }
        }catch (InputMismatchException e) {
            e.printStackTrace();
        }catch (Error e){
            System.err.println(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
