package controller;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.object.TypeObject;
import util.Directions;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * Created by Eleazar Díaz Delgado on 9/01/17.
 */
public class PerceptionActionAgentController implements Initializable {

    @FXML
    TableView<PerceptionRule> tablePerception;

    @FXML
    ChoiceBox<String> onLeftChoice;

    @FXML
    ChoiceBox<String> onRightChoice;

    @FXML
    ChoiceBox<String> onUpChoice;

    @FXML
    ChoiceBox<String> onDownChoice;

    @FXML
    ChoiceBox<Directions> action;

    @FXML
    Button addRule;

    @FXML
    TableColumn delColumn;

    ObservableList<PerceptionRule> tableContent = FXCollections.observableArrayList();

    public class PerceptionRule {
        private final SimpleStringProperty left;
        private final SimpleStringProperty right;
        private final SimpleStringProperty up;
        private final SimpleStringProperty down;
        private final SimpleStringProperty action;

        private PerceptionRule(String left, String right, String up, String down, String action) {
            this.left = new SimpleStringProperty(left);
            this.right = new SimpleStringProperty(right);
            this.up = new SimpleStringProperty(up);
            this.down = new SimpleStringProperty(down);
            this.action = new SimpleStringProperty(action);
        }

        public String getLeft() {
            return left.get();
        }

        public SimpleStringProperty leftProperty() {
            return left;
        }

        public void setLeft(String left) {
            this.left.set(left);
        }

        public String getRight() {
            return right.get();
        }

        public SimpleStringProperty rightProperty() {
            return right;
        }

        public void setRight(String right) {
            this.right.set(right);
        }

        public String getUp() {
            return up.get();
        }

        public SimpleStringProperty upProperty() {
            return up;
        }

        public void setUp(String up) {
            this.up.set(up);
        }

        public String getDown() {
            return down.get();
        }

        public SimpleStringProperty downProperty() {
            return down;
        }

        public void setDown(String down) {
            this.down.set(down);
        }

        public String getAction() {
            return action.get();
        }

        public SimpleStringProperty actionProperty() {
            return action;
        }

        public void setAction(String action) {
            this.action.set(action);
        }
    }

    private class DeleteCell extends TableCell<PerceptionRule, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteCell(){
            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    if (getTableView().getItems().size() < getIndex()) {
                        // get Selected Item
                        PerceptionRule currentPerson = DeleteCell.this.getTableView().getItems().get(DeleteCell.this.getIndex());
                        //remove selected item from the table list
                        tableContent.remove(currentPerson);
                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
            else{
                setGraphic(null);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> objects = FXCollections.observableArrayList();
        for (TypeObject typeObject : TypeObject.values()) {
            objects.add(typeObject.toString());
        }
        objects.add("Any");

        onLeftChoice.setItems(objects);
        onLeftChoice.getSelectionModel().selectLast();
        onRightChoice.setItems(objects);
        onRightChoice.getSelectionModel().selectLast();
        onUpChoice.setItems(objects);
        onUpChoice.getSelectionModel().selectLast();
        onDownChoice.setItems(objects);
        onDownChoice.getSelectionModel().selectLast();
        action.setItems(FXCollections.observableArrayList(Directions.values()));
        action.getSelectionModel().selectFirst();

        delColumn.setCellFactory(
                new Callback<TableColumn<PerceptionRule, Boolean>, TableCell<PerceptionRule, Boolean>>() {

                    @Override
                    public TableCell<PerceptionRule, Boolean> call(TableColumn<PerceptionRule, Boolean> p) {
                        return new DeleteCell();
                    }

                });
        delColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PerceptionRule, Boolean>,
                                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PerceptionRule, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        tablePerception.setItems(tableContent);
    }

    public void addNewRule() {
        tableContent.add(
                new PerceptionRule(
                        onLeftChoice.getSelectionModel().getSelectedItem(),
                        onRightChoice.getSelectionModel().getSelectedItem(),
                        onUpChoice.getSelectionModel().getSelectedItem(),
                        onDownChoice.getSelectionModel().getSelectedItem(),
                        action.getSelectionModel().getSelectedItem().toString()
                        ));
    }

    public TableView getTablePerception() {
        return tablePerception;
    }

    public ChoiceBox getOnLeftChoice() {
        return onLeftChoice;
    }

    public ChoiceBox getOnRightChoice() {
        return onRightChoice;
    }

    public ChoiceBox getOnUpChoice() {
        return onUpChoice;
    }

    public ChoiceBox getOnDownChoice() {
        return onDownChoice;
    }

    public ChoiceBox getAction() {
        return action;
    }

    public Button getAddRule() {
        return addRule;
    }
}
