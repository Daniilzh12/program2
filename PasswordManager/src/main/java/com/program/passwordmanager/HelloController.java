package com.program.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public TableView<Element> table;

    public TextField Login;
    public TextField Password;
    public TextField Site;
    public Button excel;
    public Button filter;
    ElementsDAOImpl elementsDAO;

    private void createTable() throws SQLException {
        TableColumn<Element, String> ColDelete = new TableColumn<>("Удаление");
        ColDelete.setStyle("-fx-text-fill: red");
        ColDelete.setCellValueFactory(new PropertyValueFactory<>("Удаление"));
        TableColumn<Element, Integer> ColId = new TableColumn<>("ID");
        TableColumn<Element, String> ColLogin = new TableColumn<>("Логин");
        TableColumn<Element, String> ColPassword = new TableColumn<>("Пароль");
        TableColumn<Element, String> ColSite = new TableColumn<>("Сайт");

        ColId.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        ColLogin.setCellValueFactory(cellData -> cellData.getValue().LoginProperty());
        ColPassword.setCellValueFactory(cellData -> cellData.getValue().PasswordProperty());
        ColSite.setCellValueFactory(cellData -> cellData.getValue().SiteProperty());
        ColDelete.setCellValueFactory(cellData -> cellData.getValue().DeleteProperty());
        ColLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        ColPassword.setCellFactory(TextFieldTableCell.forTableColumn());
        ColSite.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty())
                if (ColDelete == table.getSelectionModel().getSelectedCells().get(0).getTableColumn())
                    elementsDAO.deleteElement(table.getItems().get(table.getSelectionModel().getSelectedCells().get(0).getRow()).getId());
        });
        ColLogin.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Login = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Login, Elem.getPassword(), Elem.getSite());
            elementsDAO.updateElement(newElement);

        });
        ColPassword.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Password = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Elem.getLogin(), Password, Elem.getSite());
            elementsDAO.updateElement(newElement);

        });
        ColSite.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Site = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Elem.getLogin(), Elem.getPassword(), Site);
            elementsDAO.updateElement(newElement);
        });
        table.getColumns().clear();
        table.getColumns().addAll(ColId, ColLogin, ColPassword, ColSite, ColDelete);
        table.setItems(elementsDAO.getAllElements());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementsDAO = new ElementsDAOImpl();
        try {
            createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if (!Login.getText().isEmpty() && !Password.getText().isEmpty() && !Site.getText().isEmpty())
            elementsDAO.addElement(Login.getText(), Password.getText(), Site.getText());
        else alert.show();
    }


    public void Filter() {
        elementsDAO.filterElements(Login.getText(), Password.getText(), Site.getText());
    }
}