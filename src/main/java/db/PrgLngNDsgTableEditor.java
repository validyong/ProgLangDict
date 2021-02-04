package main.java.db;

import main.java.controllers.ProgrammingLanguage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrgLngNDsgTableEditor {
    /**
     *
     * @param programmingLanguage
     *
     *
     * You need to update every time you change table data...
     *
     * this is a dangling comment
     * ????????????????
     * param <- ?????????????????????????
     * edit carefully
     * yum yum yum yummy
     */
    public static void addData(ProgrammingLanguage programmingLanguage) {
        // todo connect to DB and insert the data
        System.out.println("Attempting adding data________________");

        System.out.println("prog_id: " + programmingLanguage.getPrgId());
        System.out.println("name: " + programmingLanguage.getPrgName());
        System.out.println("dsg_id: " + programmingLanguage.getDsgId());
        System.out.println("first_appeared: " + programmingLanguage.getFirstAppeared());
        System.out.println("designer: " + programmingLanguage.getDsgName());


        String insertSQL =
                "INSERT INTO prog_lang VALUES("
                + programmingLanguage.getPrgId().get() + ", '"
                + programmingLanguage.getPrgName().get() + "', "
                + programmingLanguage.getDsgId().get() + ", "
                + programmingLanguage.getFirstAppeared().get() + ") ";

        System.out.println("insert SQL: " + insertSQL);

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            ps = cn.prepareStatement(insertSQL);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
        }

        System.out.println("Entered prog_lang data has been added!");

    }

    public static void addDesigner(int dsgId, String dsgName) {
        System.out.println("attempt inserting designer...");

        String insertDsgSQL = "INSERT INTO designer VALUES(" + dsgId + ", '" + dsgName + "')";

        System.out.println("insertDsg SQL: " + insertDsgSQL);

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            ps = cn.prepareStatement(insertDsgSQL);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
        }

        System.out.println("Entered designer data has been added!");
    }

    public static void deleteData(int id) {
        // super duper data pack
        // ray tracing
        System.out.println("The row which ID: " + id + " will delete...");

        String deleteSQL = "DELETE FROM prog_lang WHERE prog_id = ?";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            ps = cn.prepareStatement(deleteSQL);
            ps.setInt(1, id);

            // execution
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
        }

        System.out.println("Selected row has been deleted!!!");
    }

    public static void updateData(int id, String newValue, String columnName) {
        // todo Fix this method
        System.out.println("ID: " + id);
        System.out.println("newValue: " + newValue);
        System.out.println("columnName: " + columnName);

        String updateSQL = "";
        if (columnName.equals("name")) {
            System.out.println();
            System.out.println("OK, attempt to update the Name");
            updateSQL = "UPDATE prog_lang SET name = '" + newValue + "' WHERE prog_id = " + id;
        } else if (columnName.equals("dsg_id") || columnName.equals("first_appeared")) {
            updateSQL = "UPDATE prog_lang SET " + columnName + "  = " + newValue + " WHERE prog_id = " + id;
        }

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            System.out.println(updateSQL);

            ps = cn.prepareStatement(updateSQL);
            ps.executeUpdate();

            System.out.println("Successful updated. Maybe...");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(ps);
            DB.close(cn);
        }


        System.out.println("The Cell has been updated!!!!");
    }
}
