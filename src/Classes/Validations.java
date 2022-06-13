package Classes;

public class Validations {

    // this method using regular expression, allows the name to be only one Capitalised word (containing only letters)
    public static boolean validateUserName(String inputName){
//        Irin
        return inputName.matches("[A-Z][a-zA-Z]*");
    }

    // this method using regular expression, allows the last name to be only one Capitalised word (containing only letters)
    public static boolean validateUserLastName(String inputLastName){
//        Vokopola
        return inputLastName.matches("[A-Z][a-zA-Z]*");
    }

}
