package com.example.mpandroidchart_test;

import static com.mysql.jdbc.Messages.getString;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlCon {
    String mysql_ip = "120.126.19.127";
    int mysql_port = 13306;
    String db_name = "cgu";
    String url = "jdbc:mysql://"+mysql_ip+":"+mysql_port+"/"+db_name;
    String db_user = "public";
    String db_password = "SQL.110APP";

    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,db_user,db_password);
        } catch (Exception e) {
            Log.e("ERROR: ", e.getMessage());
        }
        return conn;
    }
}
