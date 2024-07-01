import java.util.*;
public class Person {
    //Attributes
    private String name;         // First name of the person
    private String surname;     // Last name of the person
    private String email;       // Email of person

    //Constructors
    public Person() {
    }

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    // Setters and Getters for name
    public void Setname(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }


    // Setters and Getters for surname
    public void Setsurname(String surname){
        this.surname=surname;
    }
    public String getSurName(){
        return surname;
    }


    // Setters and Getters for email
    public void Setemail(String email){
        this.email=email;
    }

    public String getEmail(){
        return email;
    }

    public void gettingInfo(){      //get personal information from the user
        Scanner inputPersonalInfo = new Scanner(System.in);
        System.out.println("Personal Information");
        boolean validationFName=true;
        boolean validationSName=true;
        boolean validationeEmail=true;

        while(validationFName){
            System.out.print("First Name : ");
            String fName=inputPersonalInfo.next();

            if(fName.matches("[a-zA-Z]+\\.?")) {        //First name validation
                Setname(fName);
                validationFName = false;

            }
            else {
                System.out.println("Please input valid name!");
            }
        }

        while(validationSName) {
            System.out.print("Surname : ");
            String sName = inputPersonalInfo.next();

            if(sName.matches("[a-zA-Z]+\\.?")){         //surname validation
                Setsurname(sName);
                validationSName=false;

            }
            else {
                System.out.println("Please input valid name!");
            }
        }
        while(validationeEmail){
            System.out.print("Email : ");
            String email=inputPersonalInfo.next();

            //email validation
            if(email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
                Setemail(email);
                validationeEmail=false;
            }
            else{
                System.out.println("Please input valid email!");
            }
        }

        String returnFName=getName();
        String returnSName=getSurName();
        String returnEmail= getEmail();

    }





}
