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

    public void run() {
        try {
            Connection con = DriverManager.getConnection(url,db_user,db_password);
            Log.v("DB","遠端連接成功");
        }catch(SQLException e) {
            Log.e("DB","遠端連接失敗");
            Log.e("DB", e.toString());
        }
    }

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

    public ArrayList<Dcard> getArticle() throws SQLException {
        ArrayList<Dcard> dcardList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT dcard_rawdata.Id, dcard_rawdata.Title, dcard_rawdata.CreatedAt, dcard_rawdata.Content, nlp_analysis.SA_Score, nlp_analysis.SA_Class, comparison.Level, comparison.KeywordLevel1, comparison.KeywordLevel2, comparison.KeywordLevel3 FROM dcard_rawdata JOIN nlp_analysis ON dcard_rawdata.Id = nlp_analysis.Id JOIN comparison ON comparison.Id = nlp_analysis.Id WHERE dcard_rawdata.Id = nlp_analysis.Id ORDER BY  dcard_rawdata.Id DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Dcard dcard = new Dcard();
                dcard.setSascore(rs.getString("SA_Score"));
                dcard.setSaclass(rs.getString("SA_Class"));
                dcard.setTitle(rs.getString("Title"));
                dcard.setDate(rs.getString("CreatedAt"));
                dcard.setContent(rs.getString("Content"));
                dcard.setId(rs.getString("Id"));
                dcard.setLv1(rs.getString("KeywordLevel1"));
                dcard.setLv2(rs.getString("KeywordLevel2"));
                dcard.setLv3(rs.getString("KeywordLevel3"));
                dcardList.add(dcard);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dcardList;
    }
}
