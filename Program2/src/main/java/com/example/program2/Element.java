package com.example.program2;

import javafx.beans.property.*;

import java.time.LocalDate;


/**
 * version 1.2
 */
public class Element {
    private final ObjectProperty<LocalDate> Date;
    private final IntegerProperty ID;
    private final StringProperty Name;
    private final StringProperty Family;
    private final StringProperty Patronymic;
    private final StringProperty delete;

    public Element(LocalDate date, String name, String family, String patronymic)
    {
        this.ID= new SimpleIntegerProperty(-1);
        this.Date= new SimpleObjectProperty<>(date);
        this.Name = new SimpleStringProperty(name);
        this.Family = new SimpleStringProperty(family);
        this.Patronymic = new SimpleStringProperty(patronymic);
        this.delete = new SimpleStringProperty("Удалить");
    }

    public Element(int id, LocalDate date, String name, String family, String patronymic)
    {
        this.Date= new SimpleObjectProperty<>(date);
        this.ID=new SimpleIntegerProperty(id);
        this.Name = new SimpleStringProperty(name);
        this.Family = new SimpleStringProperty(family);
        this.Patronymic = new SimpleStringProperty(patronymic);
        this.delete = new SimpleStringProperty("Удалить");
    }

    public int getId()
    {
        return ID.get();
    }
    public LocalDate getDate()
    {
        return (LocalDate) Date.get();
    }

    public String getName() {
        return Name.get();
    }

    public void setID(int id)
    {
        this.ID.set(id);
    }
    public StringProperty NameProperty()
    {
        return Name;
    }
    public StringProperty FamilyProperty()
    {
        return Family;
    }
    public StringProperty PatronymicProperty()
    {
        return Patronymic;
    }
    public String getFamily() {
        return Family.get();
    }

    public String getPatronymic() {
        return Patronymic.get();
    }

    public ObjectProperty<LocalDate> DateProperty()
    {
        return Date;
    }
    public StringProperty DeleteProperty()
    {
        return delete;
    }
    public IntegerProperty IDProperty()
    {
        return ID;
    }

}
