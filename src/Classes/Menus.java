package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

    public static Scanner input = new Scanner(System.in);

//    this method asks the employee on what action he/she wants to make, and calls those methods respectively
    public static void employee_menu(PreparedStatement z, Connection x){


        System.out.println("\nWELCOME TO OUR MENU\n--------------------------\nYou can:\nDeposit (D)\nWithdraw (W)\nCheck transactions history(T)\nEXIT (E)");
        try{
            String answer = input.next();
            if (answer.equalsIgnoreCase("d")){
                Transactions.deposit(z, x);
            }else if (answer.equalsIgnoreCase("w")){
                Transactions.withdraw(z, x);
            }else if (answer.equalsIgnoreCase("t")){
                Transactions.history(z, x);
            }else if (answer.equalsIgnoreCase("e")){
                LogIn.logIn_menu(z, x);
            }else{
                System.err.println("That was not an option");
                employee_menu(z, x);
            }
        }catch (InputMismatchException e){
            e.printStackTrace();
        }catch (Error e){
            System.err.println(e.getMessage());
        }

    }

//    this method asks the employer on what action he/she wants to make, and calls those methods respectively
    public static void employer_menu(PreparedStatement z, Connection x) throws SQLException {
        String[] parts = Functions.admin(z, x).split(" ");
        String[] part1 = parts[1].split("-");
        System.out.println("WELCOME " + part1[0] + "\nYou can: \nAdd Customer (A)\nDelete Customer (D)\nBalances (B)\nEXIT (E)");
        try{
            String answer = input.next();
            if (answer.equalsIgnoreCase("a")){
                Functions.add_Customer(z, x);
            }else if (answer.equalsIgnoreCase("d")){
                Functions.delete_Customer(z, x);
            }else if (answer.equalsIgnoreCase("b")){
                Functions.balances(z, x);
            }else if (answer.equalsIgnoreCase("e")){
                System.exit(1);
            }else{
                System.err.println("That was not an option\nTry again");
                employer_menu(z, x);
            }
        }catch (InputMismatchException e){
            System.err.println("Invalid Input");
            employer_menu(z, x);
        }catch (Error e){
            System.err.println(e.getMessage());
            employer_menu(z, x);
        }

    }

}
