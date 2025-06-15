
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
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
                        viewReservations(connection);
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
            System.out.print("Enter guest name: ");
            String guestName= scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber=scanner.nextInt();
            System.out.print("Enter Contact number: ");
            String contactNumber=scanner.next();
            scanner.nextLine();

            String sql=
                    "INSERT INTO reservations (guest_name, room_number, contact_number)"+"VALUES('"+guestName+"',"+roomNumber+",'"+contactNumber+"')";
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

    public static void viewReservations(Connection connection) throws SQLException{
        //String sql="SELECT * FROM reservations";
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";
        try(Statement statement=connection.createStatement()){
            ResultSet resultSet=statement.executeQuery(sql);
            System.out.println("Total Reservations");
            System.out.println("+-------------------+---------------+---------------+-----------------+------------+");
            System.out.println("|   Reservation ID  |   Guest Name  |  Room Number  |  Contact Number |    Date    |");
            System.out.println("+-------------------+---------------+---------------+-----------------+------------+");
            while (resultSet.next()){
                int reservationid=resultSet.getInt("reservation_id");
                String guestname=resultSet.getString("guest_name");
                int roomnumber=resultSet.getInt("room_number");
                String contactnumber=resultSet.getString("contact_number");
                String reservationtime=resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s |\n",
                        reservationid, guestname, roomnumber, contactnumber, reservationtime);

            }
            System.out.println("------------------------------------------------------------------------------------");
        }
    }

}


