package com.program.passwordmanager;

import javafx.collections.ObservableList;

import java.sql.*;

public class DBConnect {
    public static final String DB_URL = "jdbc:h2:/db/StorageDB";
    public static final String DB_Driver = "org.h2.Driver";
    public static final String Table="Passwords";

    public static void getDBConnection() {
        try {
            Class.forName(DB_Driver);
            Connection connection = DriverManager.getConnection(DB_URL);
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, Table, null);
            //deleteTable();
            if (!rs.next()) {
                createTable();
            }
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL!");
        }
    }

    private static void deleteTable()
    {
        String deleteTableSQL = "DROP TABLE "+Table;
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                statement.execute(deleteTableSQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTable() {
        String createTableSQL = "CREATE TABLE "+ Table+ " ("
                + "ID INT NOT NULL auto_increment, "
                + "Login varchar(20) NOT NULL, "
                + "Password varchar(20) NOT NULL, "
                + "Site varchar(20) NULL, "
                + "PRIMARY KEY (ID) "
                + ")";
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                statement.execute(createTableSQL);
            }
        } catch (SQLException ignored) {
        }
    }

    public static void getNotes(ObservableList<Element> elementsDAO) {
        String selection = "select * from "+ Table;
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                ResultSet rs = statement.executeQuery(selection);
                while (rs.next()) {
                    Element element = new Element(rs.getInt("ID"), rs.getString("Login"), rs.getString("Password"),
                    rs.getString("Site"));
                    elementsDAO.add(element);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getFilterNotes(ObservableList<Element> elementsDAO, String login, String password, String site) {
        int count=0;
        String selection = "select * from "+ Table;
        if(!login.isEmpty()) count++;
        if(!password.isEmpty()) count++;
        if(!site.isEmpty()) count++;
        if(count!=0) selection+=" where ";
        if(!login.isEmpty()){
            selection+="Login='"+login+"' ";
            count--;
            if(count!=0)
                selection+="AND ";
        }
        if(!password.isEmpty()){
            selection+="Password='"+password+"' ";
            count--;
            if(count!=0)
                selection+="AND ";
        }
        if(!site.isEmpty()){
            selection+="Site='"+site+"' ";
        }
        selection+=";";
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                ResultSet rs = statement.executeQuery(selection);
                elementsDAO.clear();
                while (rs.next()) {
                    Element element = new Element(rs.getInt("ID"),rs.getString("Login"), rs.getString("Password"),
                            rs.getString("Site"));
                    elementsDAO.add(element);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int addNotes(Element element) {
        String insertTableSQL = "INSERT INTO "+ Table
                + " (Login,Password,Site) " + "VALUES "
                + "('"+element.getLogin()+"','"+element.getPassword()+"','"+element.getSite()+"')";
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                statement.executeUpdate(insertTableSQL);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try(Statement statement = dbConnection.createStatement()) {
                ResultSet rs=statement.executeQuery("SELECT TOP 1 ID FROM "+Table+" ORDER BY ID DESC");
                rs.next();
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static boolean editNotes(Element element) {
        String updateTableSQL = String.format("UPDATE "+ Table
                + " SET Login='%s',Password='%s',Site='%s' where ID=%s;",
                element.getLogin(),element.getPassword(),element.getSite(),element.getId());
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                statement.executeUpdate(updateTableSQL);
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean deleteNotes(int ID) {
        String deleteTableSQL = String.format("DELETE from "+ Table +" WHERE ID=%s;",ID);
        try (Connection dbConnection = DriverManager.getConnection(DB_URL)) {
            assert dbConnection != null;
            try (Statement statement = dbConnection.createStatement()) {
                statement.executeUpdate(deleteTableSQL);
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}