package com.charikleia.petrou.personalisedtravelguide;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, db;
// bind-address=192.168.100.152
    public Connection connectionclass() throws SQLException,ClassNotFoundException {
//        ip = "localhost";
        ip = "127.0.0.1";
        db= "pois";
        uname= "root";
        pass= "poiPOI098)(*";
        port= "3306";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Connection connection;
//        String ConnectionURL = null;
        String dbDriver = "com.mysql.jdbc.Driver";
        System.out.println("c1");
        /* try{

         */
            System.out.println("c2");
            Class.forName("com.mysql.jdbc.Driver");
//            ConnectionURL = "jdbc:mysql://" + ip + ":" + port + "databasename=" + db +";user"+ uname + pass +";";
//            connection = DriverManager.getConnection(ConnectionURL);
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.2.9:3306/pois" , "cp", "cp");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + uname + "&password=" + pass + "&useUnicode=true&characterEncoding=UTF-8");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("Select * From test");
//            System.out.println(resultSet);

        System.out.println("c0");


       // }
        /*catch (Exception e){
            System.out.println("c3");
            Log.e("Error", e.getMessage());
        }

         */

        System.out.println("Connection res: "+connection);
        return connection;
    }


}
