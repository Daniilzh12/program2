package com.example.program2;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class HelloController implements Initializable {
    public TableView<Element> table;

    public DatePicker Date;
    public TextField family;
    public TextField patronymic;
    public TextField input;
    public TextField name;
    public TextField search;
    ElementsDAOImpl elementsDAO;

    private void createTable()  {
        TableColumn<Element, String> ColDelete = new TableColumn<>("Удаление");
        ColDelete.setStyle("-fx-text-fill: red");
        ColDelete.setCellValueFactory(new PropertyValueFactory<>("Удаление"));
        TableColumn<Element, LocalDate> ColDate = new TableColumn<>("Дата");
        TableColumn<Element, Integer> ColId = new TableColumn<>("ID");
        TableColumn<Element, String> ColName = new TableColumn<>("Имена");
        TableColumn<Element, String> ColFamily = new TableColumn<>("Фамилии");
        TableColumn<Element, String> ColPatronymic = new TableColumn<>("Отчества");

        ColId.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
        ColName.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
        ColFamily.setCellValueFactory(cellData -> cellData.getValue().FamilyProperty());
        ColPatronymic.setCellValueFactory(cellData -> cellData.getValue().PatronymicProperty());
        ColDelete.setCellValueFactory(cellData -> cellData.getValue().DeleteProperty());

        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColDate.setCellFactory(new Callback<TableColumn<Element, LocalDate>, TableCell<Element, LocalDate>>() {

            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public TableCell<Element, LocalDate> call(TableColumn<Element, LocalDate> param) {
                return new TableCell<Element, LocalDate>() {

                    private DatePicker datePicker;

                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        if (item == getItem())
                            return;

                        super.updateItem(item, empty);
                        if (item == null) {
                            super.setText(null);
                        } else {
                            super.setText(item.format(dtf));
                        }
                        super.setGraphic(null);
                    }

                    @Override
                    public void startEdit() {
                        if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                            return;
                        }

                        if (datePicker == null) {
                            datePicker = createPicker();
                        }
                        datePicker.setValue(getItem());

                        super.startEdit();
                        setText(null);
                        setGraphic(datePicker);
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();

                        setText(getItem().format(dtf));
                        setGraphic(null);
                    }

                    private DatePicker createPicker() {
                        DatePicker picker = new DatePicker();
                        picker.setOnAction(event -> {
                            this.commitEdit(picker.getValue().atStartOfDay().toLocalDate());
                            event.consume();
                        });
                        picker.setOnKeyReleased(event -> {
                            if (event.getCode() == KeyCode.ESCAPE) {
                                this.cancelEdit();
                                event.consume();
                            }
                        });
                        return picker;
                    }


                };
            }
        });

        ColName.setCellFactory(TextFieldTableCell.forTableColumn());
        ColFamily.setCellFactory(TextFieldTableCell.forTableColumn());
        ColPatronymic.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty())
                if (ColDelete == table.getSelectionModel().getSelectedCells().get(0).getTableColumn())
                    elementsDAO.deleteElement(table.getItems().get(table.getSelectionModel().getSelectedCells().get(0).getRow()).getId());
        });
        ColDate.setOnEditCommit((TableColumn.CellEditEvent<Element, LocalDate> event) -> {

            Element Elem = event.getRowValue();
            LocalDate date = event.getNewValue();
            Element newElement = new Element(Elem.getId(), LocalDate.parse(date.toString()), Elem.getName(), Elem.getFamily(), Elem.getPatronymic());
            //elementsDAO.deleteElement(event.getRowValue().getId());
            elementsDAO.updateElement(newElement);
        });
        ColName.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Name = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Elem.getDate(), Name, Elem.getFamily(),Elem.getPatronymic());
            elementsDAO.updateElement(newElement);

        });
        ColFamily.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Family = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Elem.getDate(), Elem.getName(), Family, Elem.getPatronymic());
            elementsDAO.updateElement(newElement);

        });
        ColPatronymic.setOnEditCommit((TableColumn.CellEditEvent<Element, String> event) -> {
            Element Elem = event.getRowValue();
            String Patronymic = event.getNewValue();
            Element newElement = new Element(Elem.getId(), Elem.getDate(), Elem.getName(), Elem.getFamily(), Patronymic);
            elementsDAO.updateElement(newElement);
        });
        table.getColumns().clear();
        table.getColumns().addAll(ColId, ColDate, ColName, ColFamily, ColPatronymic, ColDelete);
        table.setItems(elementsDAO.getAllElements());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementsDAO = new ElementsDAOImpl();
        createTable();
        String[] names = new String[]{"Иван","Степан","Константин","Роман","Алексей"};
        String[] families = new String[]{"Аксенов","Лоховой","Бурдакин","Крахмалов","Степанов"};
        String[] patronomics = new String[]{"Иванович","Степанович","Константинович","Валерьевич","Андреевич"};
        LocalDate[] dates = new LocalDate[]{LocalDate.of(2001,11,3),LocalDate.of(2003,3,3),LocalDate.of(1987,11,3),LocalDate.of(1980,1,23),LocalDate.of(1997,10,2)};
        Random random =new Random();
        int count = random.nextInt(18,20);
        for(int i=0;i<count;i++)
        {
            LocalDate randDate = dates[random.nextInt(5)];
            String randName = names[random.nextInt(5)];
            String randFamily = families[random.nextInt(5)];
            String randPatr = patronomics[random.nextInt(5)];
            elementsDAO.addElement(randDate, randName,randFamily,randPatr);
        }
    }

    public void addClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if (Date.getValue() != null && !name.getText().isEmpty() && !family.getText().isEmpty() && !patronymic.getText().isEmpty())
            elementsDAO.addElement(Date.getValue(), name.getText(), family.getText(), patronymic.getText());
        else alert.show();
    }
    public void search() throws ExecutionException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if(!search.getText().isEmpty()) {
            elementsDAO.search(table,Integer.parseInt(search.getText()));
        }
        else alert.show();
    }
    public void bubbleSort() throws InterruptedException, ExecutionException {
        elementsDAO.Sort(table,1);
    }
    public void insertSort() throws InterruptedException, ExecutionException {
        elementsDAO.Sort(table,2);
    }
    public void shellSort() throws InterruptedException, ExecutionException {
        elementsDAO.Sort(table,3);
    }
    public void insertInto()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if (Date.getValue() != null && !name.getText().isEmpty() && !family.getText().isEmpty() && !patronymic.getText().isEmpty())
            elementsDAO.insertElement(Integer.parseInt(input.getText()),Date.getValue(), name.getText(), family.getText(), patronymic.getText());
        else alert.show();
    }
    public void insertAfter()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if (Date.getValue() != null && !name.getText().isEmpty() && !family.getText().isEmpty() && !patronymic.getText().isEmpty())
            elementsDAO.insertElementAfter(Integer.parseInt(input.getText()),Date.getValue(), name.getText(), family.getText(), patronymic.getText());
        else alert.show();
    }
    public void insertBefore()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы не заполнили данные!");
        if (Date.getValue() != null && !name.getText().isEmpty() && !family.getText().isEmpty() && !patronymic.getText().isEmpty())
            elementsDAO.insertElementBefore(Integer.parseInt(input.getText()),Date.getValue(), name.getText(), family.getText(), patronymic.getText());
        else alert.show();
    }
}