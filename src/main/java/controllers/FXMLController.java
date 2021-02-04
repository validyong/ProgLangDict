package main.java.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import main.java.db.DsgTableEditor;
import main.java.db.PrgLngNDsgTableEditor;
import main.java.db.PrgLngNDsgTableRetriever;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

public class FXMLController {

    public void initialize() {
        System.out.println("initialize() method is executed");
        init();// add data
        idField.setOnAction(this::puripuriIDText); // todo ISSUE why this doesn't execute if enter hasn't hit???
        prgNameEditableColumn.setOnEditCommit(this::updatePrgName);
        dsgIdEditableColumn.setOnEditCommit(this::updateDsgId);
        firstAppearedEditableColumn.setOnEditCommit(this::updateFirstAppeared);
        dsgNameColumn.setOnEditCommit(this::updateDesignerName);
    }

    private ObservableList<Designer> dsgList = PrgLngNDsgTableRetriever.designersRetrieve();
    private ObservableList<ProgrammingLanguage> plList = PrgLngNDsgTableRetriever.retrieve();

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    // todo Delete read only table view below!
    // designer table view
    @FXML
    private JFXTreeTableView<Designer> designerTableview;
    @FXML
    private JFXTreeTableColumn<Designer, Integer> dsgIdColumn;
    @FXML
    private JFXTreeTableColumn<Designer, String> dsgNameColumn;

    @FXML
    private void updateDesignerName(TreeTableColumn.CellEditEvent<Designer, String> designerStringCellEditEvent) {
        if (designerStringCellEditEvent.getOldValue().equals(designerStringCellEditEvent.getNewValue())) {
            return;
        }
        System.out.println("Attempt updating designer name... " + designerStringCellEditEvent.getNewValue());
        String oldName = designerStringCellEditEvent.getOldValue();
        System.out.println("old: " + oldName);
        System.out.println("old: " + designerStringCellEditEvent.getOldValue());
        dsgList.get(designerTableview.getSelectionModel().selectedIndexProperty().get()).setName(designerStringCellEditEvent.getNewValue());

        DsgTableEditor.updateDesigner(dsgIdColumn.getCellData(designerTableview.getSelectionModel().selectedIndexProperty().get()), designerStringCellEditEvent.getNewValue());

        for (int i = 0; i < plList.size(); i++) {
            System.out.println(plList.get(i).getDsgName().get());
            if (plList.get(i).getDsgName().get().equals(oldName)) {
                System.out.println("there is it");
                editableTreeTableView.getTreeItem(i).getValue().dsgName.set(designerStringCellEditEvent.getNewValue());
                plList.get(i).setDsgName(designerStringCellEditEvent.getNewValue());
            }
        }
    }

    @FXML
    private JFXTextField searchDesignerField;

    // editable table view ???
    @FXML
    private JFXTreeTableView<ProgrammingLanguage> editableTreeTableView;

    // todo cell update -- Detect editing a cell
    @FXML
    private void detectEditingTableCell(ActionEvent event) {
        System.out.println("detectEditingTableCell() has been called");

    }

    @FXML
    private JFXTreeTableColumn<ProgrammingLanguage, Integer> prgIdEditableColumn;
    @FXML
    private JFXTreeTableColumn<ProgrammingLanguage, String> prgNameEditableColumn;

    @FXML
    private void updatePrgName(TreeTableColumn.CellEditEvent<ProgrammingLanguage, String> t) { //when the text field updated
        if (t.getOldValue().equals(t.getNewValue())) {
            return;
        }
        //todo Adjust this
        int index = editableTreeTableView.getSelectionModel().selectedIndexProperty().get();

        System.out.println(prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()));
        System.out.println("Row index: " + index);
        System.out.println(prgNameEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()));
        System.out.println(prgNameEditableColumn.getText());
        System.out.println(t.getNewValue());
        System.out.println(t.getNewValue().toString());

        plList.get(index).setPrgName(t.getNewValue());

        for (int i = 0; i < plList.size(); i++) {
            System.out.print("[plList] Name: " + i + plList.get(i).getPrgName().get());
            System.out.print(" dsgID: " + plList.get(i).getDsgId().get());
            System.out.println(" first_appeared: " + plList.get(i).getFirstAppeared().get());
        }

//        plList.set(index - 1,
//                new ProgrammingLanguage(
//                        plList.get(index).getPrgId().get(),
//                        t.getNewValue(),
//                        plList.get(index).getDsgId().get(),
//                        plList.get(index).getFirstAppeared().get(),
//                        plList.get(index).getDsgName().get())
//                );

        // SQL
        PrgLngNDsgTableEditor.updateData(
                prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()),
                t.getNewValue(),
                "name");
    }

    @FXML
    private JFXTreeTableColumn<ProgrammingLanguage, Integer> dsgIdEditableColumn;

    @FXML
    private void updateDsgId(TreeTableColumn.CellEditEvent<ProgrammingLanguage, Integer> event) {
        // todo updateDsgId
        if (event.getNewValue().equals(event.getOldValue())) {
            return;
        }
        System.out.println("new: " + event.getNewValue());

        // check whether the input string is used for designer table or not
        for (int i = 0; i < dsgList.size(); i++) {
            if (dsgList.get(i).getId().get() == event.getNewValue()) {
                PrgLngNDsgTableEditor.updateData(
                        prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()),
                        event.getNewValue().toString(),
                        "dsg_id"
                );

                plList.get(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()).setDsgId(event.getNewValue());

                // これ何故か分からんが不要 入れるとバグる
//                plList.get(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()).setDsgName(dsgList.get(i).getName().get());

                event.getTreeTableView()
                        .getTreeItem(event.getTreeTablePosition()
                                .getRow())
                        .getValue().dsgId.set(event.getNewValue());

                System.out.println("dsgList.get(i).getName().get(): " + dsgList.get(i).getName().get());
                System.out.println(event.getTreeTablePosition().getRow());
                System.out.println(event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().dsgName);
                System.out.println(event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().getDsgName());

                event.getTreeTableView()
                        .getTreeItem(event.getTreeTablePosition()
                                .getRow())
                        .getValue().dsgName.set(dsgList.get(i).getName().getValue());

                System.out.println("designer_id has changed!");

                return;
            }
        }
        System.out.println("old: " + event.getOldValue());

        System.out.println("this designer_id doesn't exit");
    }

    @FXML
    private JFXTreeTableColumn<ProgrammingLanguage, Integer> firstAppearedEditableColumn;

    @FXML
    private void updateFirstAppeared(TreeTableColumn.CellEditEvent<ProgrammingLanguage, Integer> t) {
        if (t.getOldValue().equals(t.getNewValue())) {
            return;
        }
        System.out.println(t.getNewValue());
        plList.get(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()).setFirstAppeared(t.getNewValue());

        PrgLngNDsgTableEditor.updateData(prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()),
                        t.getNewValue().toString(),
                        "first_appeared");
        System.out.println("first_appeared has changed!");
    }

    @FXML
    private JFXTreeTableColumn<ProgrammingLanguage, String> dsgNameEditableColumn;

    // Delete Designer Button
    // Don't delete if the designer used for ProgrammingLanguage table
    @FXML
    private JFXButton deleteDesignerButton;

    @FXML
    private boolean deleteDesigner() {
        System.out.println("Attempt deleting a designer...");

        // check whether the id is used for plList or not
        for (int i = 0; i < plList.size(); i++) {
            if (dsgIdColumn.getCellData(designerTableview.getSelectionModel().selectedIndexProperty().get()) == plList.get(i).getDsgId().get()) {
                System.out.println("This Designer is used for Programming Languages table.");
                return false;
            }

        }
        System.out.println("Selected row index: " + designerTableview.getSelectionModel().selectedIndexProperty().get());
        if (dsgIdColumn.getCellData(designerTableview.getSelectionModel().selectedIndexProperty().get()) != null) {
            System.out.println("Selected row ID: " + dsgIdColumn.getCellData(designerTableview.getSelectionModel().selectedIndexProperty().get()));
            DsgTableEditor.deleteDesigner(dsgIdColumn.getCellData(designerTableview.getSelectionModel().selectedIndexProperty().get()));
        }

        System.out.println("dsgList Remove: " + designerTableview.getSelectionModel().selectedIndexProperty().get());
        dsgList.remove(designerTableview.getSelectionModel().selectedItemProperty().get().getValue());
        final IntegerProperty currCountProp = designerTableview.currentItemsCountProperty();
        currCountProp.set(currCountProp.get() - 1); // number of rows?
        System.out.println("Maybe rest rows(たぶん残りの行数): " + currCountProp);

        return true;
    }

    @FXML
    private JFXButton addDesignerButton;


    private Designer dsgForAdding;

    @FXML
    private boolean getDesignerFromTextFields(ActionEvent event) {
        System.out.println("Attempt adding a designer...");
        System.out.println("dsg_id: " + dsgIdFieldForDesigner.getText());
        System.out.println("dsg_name: " + dsgNameFieldForDesigner.getText());

        if (dsgIdFieldForDesigner.getText().matches("-?(0|[1-9]\\d*)")) {
            for (Designer designer : dsgList) {
                if (designer.getId().get() == Integer.parseInt(dsgIdFieldForDesigner.getText())) {
                    System.out.println("This dsg_id is already taken.");
                    return false;
                }
            }

            if (!dsgNameFieldForDesigner.getText().isEmpty()) {
                dsgForAdding = new Designer(Integer.parseInt(dsgIdFieldForDesigner.getText()), dsgNameFieldForDesigner.getText());
                dsgList.add(dsgForAdding);
                DsgTableEditor.addDesigner(dsgForAdding);
                return true;
            }
            System.out.println("dsg_name is weird");
        }

        System.out.println("dsg_id is weird");

        return false;
    }

    @FXML
    private JFXTextField dsgIdFieldForDesigner;
    @FXML
    private JFXTextField dsgNameFieldForDesigner;
    @FXML
    private Label designerTableviewCount;

    // Delete Button
    @FXML
    private JFXButton deleteRowButton;

    @FXML
    private void deleteRow() {
        // 2020/10/22 13:32
        // it ends in 5 hours
        System.out.println("Enemy has been eliminated");
        System.out.println("Selected row index: " + editableTreeTableView.getSelectionModel().selectedIndexProperty().get());
        if (prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()) != null) {
            System.out.println("Selected row ID: " + prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()));
            PrgLngNDsgTableEditor.deleteData(prgIdEditableColumn.getCellData(editableTreeTableView.getSelectionModel().selectedIndexProperty().get()));
        }
    }

    // add button event
    @FXML
    private JFXButton addButton;

    private ProgrammingLanguage plForAdding;

    @FXML
    private boolean getStringFromTextFields(ActionEvent event) {
        // todo Call PrgLngNDsgTableEditor.addData()
        IntegerProperty currCountProp = editableTreeTableView.currentItemsCountProperty();
        System.out.println("number of row" + currCountProp.get());

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("!!! Add Button has pressed!!!");
        System.out.println("ID: " + idField.getText());

        if (!idField.getText().matches("-?(0|[1-9]\\d*)")) {
            System.out.println("Don't input weird id");
            return false;
        }

        for (int i = 0; i < currCountProp.get(); i++) {
            // check whether idField.getText() exist
            if (Integer.parseInt(idField.getText()) == prgIdEditableColumn.getCellData(i)) {
                System.out.println("This ID is already taken.");
                return false;
            }
        }
        System.out.println("Name: " + nameField.getText());
        if (nameField.getText().isEmpty()) {
            System.out.println("nameField is empty... that's over_____");
            return false;
        }
        System.out.println("Designer ID: " + dsgIdField.getText());
        // check whether dsgIdField.getText() is number or not
        if (!dsgIdField.getText().matches("-?(0|[1-9]\\d*)")) {
            System.out.println("You damn ass input Not number in Designer ID TextField");
            return false;
        }

        System.out.println("First Appeared: " + firstAppearedField.getText());
        if (!firstAppearedField.getText().matches("-?(0|[1-9]\\d*)")) {
            System.out.println("THE year you input is shit");
            return false;
        }
        System.out.println("Designer Name: " + dsgNameField.getText());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        for (int i = 0; i < dsgList.size(); i++) {

            // check whether dsgIdField.getText() is equal to dsg_id or not
            if (Integer.parseInt(dsgIdField.getText()) == dsgList.get(i).getId().get()) {
                System.out.println("Same dsg_id is founded!");
                // get dsg_name's dsg_id
                System.out.println(dsgNameEditableColumn.getCellData(i));
                plForAdding = new ProgrammingLanguage(
                        Integer.parseInt(idField.getText()),
                        nameField.getText(),
                        Integer.parseInt(dsgIdField.getText()),
                        Integer.parseInt(firstAppearedField.getText()),
                        dsgList.get(i).getName().get()
                );
                PrgLngNDsgTableEditor.addData(plForAdding);

                return true;
            }

            System.out.println(dsgIdEditableColumn.getCellData(i));
        }
        // create new Designer
        // check whether dsgNameField.getText() is Null or not
        if (!dsgNameField.getText().isEmpty()) {
            plForAdding = new ProgrammingLanguage(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    Integer.parseInt(dsgIdField.getText()),
                    Integer.parseInt(firstAppearedField.getText()),
                    dsgNameField.getText()
            );

            dsgList.add(new Designer(Integer.parseInt(dsgIdField.getText()), dsgNameField.getText()));

            PrgLngNDsgTableEditor.addDesigner(plForAdding.getDsgId().get(), plForAdding.getDsgName().get());
            PrgLngNDsgTableEditor.addData(plForAdding);
            return true;
        }
        System.out.println("The ID you entered does not exist and Designer is null..");
        return false;
    }

    //// JFXTextField //////////////////////////////////////////////////
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField dsgIdField;
    @FXML
    private JFXTextField firstAppearedField;
    @FXML
    private JFXTextField dsgNameField;

    @FXML
    private void puripuriIDText(ActionEvent event) {
        //
        System.out.println("The String in ID text field has changed!!!");
        System.out.println(idField.getText());
    }

    /////////////////////////////////////////////////////////////////////
    @FXML
    private Label editableTreeTableViewCount;
    @FXML
    private JFXTextField searchField2;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        setupDesignerTableView();
        setupEditableTableView();
    }

    private <T> void setupDesignerCellValueFactory(JFXTreeTableColumn<Designer, T> column, Function<Designer, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Designer, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupDesignerTableView() {
        setupDesignerCellValueFactory(dsgIdColumn, p -> p.id.asObject());
        setupDesignerCellValueFactory(dsgNameColumn, Designer::getName);

        dsgIdColumn.setCellFactory((TreeTableColumn<Designer, Integer> param) -> new GenericEditableTreeTableCell<>(
                new IntegerTextFieldEditorBuilder()
        ));
        dsgIdColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Designer, Integer> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow()).getValue().id.set(t.getNewValue()));

        dsgNameColumn.setCellFactory((TreeTableColumn<Designer, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()
        ));
        dsgNameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Designer, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().name.set(t.getNewValue()));

        designerTableview.setRoot(new RecursiveTreeItem<>(dsgList, RecursiveTreeObject::getChildren));
        designerTableview.setShowRoot(false);
        designerTableview.setEditable(true);
        designerTableviewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + designerTableview.getCurrentItemsCount() + POSTFIX,
                        designerTableview.currentItemsCountProperty()));
        searchDesignerField.textProperty()
                .addListener(setupSearchDesignerField(designerTableview));

        deleteDesignerButton.disableProperty().bind(Bindings.equal(-1, designerTableview.getSelectionModel().selectedIndexProperty()));
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ProgrammingLanguage, T> column, Function<ProgrammingLanguage, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProgrammingLanguage, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }


    private void setupEditableTableView() {
        setupCellValueFactory(prgIdEditableColumn, p -> p.prgId.asObject());
        setupCellValueFactory(prgNameEditableColumn, ProgrammingLanguage::getPrgName);
        setupCellValueFactory(dsgIdEditableColumn, p -> p.dsgId.asObject());
        setupCellValueFactory(firstAppearedEditableColumn, p -> p.firstAppeared.asObject());
        setupCellValueFactory(dsgNameEditableColumn, ProgrammingLanguage::getDsgName);

        // add editors
        prgIdEditableColumn.setCellFactory((TreeTableColumn<ProgrammingLanguage, Integer> param) -> new GenericEditableTreeTableCell<>(
                new IntegerTextFieldEditorBuilder()));
        prgIdEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, Integer> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().prgId.set(t.getNewValue()));

        prgNameEditableColumn.setCellFactory((TreeTableColumn<ProgrammingLanguage, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        prgNameEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().prgName.set(t.getNewValue()));

        // todo if prgName is edited...
//        prgNameEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, String> t) -> System.out.println("ma???" + t.getNewValue()));

        // dsg_id
        dsgIdEditableColumn.setCellFactory((TreeTableColumn<ProgrammingLanguage, Integer> param) -> new GenericEditableTreeTableCell<>(
                new IntegerTextFieldEditorBuilder()));
//        dsgIdEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, Integer> t) -> t.getTreeTableView()
//                .getTreeItem(t.getTreeTablePosition()
//                        .getRow())
//                .getValue().dsgId.set(t.getNewValue()));

        firstAppearedEditableColumn.setCellFactory((TreeTableColumn<ProgrammingLanguage, Integer> param) -> new GenericEditableTreeTableCell<>(
                new IntegerTextFieldEditorBuilder()));
        firstAppearedEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, Integer> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().firstAppeared.set(t.getNewValue()));

        dsgNameEditableColumn.setCellFactory((TreeTableColumn<ProgrammingLanguage, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        dsgNameEditableColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<ProgrammingLanguage, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().dsgName.set(t.getNewValue()));

        // plListを常に監視している...?
        editableTreeTableView.setRoot(new RecursiveTreeItem<>(plList, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField2.textProperty()
                .addListener(setupSearchField(editableTreeTableView));

        // 2020/10/22 added... delete button disable property
        deleteRowButton.disableProperty().bind(Bindings.equal(-1, editableTreeTableView.getSelectionModel().selectedIndexProperty()));


        // plListがこのメソッドで定義されてるのでdelete Button が押されたときrowを消す処理もここに書くことにします
        // todo fixed...
        deleteRowButton.setOnMouseClicked((event -> {

            System.out.println("plList Remove: " + editableTreeTableView.getSelectionModel().selectedIndexProperty().get());
            plList.remove(editableTreeTableView.getSelectionModel().selectedItemProperty().get().getValue());
            final IntegerProperty currCountProp = editableTreeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() - 1); // number of rows?
            System.out.println("Maybe rest rows(たぶん残りの行数): " + currCountProp);
        }));


        // addButton clicked
        addButton.setOnMouseClicked((event -> {
            System.out.println("the event addButton.setOnMouseClicked()");
            if (!(plForAdding == null)) {
                System.out.println("New Programming Language is coming...");
                plList.add(plForAdding);
                plForAdding = null;

                final IntegerProperty currCountProp = editableTreeTableView.currentItemsCountProperty();
                currCountProp.set(currCountProp.get() + 1); // number of rows?
            } else {
                System.out.println("You could not add new data");
            }

        }));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<ProgrammingLanguage> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(prgLngProp -> {
                    ProgrammingLanguage programmingLanguage = prgLngProp.getValue();
                    return Integer.toString(programmingLanguage.prgId.get()).contains(newVal)
                            || Integer.toString(programmingLanguage.prgId.get()).contains(newVal)
                            || programmingLanguage.prgName.get().contains(newVal)
                            || Integer.toString(programmingLanguage.dsgId.get()).contains(newVal)
                            || Integer.toString(programmingLanguage.firstAppeared.get()).contains(newVal)
                            || programmingLanguage.dsgName.get().contains(newVal);
                });
    }

    private ChangeListener<String> setupSearchDesignerField(final JFXTreeTableView<Designer> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(dsgProp -> {
                    Designer designer = dsgProp.getValue();
                    return Integer.toString(designer.id.get()).contains(newVal)
                            || Integer.toString(designer.id.get()).contains(newVal)
                            || designer.name.get().contains(newVal);
                });
    }
}
