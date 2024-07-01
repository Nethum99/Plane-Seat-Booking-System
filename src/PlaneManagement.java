import java.util.*;
public class PlaneManagement {

    //Arrays to store seats
    static int[] rowA = new int[14];
    static int[] rowB = new int[12];
    static int[] rowC = new int[12];
    static int[] rowD = new int[14];

    //Array to store ticket information for each row
    static String[][] storeA =new String[14][6];    //rowA tickets
    static String[][] storeC =new String[12][6];    //rowB tickets
    static String[][] storeB =new String[12][6];    //rowC tickets
    static String[][] storeD =new String[14][6];    //rowD tickets

    static int total =0;    //variable to store total sales

    static boolean welcome=true;    //variable to Welcome the user


    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        //Displaying the welcome message
        if(welcome){
            System.out.println("Welcome to the Plane Management application");
            System.out.println();
            welcome=false;
        }

        //Displaying the Menu
        System.out.println("""
        ***************************************
        *             MENU OPTION             *
        ***************************************
                    1 )Buy a seat
                    2 )Cancel a seat
                    3 )Find first available seat
                    4 )Show seating plan
                    5 )Print tickets information and total sales
                    6 )Search ticket
                    0 )Quit
        ***************************************""");
        System.out.print("Please select an option :");


        int userMenuOptionChoice;        //Declaring a variable to store user's menu option input

        try{           //Trying to read an integer value from the user
        userMenuOptionChoice = input.nextInt();
        optionMenuFilter(userMenuOptionChoice);     //calling the method to filter and handle the user input
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a number in the menu");
            main(new String[0]);        //calling the main method for allow the user to input valid value
        }

    }
    private static void afterBooking(String[][] store, int seatNum, String rowLetter){

        Person personObject=new Person();     //Create new object to gather booking details.

        personObject.gettingInfo();       //call the gettingInfo() method of Person class to collect user information and validate it.
        store[seatNum-1][0]= personObject.getName();      //Store the name in the first column of the specified seat.
        store[seatNum-1][1]= personObject.getSurName();
        store[seatNum-1][2]= personObject.getEmail();     //Store the email in the first column of the specified seat.

        ticketInfo(store,seatNum,rowLetter,personObject);     //Call the ticketInfo method to further process the booking information
    }

    private static void ticketInfo(String[][] Store, int seatNum, String rowLetter, Person person) {
        int price = ticketPrice(seatNum);

        //Create a new Ticket object with the provided row letter, seat number, price, and person details
        Ticket ticketInfoObject = new Ticket(rowLetter,seatNum,price,person);

        ticketInfoObject.setRow(rowLetter);     //set the Row.Seat,Price attributes of the Ticket object
        ticketInfoObject.setSeat(seatNum);
        ticketInfoObject.setPrice(price);

        Store[seatNum-1][3]=ticketInfoObject.getRow();      //Store the row in the fourth column of the specified seat
        Store[seatNum-1][4] =String.valueOf(ticketInfoObject.getSeat());
        Store[seatNum-1][5] =String.valueOf(ticketInfoObject.getPrice());



        Ticket.saveFile(rowLetter,seatNum,price,person);        //call saveFile method  for save ticket information to text file.
    }

    private static void optionMenuFilter(int userMenuOptionChoice){         //Filter user's menu option and performs its actions.

        switch (userMenuOptionChoice){      //userMenuOptionChoice is the option chosen by user in option menu.

            case 1:
                buySeat();      //Buy a seat.
                main(new String[0]);
                break;

            case 2:
                cancelSeat();       //Cancel a seat.
                main(new String[0]);
                break;

            case 3:
                findFirstAvailableCheckRows(true);      //Find the first available seat.
                main(new String[0]);
                break;

            case 4:
                printingPlan();
                main(new String[0]);        //Print the seating plan.
                break;

            case 5:
                printTicketTotal(rowA, rowB, rowC, rowD, storeA, storeB, storeC, storeD, total);        //Print ticket information for bookings and show total sales.
                break;

            case 6:
                searchTicket();     //Search ticket
                break;

            case 0:
                System.out.println();       //Exit in programme without error.
                break;

            default:
                System.out.println("Invalid option number. Enter valid option number!");        //Invalid option number and call main method
                main(new String[0]);
                break;
        }

    }

    private static void cancelSeat() {
        String rowLetter;       // Variable to store the row letter entered by the user
        int seatNum;        // Variable to store the seat number entered by the user
        Scanner inputCancelSeat = new Scanner(System.in);

        while (true){
            System.out.print("Enter row :");
            rowLetter = inputCancelSeat.next().toUpperCase();

            // Check if the entered row letter is valid (A, B, C, or D)
            if((rowLetter.equals("A") || rowLetter.equals("B") || rowLetter.equals("C") || rowLetter.equals("D"))){
                break;      // Exit the loop if the input is valid
            }
            else{
                System.out.println("Invalid row letter");       // Display error message for invalid input
            }

        }

        while (true) {
            try {
                System.out.print("Enter seat number :");
                seatNum = inputCancelSeat.nextInt();

                // Prompt the user to enter a valid seat number (1-14)
                if (1 <= seatNum && seatNum <= 14) {
                    break;
                } else {
                    System.out.println("Enter valid seat number (1-14)");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid seat number.");
                inputCancelSeat.next(); // Consume invalid input
            }

        }

        // Check the row letter and call the method to cancel the seat
        if (rowLetter.equals("A")) {
            seatCheckForCancellation(rowA,seatNum,rowLetter, storeA);
        } else if (rowLetter.equals("B")) {
            seatCheckForCancellation(rowB, seatNum, rowLetter, storeB);
        } else if (rowLetter.equals("C")) {
            seatCheckForCancellation(rowC, seatNum, rowLetter, storeC);
        } else if (rowLetter.equals("D")) {
            seatCheckForCancellation(rowD, seatNum, rowLetter, storeD);
        }
    }

    private static void seatCheckForCancellation(int[]row, int seatNum, String rowLetter, String[][] store){
        if (row[seatNum - 1] == 1) {        // Check if the seat is reserved
            row[seatNum - 1] =0;        //Reset the seat status to available(0)
            for(int i=0;i<6;i++){
                store[seatNum-1][i]=null;
            }
            Ticket.deleteSavedFile(rowLetter,seatNum);      // Delete the saved ticket file for the cancelled sea
            System.out.println("Your row " + rowLetter + " number " + seatNum + " seat has been cancelled");
        }
        else{
            System.out.println("There are no seats matching your entry");       //If the seat is not booked, inform the user that there are no matching seats
        }

    }

    private static void fourteenSeats(){        //Display Price List
        System.out.println("Ticket Prices\nSeat Number    Price");
        System.out.println("  1-5          £200\n  6-9          £150\n  10-14        £180");
    }
    private static void twelveSeats(){      //Display Price List
        System.out.println("Ticket Prices\nSeat Number    Price");
        System.out.println("  1-5          £200\n  6-9          £150\n  10-12        £180");
    }


    private static void buySeat() {     //method to handle booking a seat
        Scanner input = new Scanner(System.in);
        String rowLetter;       //declaring a variable for indentify row.

            while (true) {
                System.out.print("Enter row (A or B or C or D) :");
                rowLetter = input.next().toUpperCase();     //Reads the user input for the row letter and converts it to uppercase

                if (rowLetter.equals("A") || rowLetter.equals("D")) {
                    fourteenSeats();        //"Calling the fourteenSeats method to display ticket prices for the fourteen-seat rows
                    break;
                }
                else if (rowLetter.equals("B") || rowLetter.equals("C")) {
                    twelveSeats();      //Calling the twelveSeats method to display ticket prices for the fourteen-seat rows
                    break;
                }
                else{
                    System.out.println("Enter Valid Row (A or B or C or D)");       //valid inputs
                }
            }


            while(true){
                try {
                    System.out.print("Enter seat number :");
                    int seatNum = input.nextInt();      //get seat number from user as Integer

                    if (rowLetter.equals("A") && (0 < seatNum && seatNum < 15)) {       //Checking the Row A seat number is within the range of 1 to 14.
                        seatCheck(rowA, seatNum, rowLetter);        //Calling the method of seatCheck for check this seat is available or not available.
                        break;
                    } else if (rowLetter.equals("B") && (0 < seatNum && seatNum < 13)) {
                        seatCheck(rowB, seatNum, rowLetter);
                        break;
                    } else if (rowLetter.equals("C") && (0 < seatNum && seatNum < 13)) {
                        seatCheck(rowC, seatNum, rowLetter);
                        break;
                    } else if (rowLetter.equals("D") && (0 < seatNum && seatNum < 15)) {
                        seatCheck(rowD, seatNum, rowLetter);
                        break;      //exit the loop after processing the seat.

                    } else {
                        System.out.println("Invalid seat number. Enter valid seat number!");
                    }
                }
                catch (ArrayIndexOutOfBoundsException e){       // Catch the exception if the entered seat number is out of range
                    System.out.println("Seat number is in out of range.Enter valid seat number!");       //Notify the user about the out-of-range seat number
                }

                catch (InputMismatchException e) {
                    System.out.println("Invalid seat number.Enter valid seat number!");
                    input.next(); // Consume invalid input
                }
            }

    }


    private static void seatCheck(int []row, int seatNum, String rowLetter){

        if (row[seatNum - 1] == 1) {        //Check if the seat is already booked or not
            System.out.println("This seat is already reserved. Please select another seat");
            System.out.println(" ");
            buySeat();
        }
        else {      //If the seat is available for booking
            row[seatNum - 1] = 1;
            if(rowLetter.equals("A")){
                afterBooking(storeA,seatNum,"A");       //Call afterBooking method to process the booking and store the ticket information.
            }
            else if(rowLetter.equals("B")){
                afterBooking(storeB,seatNum,"B");
            }
            else if(rowLetter.equals("C")){
                afterBooking(storeC,seatNum,"C");
            }
            else if(rowLetter.equals("D")){
                afterBooking(storeD,seatNum,"D");
            }
            System.out.println("Your booking for Row " + rowLetter + " Seat " + seatNum + " has been confirmed");       // Notify the user about the successful booking
        }

    }

    private static void findFirstAvailableCheckRows(boolean available){
        // Check if there are available seats in each row starting from the front

        if(available){
            boolean availableOne= findFirstAvailable(rowA,"Row A");     //call the findFirstAvailable method to find the row ,seats are available or not available.

            if(availableOne) {
                boolean availableTwo = findFirstAvailable(rowB, "Row B");

                if (availableTwo) {
                    boolean availableThree = findFirstAvailable(rowC, "Row C");

                    if (availableThree) {
                        boolean availableFour = findFirstAvailable(rowD, "Row D");

                        if (availableFour) {
                            System.out.println("There are no available seats");
                        }
                    }
                }
            }
        }
    }



    private static void printingPlan(){     //printing seating plan
        System.out.print("A  ");
        printRow(rowA);     //call printRow method to Display seating plan
        System.out.print("B  ");
        printRow(rowB);
        System.out.println(" ");
        System.out.print("C  ");
        printRow(rowC);
        System.out.print("D  ");
        printRow(rowD);

    }

    private static void printRow(int[] row){        //Display each row using for loop
        for(int i=0;i<row.length;i++){
            if(row[i]==0){
                System.out.print("O ");
            }
            else{
                System.out.print("X ");
            }
        }
        System.out.println();
    }


    private static int ticketPrice(int seatNum){        // Check if the seat number falls within the range of seats with different prices
        if(0<seatNum && seatNum<6){
            return 200;     // Return the price for seats 1 to 5
        }
        else if (6<seatNum && seatNum<10) {
            return 150;
        }
        else{
            return 180;
        }
    }


    private static boolean findFirstAvailable(int[]row, String rowLetter) {
        //row(The array representing the seats in the row).
        //rowLetter(The letter representing the row).

        for (int x = 1; x < row.length; x++) {      // Iterate through each seat in the row
            if (row[x-1] == 0) {
                System.out.println("The first available seat is in " + rowLetter + " " + x + " seat.");
                return false;       // If an available seat is found, print its information and return false
            }
        }
        return true;        //If no available seats are found in the row, return true
    }



    private static int PrintTicketTotalForLoop(int[] rowPrintTicket,String[][] rowPrintTicketTwoD,int totalPrintTicket){
        //rowPrintTicket (An array representing the reservation status of seats in a row.)
        //rowPrintTicketTwoD (A 2D array containing ticket information for each reserved seat.)

        //Calculates the total ticket price and prints ticket information for each reserved seat.
        for(int i=0;i<rowPrintTicket.length;i++){
            if(rowPrintTicket[i]==1){
                System.out.println(rowPrintTicketTwoD[i][0] +" "+rowPrintTicketTwoD[i][1]+ "  " +rowPrintTicketTwoD[i][3] +rowPrintTicketTwoD[i][4] + " £" + rowPrintTicketTwoD[i][5]);
                totalPrintTicket=totalPrintTicket+Integer.parseInt(rowPrintTicketTwoD[i][5]);       //The total ticket price after adding the price of each reserved seat.
            }
        }
        return totalPrintTicket;
    }

    private static void printTicketTotal(int[] getRowA, int[] getRowB, int[] getRowC, int[] getRowD, String[][] storeA, String[][] storeB, String[][] storeC, String[][] storeD, int total){
        // Calculate total ticket price and print ticket information for each row

        int totalFinal;
        int totalFinaltwo;
        totalFinal=PrintTicketTotalForLoop(getRowA,storeA,total);
        totalFinaltwo=totalFinal;
        totalFinal=PrintTicketTotalForLoop(getRowB,storeB,total);
        totalFinaltwo=totalFinaltwo+totalFinal;
        totalFinal=PrintTicketTotalForLoop(getRowC,storeC,total);
        totalFinaltwo=totalFinaltwo+totalFinal;
        totalFinal=PrintTicketTotalForLoop(getRowD,storeD,total);
        totalFinaltwo=totalFinaltwo+totalFinal;

        System.out.println("Total :£ "+totalFinaltwo);
        main(new String[0]);
    }







private static void searchTicket(){     //Allows the user to search for a ticket by entering the row and seat number.
    try {
        while(true) {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter Row :");
            String searchRow = input.next();
            System.out.print("Enter seat number :");
            int searchSeat = input.nextInt();
            if (searchRow.equals("A") && rowA[searchSeat - 1] == 1 ) {
                searchTicketConditions(searchSeat, storeA);     //call searchTicketConditions method to display ticket information.
                break;

            } else if (searchRow.equals("B") && rowB[searchSeat - 1] == 1) {
                searchTicketConditions(searchSeat, storeB);
                break;

            } else if (searchRow.equals("C") && rowC[searchSeat - 1] == 1) {
                searchTicketConditions(searchSeat, storeC);
                break;

            } else if (searchRow.equals("D") && rowD[searchSeat - 1] == 1) {
                searchTicketConditions(searchSeat, storeD);
                break;

            } else if (searchRow.equals("A")||searchRow.equals("B")||searchRow.equals("C")||searchRow.equals("D")){

                //If the seat is not booked, inform the user that the seat is available
                System.out.println(searchRow + searchSeat + " " + "seat is available");
                break;

            }else{
                System.out.println("Enter valid values!");
            }
        }
        main(new String[0]);        // Restart the main menu after completing the ticket search
    }
    catch (Exception e){
        System.out.println("Enter valid values!");
        searchTicket();
    }


}
    private static void searchTicketConditions(int searchSeatNumber,String [][] store){     //Display ticket information
        System.out.println("Ticket Owner : "+store[searchSeatNumber-1][0]+" "+store[searchSeatNumber-1][1]);
        System.out.println("Ticket Row & Seat Number : " + store[searchSeatNumber - 1][3] + store[searchSeatNumber - 1][4]);
    }

}