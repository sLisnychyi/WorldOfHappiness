package com.happinest.world.persistance;

import com.happinest.world.data.TwitterData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;


public class PersistTwitts {

    private Connection getConnection() {
        String user = "hack";
        String password = "verified_test@";
        String url = "jdbc:mysql://" + "192.168.137.1" + ":" + "3306" + "/tweets";
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Can't get connection to db");
            e.printStackTrace();
        }
        return c;
    }

    public void persistTweets(List<TwitterData> data) {
        if (data.size() > 0) {
            Connection connection = getConnection();
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(
                    "INSERT INTO data (date,language,geoposition,text,tid) VALUES (?, ?, ?, ?, ?)");

            } catch (SQLException e) {
                System.out.println("Can't create statement.");
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    int numberOfInsertedRows = 0;
                    String state = data.get(0).getGeoPosition();
                    for (TwitterData twitt : data) {
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(twitt.getDate());
                        String geoPosition = twitt.getGeoPosition();
                        String language = twitt.getLanguage();
                        String text = twitt.getText().replaceAll("'", "\"");

                        statement.setDate(1, new java.sql.Date(twitt.getDate().getTime()));
                        statement.setString(2, language);
                        statement.setString(3, geoPosition);
                        statement.setString(4, text);
                        statement.setLong(5, twitt.getTid());


                        //                    String insert = "INSERT INTO data (date,language,geoposition,text) VALUES (" + "'" +
                        //                            date + "','" +
                        //                            language + "','" +
                        //                            geoPosition + "','" +
                        //                            text + "')";
                        try {
                            statement.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Exception while working with db" + e.getMessage());
                        }
                        numberOfInsertedRows++;
                    }
                    System.out.println(String.format("INSERTED : %s rows. For = %s.", numberOfInsertedRows, state));

                }
                connection.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection.");
                e.printStackTrace();
            }
        }
    }

//
//    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("com.mysql.jdbc.Driver");
//        java.util.Date dt = new java.util.Date();
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentTime = sdf.format(dt);
//      /* START System INFO */
//        String ip = "192.168.4.1";
//        String port = "3306";
//        String user = "hack";
//        String password = "verified_test@";
//        String url = "jdbc:mysql://" + ip + ":" + port + "/tweets";
//      /* END Start INFO */
//        Connection c = null;
//        c = SqlOpen(user, password, url);
//        SqlInsert(c, 45, "45", "erte", "tester");
//        SqlClose(c);
//
//
//    }
//
//    public static int SqlInsert(Connection c, int id_tweet, String date, String geoposition, String text) {
//        String query = "INSERT INTO data (id_tweet,geoposition,text) VALUES(" + id_tweet + ",'" + geoposition + "','" + text + "')";
//        try {
//            Statement st = c.createStatement();
//            st.executeUpdate(query);
//
//        } catch (Exception e) {
//            return 0;
//        } finally {
//            return 1;
//        }
//    }
//
//    /* Close SQL Connection */
//    public static String SqlClose(Connection c) {
//        try {
//            if (c != null) {
//                c.close();
//            } //Закрываем коннект
//        } catch (SQLException e) {
//            return null;
//        } finally {
//            return "OK";
//        }
//    }
//
//    /* Open SQL Connection */
//    public static Connection SqlOpen(String user, String password, String url) {
//        Connection c = null;
//        try {
//            c = DriverManager.getConnection(url, user, password);
//
//        } catch (Exception e) {
//            return null;
//        }
//        return c;
//    }


}
