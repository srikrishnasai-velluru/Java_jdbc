import java.sql.*;
import java.util.Scanner;

public class Jdbccrud {

    public static void main(String[] args) throws SQLException {
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC", "root", "Your Mysql password");

        if (con != null) {
            System.out.println("Connection Established");
        } else {
            System.out.println("Connection not Established");
            return;
        }

        Scanner sc = new Scanner(System.in);
        PreparedStatement ps;
        ResultSet rs;

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Insert a User");
            System.out.println("2. View All Users");
            System.out.println("3. View User by Username");
            System.out.println("4. Update Password");
            System.out.println("5. Delete User");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            if (choice == 1) {
                System.out.println("Enter Username:");
                String username = sc.nextLine();
                System.out.println("Enter Password:");
                String password = sc.nextLine();
                System.out.println("Enter Name:");
                String name = sc.nextLine();
                System.out.println("Enter Email:");
                String email = sc.nextLine();

                String insert = "INSERT INTO users(username,password,name,email) VALUES(?,?,?,?)";
                ps = con.prepareStatement(insert);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, name);
                ps.setString(4, email);
                int res = ps.executeUpdate();
                if (res > 0) System.out.println("User inserted successfully!");

            } else if (choice == 2) {
                System.out.println("All Users:");
                Statement st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM users");
                while (rs.next()) {
                    System.out.println(rs.getString("username") + " " +
                            rs.getString("password") + " " +
                            rs.getString("name") + " " +
                            rs.getString("email"));
                }

            } else if (choice == 3) {
                System.out.println("Enter username to search:");
                String uname = sc.nextLine();
                String select1 = "SELECT * FROM users WHERE username=?";
                ps = con.prepareStatement(select1);
                ps.setString(1, uname);
                rs = ps.executeQuery();
                if (!rs.isBeforeFirst()) {
                    System.out.println("No user found.");
                } else {
                    while (rs.next()) {
                        System.out.println(rs.getString("username") + " " +
                                rs.getString("password") + " " +
                                rs.getString("name") + " " +
                                rs.getString("email"));
                    }
                }

            } else if (choice == 4) {
                System.out.println("Enter the username to update password:");
                String userToUpdate = sc.nextLine();
                System.out.println("Enter new password:");
                String newPassword = sc.nextLine();
                String update = "UPDATE users SET password=? WHERE username=?";
                ps = con.prepareStatement(update);
                ps.setString(1, newPassword);
                ps.setString(2, userToUpdate);
                int updateRes = ps.executeUpdate();
                if (updateRes > 0)
                    System.out.println("Password updated successfully!");
                else
                    System.out.println("User not found.");

            } else if (choice == 5) {
                System.out.println("Enter username to delete:");
                String userToDelete = sc.nextLine();
                String delete = "DELETE FROM users WHERE username=?";
                ps = con.prepareStatement(delete);
                ps.setString(1, userToDelete);
                int delRes = ps.executeUpdate();
                if (delRes > 0)
                    System.out.println("User deleted successfully!");
                else
                    System.out.println("User not found.");

            } else if (choice == 6) {
                System.out.println("Thanks for using...!");
                con.close();
                sc.close();
                System.exit(0);

            } else {
                System.out.println("Invalid choice. Please select from 1 to 6.");
            }
        }
    }
}
