package controller;

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
import model.dsl.IEval;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.net.URL;
import java.util.ResourceBundle;

import model.dsl.Expr.ExprLexer;
import model.dsl.Expr.ExprParser;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import util.Directions;

/**
 *
 * Created by Eleazar Díaz Delgado on 9/01/17.
 */
public class SituationActionController implements Initializable {

    @FXML
    TableView<SituationRule> tableSituation;

    @FXML
    TableColumn expressionColumn;

    @FXML
    TableColumn actionColumn;

    @FXML
    TableColumn delColumn;

    public ObservableList<SituationRule> tableContent = FXCollections.observableArrayList();

    public class SituationRule {
        private final SimpleStringProperty expression;
        private final SimpleStringProperty action;

        public SituationRule(String expression, String action) {
            this.expression = new SimpleStringProperty(expression);
            this.action = new SimpleStringProperty(action);
        }

        public String getExpression() {
            return expression.get();
        }

        public SimpleStringProperty expressionProperty() {
            return expression;
        }

        public String getAction() {
            return action.get();
        }

        public SimpleStringProperty actionProperty() {
            return action;
        }

        public void setExpression(String expression) {
            this.expression.set(expression);
        }

        public void setAction(String action) {
            this.action.set(action);
        }

        /**
         * Get a IEval item from a rule string
         * @return
         */
        public IEval getIEval() {
            ANTLRInputStream in = new ANTLRInputStream(expression.get());
            ExprLexer exprLexer = new ExprLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(exprLexer);
            ExprParser parser = new ExprParser(tokens);
            return parser.expresion().e;
        }

        public Directions getDirection() {
            return Directions.valueOf(action.get());
        }
    }


    private class ChoiceCell extends TableCell<SituationRule, String> {
        final ChoiceBox<Directions> cellChoice = new ChoiceBox<>(FXCollections.observableArrayList(Directions.values()));

        ChoiceCell() {
            cellChoice.getSelectionModel().selectFirst();
            cellChoice.setOnAction(actionEvent -> {
                tableContent.get(getIndex()).setAction(cellChoice.getSelectionModel().getSelectedItem().toString());
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty && t != null){
                cellChoice.getSelectionModel().select(Directions.valueOf(t));
                setGraphic(cellChoice);
            }
            else{
                setGraphic(null);
            }
        }
    }


    private class DeleteCell extends TableCell<SituationRule, Boolean> {
        final Button cellButton = new Button("");

        DeleteCell(){
            cellButton.setGraphic(new FontIcon(FontAwesome.TIMES));
            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    SituationRule currentPerson = SituationActionController.DeleteCell.this.getTableView().getItems().get(SituationActionController.DeleteCell.this.getIndex());
                    //remove selected item from the table list
                    tableContent.remove(currentPerson);
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


    private class TextCell extends TableCell<SituationRule, String> {
        final TextField cellText = new TextField("");

        TextCell(){
            cellText.textProperty().addListener((observable, oldValue, newValue) -> {
                tableContent.get(getIndex()).setExpression(newValue);
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty && t != null){
                cellText.setText(t);
                setGraphic(cellText);
            }
            else{
                setGraphic(null);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        expressionColumn.setCellFactory(
                new Callback<TableColumn<SituationRule, String>, TableCell<SituationRule, String>>() {

                    @Override
                    public TableCell<SituationRule, String> call(TableColumn<SituationRule, String> p) {
                        return new TextCell();
                    }

                });
        expressionColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SituationRule, String>,
                        ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<SituationRule, String> p) {
                        return new SimpleStringProperty(p.getValue().getExpression());
                    }
                });

        actionColumn.setCellFactory(
                new Callback<TableColumn<SituationRule, String>, TableCell<SituationRule, String>>() {

                    @Override
                    public TableCell<SituationRule, String> call(TableColumn<SituationRule, String> p) {
                        return new ChoiceCell();
                    }

                });
        actionColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SituationRule, String>,
                        ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<SituationRule, String> p) {
                        return new SimpleStringProperty(p.getValue().getAction());
                    }
                });


        delColumn.setCellFactory(
                new Callback<TableColumn<SituationRule, Boolean>, TableCell<SituationRule, Boolean>>() {

                    @Override
                    public TableCell<SituationRule, Boolean> call(TableColumn<SituationRule, Boolean> p) {
                        return new DeleteCell();
                    }

                });
        delColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<SituationRule, Boolean>,
                                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SituationRule, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        tableSituation.setItems(tableContent);
    }

    /**
     * Add a default rule to start
     */
    public void addNewRule() {
        tableContent.add(
                new SituationRule(
                        "free left"
                        , "DOWN"
                        ));
    }

    /**
     * If rule is correct true else false
     * @param rule
     * @return
     */
    public boolean checkRule(String rule) {
        try {
            ANTLRInputStream in = new ANTLRInputStream(rule);
            ExprLexer exprLexer = new ExprLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(exprLexer);
            ExprParser parser = new ExprParser(tokens);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


}
