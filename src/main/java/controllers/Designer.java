package main.java.controllers;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Designer extends RecursiveTreeObject<Designer> {
    SimpleIntegerProperty id;
    StringProperty name;

    public Designer(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }
}
