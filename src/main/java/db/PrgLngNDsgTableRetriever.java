package main.java.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.controllers.Designer;
import main.java.controllers.ProgrammingLanguage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrgLngNDsgTableRetriever {
    public static ObservableList<ProgrammingLanguage> retrieve() {
        // feels like iron consciousness...
        String SQL = "SELECT prog_lang.prog_id, prog_lang.name AS prg_name, prog_lang.dsg_id, prog_lang.first_appeared, designer.name AS dsg_name FROM prog_lang JOIN designer ON prog_lang.dsg_id = designer.dsg_id";

        ObservableList<ProgrammingLanguage> plList = FXCollections.observableArrayList();

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DB.getConnection();
            ps = cn.prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getString("prog_id") + "\t");
                System.out.print(rs.getString("prg_name") + "\t");
                System.out.print(rs.getString("dsg_id") + "\t");
                System.out.print(rs.getString("first_appeared") + "\t");
                System.out.println(rs.getString("dsg_name"));

                plList.add(new ProgrammingLanguage(
                        Integer.parseInt(rs.getString("prog_id")),
                        rs.getString("prg_name"),
                        Integer.parseInt(rs.getString("dsg_id")),
                        Integer.parseInt(rs.getString("first_appeared")),
                        rs.getString("dsg_name")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
            DB.close(rs);
        }

        return plList;
    }

    public static ObservableList<Designer> designersRetrieve(){
        ObservableList<Designer> designers = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM designer";

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = DB.getConnection();
            ps = cn.prepareStatement(SQL);
            rs = ps.executeQuery();

            System.out.println("Designer table");

            while (rs.next()) {
                System.out.print("dsg_id: " + rs.getString("dsg_id"));
                System.out.println("name: " + rs.getString("name"));

                designers.add(new Designer(
                        Integer.parseInt(rs.getString("dsg_id")),
                        rs.getString("name")
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            DB.close(cn);
            DB.close(ps);
            DB.close(rs);
        }

        return designers;
    }
}
