package main.java.db;

import javafx.beans.property.StringProperty;
import main.java.controllers.Designer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DsgTableEditor {
    public static void addDesigner(Designer designer) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            String SQL = "INSERT INTO designer VALUES(?, ?)";

            cn = DB.getConnection();

            ps = cn.prepareStatement(SQL);

            ps.setInt(1, designer.getId().get());
            ps.setString(2, designer.getName().get());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
        }
    }

    public static void deleteDesigner(int dsgId) {
        String deleteSQL = "DELETE FROM designer WHERE dsg_id = ?";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            ps = cn.prepareStatement(deleteSQL);
            ps.setInt(1, dsgId);

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

    public static void updateDesigner(int dsgId, String name) {
        System.out.println("dsgId: " + dsgId);
        System.out.println("name: " + name);

        String SQL = "UPDATE designer SET name = ? WHERE dsg_id = ?";

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = DB.getConnection();

            System.out.println(SQL);

            ps = cn.prepareStatement(SQL);
            ps.setString(1, name);
            ps.setInt(2, dsgId);
            ps.executeUpdate();

            System.out.println("Successful updated. Maybe...");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(ps);
            DB.close(cn);
        }
    }
}
