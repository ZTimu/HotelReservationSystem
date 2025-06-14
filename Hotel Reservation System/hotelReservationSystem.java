
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.Statement;

public class hotelReservationSystem {
    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password ="21335756";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//jdbc driver loaded
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        ///viewReservations(connection);
                        break;
                    case 3:
                        //getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        //updateReservation(connection, scanner);
                        break;
                    case 5:
                        //deleteReservation(connection, scanner);
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
    private static void reserveRoom(Connection connection, Scanner scanner){
        try{
            System.out.println("Enter guest name: ");
            String guestName= scanner.nextLine();
            System.out.println("Enter room number: ");
            int roomNumber=scanner.nextInt();
            String contactNumber=scanner.next();

            String sql= "INSERT INTO reservations (guestName, roomNumber, contactNumber)"+"VALUES('"+guestName+"',"+roomNumber+",'"+contactNumber+"')";
            try(Statement statement=connection.createStatement()){
                int affectedrows=statement.executeUpdate(sql);
                if(affectedrows>0){
                    System.out.println("Reservation Successfull.");
                }
                else{
                    System.out.println("Reservation Failed.");
                }

            }
            }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

