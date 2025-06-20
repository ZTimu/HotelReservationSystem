
import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Scanner;


public class hotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "21335756";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//jdbc driver loaded
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
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
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter Contact number: ");
            String contactNumber = scanner.next();
            scanner.nextLine();

            String sql =
                    "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES(?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,guestName );
                preparedStatement.setInt(2, roomNumber);
                preparedStatement.setString(3, contactNumber);
                int affectedrows = preparedStatement.executeUpdate();
                if (affectedrows > 0) {
                    System.out.println("Reservation Successfull.");
                } else {
                    System.out.println("Reservation Failed.");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) throws SQLException {
        //String sql="SELECT * FROM reservations";
        String sql = "SELECT * FROM reservations";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            System.out.println("Total Reservations");
            System.out.println("+-------------------+---------------+---------------+-----------------+------------+");
            System.out.println("|   Reservation ID  |   Guest Name  |  Room Number  |  Contact Number |    Date    |");
            System.out.println("+-------------------+---------------+---------------+-----------------+------------+");
            while (resultSet.next()) {
                int reservationid = resultSet.getInt("reservation_id");
                String guestname = resultSet.getString("guest_name");
                int roomnumber = resultSet.getInt("room_number");
                String contactnumber = resultSet.getString("contact_number");
                String reservationtime = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s |\n",
                        reservationid, guestname, roomnumber, contactnumber, reservationtime);

            }
            System.out.println("------------------------------------------------------------------------------------");

        }
    }


    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int reservationid = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Name: ");
            String guestname = scanner.nextLine();

            String sql = "SELECT room_number FROM reservations WHERE reservation_id=? AND guest_name= ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,reservationid);
                preparedStatement.setString(2, guestname);
                 ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int roomnumber = resultSet.getInt("room_number");
                    System.out.println("Reservation ID: " + reservationid + " room number is: " + roomnumber);
                } else {
                    System.out.println("Reservation not found. Please book");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void updateReservation(Connection connection, Scanner scanner) {

        try {
            System.out.print("Enter Reservation ID to update: ");
            int updateID = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(connection, updateID)) {
                System.out.println("This id does not Exist. Please enter correct one");
                return;
            }
            System.out.print("Enter guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.print("Enter new Room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.print("Enter new Contact number: ");
            String newContactNumber = scanner.next();

            String sql =
                    "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,newGuestName);
                preparedStatement.setInt(2,newRoomNumber);
                preparedStatement.setString(3,newContactNumber);
                preparedStatement.setInt(4,updateID);
                int affectedrows = preparedStatement.executeUpdate();
                if (affectedrows > 0) {
                    System.out.println("Reservation Updated Successfully!");
                } else {
                    System.out.println("Updation failed! Please Try again");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter Reservation Id to erase: ");
            int deleteReservationId = scanner.nextInt();
            if (!reservationExists(connection, deleteReservationId)) {
                System.out.println("Reservation ID does not existed, please try again!");
                return;
            }
            String sql = "DELETE FROM reservations WHERE reservation_id=? ";
            try (PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,deleteReservationId);
                int affectedrows = preparedStatement.executeUpdate();
                if (affectedrows > 0) {
                    System.out.println("Deletion Successfull!");
                } else {
                    System.out.println("Deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationid) {

        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id=?";

            try (PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,reservationid );
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();// means data found, So reservation exists
            }
        } catch (SQLException e) {// handling databases erros
            e.printStackTrace();
            return false;
        }
    }


    public static void exit() throws InterruptedException {
        System.out.print("Existing System");
        int i = 3;
        while (i != 0) {
            System.out.print('.');
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("Thank you, Will see you soon.");
    }


}

