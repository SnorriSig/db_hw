package com.example.db_hw;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDB {
    String username = "root";
    //String[] args;
    String password = DbHwApplication.password; //"sesame5%.";
    String url = "jdbc:mysql://" + DbHwApplication.ip + ":3306/";
//    String url = "jdbc:mysql://1.2.3.4:3306/?useSSL=false";
    String schemaName = "mydb";
    String tableName = "persons";
    List<String > persons = new ArrayList();

    public MyDB(){
        connectAndQuery();
    }

    private void connectAndQuery(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(url, username,password)){
            if(!conn.isClosed()){
                System.out.println("DB Conn ok ");
                initializeDatabase(conn);
//                // Get the rows:
                String sql = "SELECT * FROM " + tableName;
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                persons.clear();
                while (resultSet.next()){
                    String firstName = resultSet.getString("name");
                    System.out.println("Name: " + firstName);
                    persons.add(firstName);
                }
            }
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }

    private void initializeDatabase(Connection conn) throws Exception{
        // 1. make sure there exists a schema, named mydb. If not, create one
        String sql = "CREATE DATABASE IF NOT EXISTS mydb;";
        PreparedStatement create = conn.prepareStatement(sql);
        create.executeUpdate();

        sql = "USE mydb;";
        create = conn.prepareStatement(sql);
        create.executeUpdate();

        // 2. make sure there exists a table, named persons. If not, create one:
        //    Primary key: idpersons INT AUTO_INCREMENT
        //    Column: name VARCHAR(45)
        sql = "CREATE TABLE IF NOT EXISTS persons \n" +
                "(\n" +
                "idpersons INT PRIMARY KEY \tAUTO_INCREMENT,\n" +
                "name VARCHAR(45) UNIQUE\n" +
                ");";
        create = conn.prepareStatement(sql);
        create.executeUpdate();

        // 3. If there was no table named persons, then insert two rows into the new table: "Anna" and "Bent"
        sql = "INSERT IGNORE INTO persons (idpersons, name) VALUES (1,'Anna');";
        create = conn.prepareStatement(sql);
        create.executeUpdate();

        sql = "INSERT IGNORE INTO persons (idpersons, name) VALUES (2,'Bent');";
        create = conn.prepareStatement(sql);
        create.executeUpdate();
    }

    public List<String> getPersons() {
        return persons;
    }

}
