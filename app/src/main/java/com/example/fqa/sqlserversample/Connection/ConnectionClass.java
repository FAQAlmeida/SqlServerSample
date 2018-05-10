package com.example.fqa.sqlserversample.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    private static final String CLASS = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DBPATH = "jdbc:jtds:sqlserver://192.168.15.2:1433;";
    private static final String INSTANCE = "instanceName=FQASQLSERVER;";
    private static final String DBNAME = "databaseName=Android;";
    private static final String USER = "user=05118_1_C_1_2017;";
    private static final String PASSWORD = "password=Smile435BMI;";
    private static final String WINAUTH = "integratedSecurity=true;";
    private static final String PATH = DBPATH + INSTANCE + DBNAME + WINAUTH;
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(CLASS);
        return DriverManager.getConnection(PATH);
    }

    public static void main(String[] args) {
        try{
            System.out.println(new ConnectionClass().getConnection().toString());
            System.out.println("Feito");
        }catch (Exception ex){
            System.out.println(ex.getMessage() + ex.getCause() + ex.getClass());
        }
    }
}
