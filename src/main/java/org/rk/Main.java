package org.rk;

import java.io.UnsupportedEncodingException;
import java.sql.*;

public class Main {

//    private static String url = "jdbc:mysql://localhost:3306/testdb";
//    private static String user = "root";
//    private static String password = "Bea007Bea007";

    private static String url = "jdbc:h2:mem:testdb";
    private static String user = "sa";
    private static String password = "";

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            //connection.setAutoCommit(false);
            createDb(connection);
            insertSomeData(connection);
            //connection.commit();

            updateCat(connection);
            printTableContent(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTableContent(Connection connection) {
        String sql = "SELECT * FROM ANIMAL";
        try(Statement st = connection.createStatement()){
             var result = st.executeQuery(sql);
             while(result.next()){
                 int id = result.getInt(1);
                 String animalName = result.getString("name");
                 System.out.println(String.format("Animal id:%s, name:%s",id,animalName));
             }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static void insertSomeData(Connection connection) throws SQLException {

        String animalName= "cat";

       // String updateString = "INSERT INTO ANIMAL (name) VALUES ('" + animalName + "')";
        String updateString = "INSERT INTO ANIMAL (name) VALUES (?)";
        try (PreparedStatement insertCat = connection.prepareStatement(updateString)) {
           insertCat.setString(1,animalName);
           insertCat.execute();
        } catch (SQLException e) {
           e.printStackTrace();
           connection.rollback();
        }
    }

    private static void updateCat(Connection connection) throws SQLException {

        String animalName= "dog";

        // String updateString = "INSERT INTO ANIMAL (name) VALUES ('" + animalName + "')";
        String updateString = "UPDATE  ANIMAL set name= ? WHERE id=3";
        try (PreparedStatement insertCat = connection.prepareStatement(updateString)) {
            insertCat.setString(1,animalName);
            int affectedRows = insertCat.executeUpdate();
            System.out.println(String.format("%s animals updated",affectedRows));
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    private static void createDb(Connection connection) {

        try (Statement stmt = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS ANIMAL(" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "PRIMARY  KEY (id)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
