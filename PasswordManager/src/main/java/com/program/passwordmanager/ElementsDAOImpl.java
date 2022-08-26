package com.program.passwordmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ElementsDAOImpl implements ElementsDAO{
    ObservableList<Element> elements;

    public ElementsDAOImpl()
    {
        elements = FXCollections.observableArrayList();
        DBConnect.getDBConnection();
        DBConnect.getNotes(elements);
    }

    @Override
    public ObservableList<Element> getAllElements() {
        return elements;
    }

    @Override
    public Element getElement(int ElementId) {

        return elements.get(ElementId);
    }
    public void addElement(String Login,String Password,String Site)
    {
        int id;
        Element temp=new Element(Login,Password,Site);
        id=DBConnect.addNotes(temp);
        if(id!=-1){
            temp=new Element(id,Login,Password,Site);
            elements.add(temp);
        }
    }

    @Override
    public void updateElement(Element NewElement) {
        if(DBConnect.editNotes(NewElement))
            for(Element el: elements)
                if(el.getId()==NewElement.getId())
                    elements.set(elements.indexOf(el),NewElement);
    }

    @Override
    public void deleteElement(int ID) {
        if(DBConnect.deleteNotes(ID))
            elements.removeIf(el -> el.getId() == ID);
    }

    public void filterElements(String Login,String Password,String Site)
    {
        DBConnect.getFilterNotes(elements,Login,Password,Site);
    }

}