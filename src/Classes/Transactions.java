package Classes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transactions {
    public static Scanner input = new Scanner(System.in);
    public static LocalTime time = LocalTime.now();
    public static LocalDate date = LocalDate.now();
    public static int subject_id;
    public static int balance;

//    the getters and setters are for the id and balance, to manipulate them during this program
    public static int getSubject_id() {
        return subject_id;
    }
    public static int getBalance() {
        return balance;
    }

    public static void setSubject_id(int subject_id) {
        Transactions.subject_id = subject_id;
    }
    public static void setBalance(int balance) {
        Transactions.balance = balance;
    }


    // this array of Strings stores some responses and queries
    public static String[] responses = {"That was not an option",
                                        "Incorrect input",
                                        "An error occurred",
                                        "SELECT * FROM registrario", "SELECT * FROM transactions",
                                        "SELECT * FROM transactions WHERE subject_id=",
                                        "SELECT balance FROM registrario WHERE id= ",
                                        "UPDATE registrario SET balance=? WHERE id=?",
                                        "INSERT INTO transactions (subject_id,date,time,type) values(?,?,?,?)",
                                        "DEPOSIT", "WITHDRAW"};



    /*
    this method gets all the rows from the transactions table(using a ResultSet) where the subject_id equals the actual id
    of the employee logged in and displays all the rows
     */
    public static void history(PreparedStatement z, Connection x) {
        try{
            String str = responses[5] + " " + getSubject_id();
            z = x.prepareStatement(str);
            ResultSet rs = z.executeQuery(str);
            System.out.println("ID\tDATE\t\tTIME\t\tTYPE");
            while (rs.next()){
                int id = rs.getInt(2);
                Date date = rs.getDate(3);
                Time time = rs.getTime(4);
                String type = rs.getString(5);
                System.out.println(id + "\t" + date +"\t" + time + "\t" + type);
            }
            System.out.println("\nYOUR BALANCE IS: " + getBalance() + "\n");
            Menus.employee_menu(z, x);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Error e){
            System.err.println(responses[2]);
        }
    }

    /*
    this method stores the balance of the logged in employee from the registrario table
    asks the employee about the desirable amount to deposit, makes the addition
    this new balance gets stored into the registrario table using UPDATE and the id of the employee
    and then in the transactions table is inserted a new line containing
    the employee id, actual time, actual date, and string = "Deposit" + the desirable amount to deposit
     */
    public static void deposit(PreparedStatement z, Connection x){
        System.out.println("\nHow much money will you deposit?");
        try{
            String str = responses[6] + getSubject_id();
            int answer = input.nextInt();
            int balance = 0;
            z = x.prepareStatement(str);
            ResultSet rs = z.executeQuery(str);
            while (rs.next()){
                 balance = rs.getInt(1);
            }
            int sum = answer + balance;
            setBalance(sum);
            String str1 = responses[7];
            z = x.prepareStatement(str1);
            z.setInt(1, sum);
            z.setInt(2, getSubject_id());
            z.execute();

            String type = responses[9] + " + " + answer;
            String query = responses[8];
            z = x.prepareStatement(query);
            z.setInt(1, getSubject_id());
            z.setDate(2, Date.valueOf(date));
            z.setTime(3, Time.valueOf(time));
            z.setString(4, type);
            z.execute();
            System.out.println("\nDEPOSIT successful\n");
            history(z, x);

        }catch (InputMismatchException e){
            System.err.println(responses[1] + "\nTry Again");
            deposit(z, x);
        }catch (Error e){
            System.err.println(responses[2]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /*
    this method stores the balance of the logged in employee from the registrario table
    asks the employee about the desirable amount to withdraw, makes the subtraction
    (the method will repeat itself if the balance after the subtraction is negative)
    this new balance gets stored into the registrario table using UPDATE and the id of the employee
    and then in the transactions table is inserted a new line containing
    the employee id, actual time, actual date, and string = "Withdraw" + the desirable amount to withdraw

     */
    public static void withdraw(PreparedStatement z, Connection x){
        System.out.println("\nHow much money will you withdraw?");
        try{
            String balance_query = responses[6] + getSubject_id();
            int answer = input.nextInt();
            int balance = 0;
            z = x.prepareStatement(balance_query);
            ResultSet rs = z.executeQuery(balance_query);
            while (rs.next()){
                balance = rs.getInt(1);
            }
            int sum = balance - answer;
            if (sum > 0){
                setBalance(sum);
                String update_query = responses[7];
                z = x.prepareStatement(update_query);
                z.setInt(1, sum);
                z.setInt(2, getSubject_id());
                z.execute();

                String type = responses[10] + " - " + answer;
                String transactions_query = responses[8];
                z = x.prepareStatement(transactions_query);
                z.setInt(1, getSubject_id());
                z.setDate(2, Date.valueOf(date));
                z.setTime(3, Time.valueOf(time));
                z.setString(4, type);
                z.execute();
                System.out.println("\nWITHDRAW successful\n");
                history(z, x);
            }else{
                System.err.println("You have not enough balance to withdraw " + getBalance() + "\nTry Again");
                Menus.employee_menu(z, x);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InputMismatchException e){
            System.err.println(responses[1] +"\nTry Again");
            withdraw(z, x);
        }


    }














}
