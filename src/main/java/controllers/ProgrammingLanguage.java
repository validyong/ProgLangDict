package main.java.controllers;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProgrammingLanguage extends RecursiveTreeObject<ProgrammingLanguage> {
    SimpleIntegerProperty prgId;
    StringProperty prgName;
    SimpleIntegerProperty dsgId;
    SimpleIntegerProperty firstAppeared;
    StringProperty dsgName;

    public ProgrammingLanguage(int prgId, String prgName, int dsgId, int firstAppeared, String dsgName) {
        this.prgId = new SimpleIntegerProperty(prgId);
        this.prgName = new SimpleStringProperty(prgName);
        this.dsgId = new SimpleIntegerProperty(dsgId);
        this.firstAppeared = new SimpleIntegerProperty(firstAppeared);
        this.dsgName = new SimpleStringProperty(dsgName);
    }

    public SimpleIntegerProperty getPrgId() {
        return prgId;
    }

    public StringProperty getPrgName() {
        return prgName;
    }

    public SimpleIntegerProperty getDsgId() {
        return dsgId;
    }

    public SimpleIntegerProperty getFirstAppeared() {
        return firstAppeared;
    }

    public StringProperty getDsgName() {
        return dsgName;
    }

    public void setPrgName(String prgName) {
        this.prgName = new SimpleStringProperty(prgName);
    }

    public void setDsgId(int dsgId) {
        this.dsgId = new SimpleIntegerProperty(dsgId);
    }

    public void setFirstAppeared(int firstAppeared) {
        this.firstAppeared = new SimpleIntegerProperty(firstAppeared);
    }

    public void setDsgName(String dsgName) {
        this.dsgName = new SimpleStringProperty(dsgName);
    }
}
