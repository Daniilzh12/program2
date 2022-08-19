package com.example.program2;
import java.time.LocalDate;
import java.util.List;

public interface ElementsDAO {
    public List<Element> getAllElements();
    public Element getElement(int ElementId);
    public void updateElement(Element NewElement);
    public void deleteElement(int ID);
    public void addElement(LocalDate date, String name, String family, String patronymic);
}