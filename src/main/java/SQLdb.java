

import com.mysql.cj.jdbc.Driver;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;
import java.util.ArrayList;
public class SQLdb {
    private final static String url = "jdbc:mysql://localhost:3306/webstudentsgroups";
    private final static String user = "root";
    public static void updateDB(String directory) {
        File dir = new File(directory);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, "root");
            Statement statement = connection.createStatement();
            System.out.println("Connection succesfull...");
            for(File item : dir.listFiles()) {
                String file_dir = item.getName();
                if (!file_dir.contains(".csv")){
                    continue;
                }
                String fullWayFile = directory + "/" + file_dir;
                BufferedReader reader = new BufferedReader(new FileReader(fullWayFile));
                String line = null;
                Scanner scanner = null;
                int index = 0;
                String lastName = "";
                String firstName = "";
                String secondName = "";
                String birthday = "";
                String groupName = file_dir.replace(".csv", "");
                System.out.println(groupName+ fullWayFile);
                String query = "SELECT * FROM `group` WHERE group_name = '"+groupName+"';";
                ResultSet rs = statement.executeQuery(query);
                if(!rs.next()){
                    statement.executeUpdate("INSERT INTO `group`(group_name) VALUES ('" + groupName + "');");
                }

                while ((line = reader.readLine()) != null) {
                    scanner = new Scanner(line);
                    scanner.useDelimiter(",");
                    while (scanner.hasNext()) {
                        String data = scanner.next();
                        if (index == 0)
                            lastName = data;  //фамилия
                        else if (index == 1)
                            firstName = data; //имя
                        else if (index == 2)
                            secondName = data; //отчество
                        else if (index == 3)
                            birthday = data; //дата рождения
                        index++;
                    }
                    System.out.println(lastName+ firstName+secondName+birthday+groupName);
                    statement.executeUpdate("INSERT INTO `student`(group_id, first_name, second_name, last_name, birthday_date) VALUES ('" + groupName + "', '" + firstName + "', '" + secondName + "', '" +
                            lastName + "', '" + birthday + "');");
                    index = 0;
                }
            }
            connection.close();
            statement.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed...");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e);
        }
    }
}
