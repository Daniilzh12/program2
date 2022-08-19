package com.example.program2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ElementsDAOImpl implements ElementsDAO{
    ObservableList<Element> elements;
    Thread thread;
    CompletableFuture<Void> future;
    ThreadMethods threadMethods;
    public ElementsDAOImpl()
    {
        elements = FXCollections.observableArrayList();
    }
    @Override
    public ObservableList<Element> getAllElements() {
        return elements;
    }

    int maxID=0;
    @Override
    public Element getElement(int ElementId) {

        for(Element el: elements)
            if(el.getId()==ElementId)
                return el;
        return null;
    }
    public void addElement(LocalDate date, String name, String family, String patronymic)
    {
        Element temp=new Element(maxID,date,name,family,patronymic);
        elements.add(temp);
        maxID++;
    }
    public void insertElement(int id,LocalDate date, String name, String family, String patronymic)
    {
        Element el = getElement(id);
        Element temp = new Element(id,date, name, family, patronymic);
        elements.set(elements.indexOf(el),temp);
    }
    public void insertElementBefore(int id,LocalDate date, String name, String family, String patronymic)
    {
        Element el = getElement(id);
        Element temp = new Element(maxID,date, name, family, patronymic);
        elements.add(elements.indexOf(el), temp);
        maxID++;
    }
    public void insertElementAfter(int id,LocalDate date, String name, String family, String patronymic)
    {
        Element el = getElement(id);
        Element temp = new Element(maxID,date, name, family, patronymic);
        elements.add(elements.indexOf(el)+1, temp);
        maxID++;
    }
public void Sort(TableView<Element> table,int type) throws InterruptedException, ExecutionException {
    /*threadMethods = new ThreadMethods(table, elements, type);
        Thread thread1 = new Thread(threadMethods);
        if(thread != null && thread.isAlive()) thread.join();
        thread = thread1;
        thread.start();*/
    if (future != null) future.get();
    switch (type) {
        case 1 -> future = CompletableFuture.runAsync(() -> {
            Sort.BubbleSortDate(elements);
        });
        case 2 -> future = CompletableFuture.runAsync(() -> {
            Sort.InsertSortID(elements);
        });
        case 3 -> future = CompletableFuture.runAsync(() -> {
            Sort.ShellSortNames(elements);
        });
    }
}

    public void search(TableView<Element> table,int key) throws ExecutionException, InterruptedException {
        if (future != null) future.get();
        future = CompletableFuture.runAsync(() -> {
            Sort.linearSearch(table,elements,key);
        });
    }
    @Override
    public void updateElement(Element NewElement) {
        for(Element el: elements)
            if(el.getId()==NewElement.getId())
                elements.set(elements.indexOf(el),NewElement);
    }

    @Override
    public void deleteElement(int ID) {
        elements.removeIf(el -> el.getId() == ID);
    }
}