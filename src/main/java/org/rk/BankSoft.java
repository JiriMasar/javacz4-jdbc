package org.rk;

import java.sql.*;

public class BankSoft {

//    private static String url = "jdbc:mysql://localhost:3306/testdb";
//    private static String user = "root";
//    private static String password = "Bea007Bea007";

    private static String url = "jdbc:h2:mem:testdb";
    private static String user = "sa";
    private static String password = "";

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false);
            createDb(connection);
            createAccount(connection, "karel", 100);
            createAccount(connection, "monika", 20);
            connection.commit();

            try {
                transferMoney(connection, "karel", "monika", 20);
                connection.commit();
            }catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
            printTableContent(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void transferMoney(Connection connection, String fromAccount, String toAccount, int ammount) throws Exception {
        int toBalance = getAccountBalance(connection, toAccount);
        updateAccount(connection, toAccount, ammount + toBalance);


        int fromBalance = getAccountBalance(connection, fromAccount);
        if(fromBalance<ammount){
            throw new Exception(fromAccount+" have no enought money");
        }
        updateAccount(connection, fromAccount, fromBalance - ammount);

    }

    private static void printTableContent(Connection connection) {
        String sql = "SELECT * FROM BankAccount";
        try (Statement st = connection.createStatement()) {
            var result = st.executeQuery(sql);
            while (result.next()) {
                int ammount = result.getInt("ammount");
                String accountName = result.getString("name");
                System.out.println(String.format("BankAccount with name :%s, have %s ", accountName, ammount));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static int getAccountBalance(Connection connection, String accountName) {
        String sql = "SELECT ammount FROM BankAccount WHERE name like '" + accountName + "'";
        try (Statement st = connection.createStatement()) {
            var result = st.executeQuery(sql);
            while (result.next()) {
                int ammount = result.getInt(1);
                return ammount;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private static void createAccount(Connection connection, String name, int ammount) throws SQLException {


        String updateString = "INSERT INTO BankAccount (name,ammount) VALUES (?,?)";
        try (PreparedStatement insertCat = connection.prepareStatement(updateString)) {
            insertCat.setString(1, name);
            insertCat.setInt(2, ammount);
            insertCat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    private static void updateAccount(Connection connection, String name, int ammount) throws SQLException {

        // String updateString = "INSERT INTO ANIMAL (name) VALUES ('" + animalName + "')";
        String updateString = "UPDATE  BankAccount set ammount= ? WHERE name like ?";
        try (PreparedStatement updateAccount = connection.prepareStatement(updateString)) {
            updateAccount.setInt(1, ammount);
            updateAccount.setString(2, name);

            int affectedRows = updateAccount.executeUpdate();
            System.out.println(String.format("%s animals updated", affectedRows));
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    private static void createDb(Connection connection) {

        try (Statement stmt = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS BankAccount(" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "ammount INTEGER, " +
                    "PRIMARY  KEY (id)" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
