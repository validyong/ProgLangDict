package main.java.app;

import main.java.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTester {
    public static void main(String[] args) {
        String columnName = "name";
        String SQL = "UPDATE prog_lang SET " + columnName + " = ? WHERE prog_id = 893";
        String testSQL = "UPDATE prog_lang SET name = 'child of DBTester' WHERE prog_id = 893";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            System.out.println("SQL:");
            System.out.println(SQL);

            System.out.println("testSQL:");
            System.out.println(testSQL);

            ps = cn.prepareStatement(testSQL);
//            ps.setString(1, "SUpeR");
            System.out.println("successfully set...");

            ps.executeUpdate();

            System.out.println("successfully updated...");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(ps);
            DB.close(cn);
        }

    }
}
