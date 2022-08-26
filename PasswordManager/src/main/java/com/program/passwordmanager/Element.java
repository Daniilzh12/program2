package com.program.passwordmanager;

import javafx.beans.property.*;

import java.time.LocalDate;


public class Element {
    private final IntegerProperty ID;
    private final StringProperty Login;
    private final StringProperty Password;
    private final StringProperty Site;
    private final StringProperty delete;

    public Element(String Login, String Password, String Site)
    {
        this.ID= new SimpleIntegerProperty(-1);
        this.Login= new SimpleStringProperty(Login);
        this.Password = new SimpleStringProperty(Password);
        this.Site = new SimpleStringProperty(Site);
        this.delete = new SimpleStringProperty("Удалить");
    }

    public Element(int id, String Login, String Password, String Site)
    {
        this.ID=new SimpleIntegerProperty(id);
        this.Login= new SimpleStringProperty(Login);
        this.Password = new SimpleStringProperty(Password);
        this.Site = new SimpleStringProperty(Site);
        this.delete = new SimpleStringProperty("Удалить");
    }

    public int getId()
    {
        return ID.get();
    }
    public String getLogin()
    {
        return Login.get();
    }
    public String getPassword()
    {
        return Password.get();
    }
    public String getSite()
    {
        return Site.get();
    }

    public StringProperty LoginProperty()
    {
        return Login;
    }
    public StringProperty PasswordProperty()
    {
        return Password;
    }
    public StringProperty SiteProperty()
    {
        return Site;
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
