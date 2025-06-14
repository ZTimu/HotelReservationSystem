
import jdk.jshell.spi.ExecutionControl;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class hotelReservationSystem {
    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password ="21335756";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");//loading jdbc driver class for MySQL
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println();
                System.out.println("Hotel Reservation System");
                System.out.println(".........................");
                Scanner scanner=new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.println("Choose an Option: ");
                int choice=scanner.nextInt();
                switch(choice){
                    case 1:
                        //reserveRoom();
                        break;
                    case 2:
                        //viewReservations();
                        break;
                    case 3:
                        //getRoomNumber();
                        break;
                    case 4:
                        //updateReservation();
                        break;
                    case 5:
                        //deleteReservation();
                        break;
                    case 0:
                        //exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
