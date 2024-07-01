import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Ticket {       //Ticket class

    //Attributes
    private String row;     //Row of seat
    private int  seat;      //seat number
    private int price;      //seat price

    private Person person;      //person attribute

    //Constructor
    public Ticket(String row,int seat,int price,Person person){
        this.row=row;
        this.seat=seat;
        this.price=price;
        this.person=person;
    }




    public String getRow() {
        return row;
    }       //getter of Row

    public void setRow(String row) {
        this.row = row;
    }       //setter of Row

    public int getSeat() {return seat;}     //getter of seat number

    public void setSeat(int seat) {this.seat = seat;}       //setter of seat number

    public int getPrice() {
        return price;
    }       //getter of seat price

    public void setPrice(int price) {
        this.price = price;
    }       //setter pf setter price
    public void setPerson(Person person){
        this.person=person;
    }
    public Person getPerson(){
        return person;
    }       //return peron(getter)


    //Save ticket information to text file.
    public static void saveFile(String row,int seat,int price,Person person){
        try{
            File TicketFile=new File(row+seat+".txt");
            boolean TicketFileCreated=TicketFile.createNewFile();
            if(TicketFileCreated){
                FileWriter writer=new FileWriter(row+seat+".txt");
                writer.write("Ticket Information\n");
                writer.write("                   \n");
                writer.write("Name           :"+person.getName()+" "+person.getSurName()+"\n");
                writer.write("Email          :"+person.getEmail()+"\n");
                writer.write("Seat           :"+row+seat+"\n");
                writer.write("Ticket Price   :"+"£"+price+"\n");

                writer.close();
            }

        }


        catch (IOException e) {
            System.out.println("Error in fail saving");
        }

    }
    //delete saved text file.
    public static void deleteSavedFile(String row,int seat){
        File TicketFileDelete=new File(row+seat+".txt");
        TicketFileDelete.delete();
    }


}
