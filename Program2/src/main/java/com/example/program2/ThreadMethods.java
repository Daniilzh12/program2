package com.example.program2;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import static java.util.Collections.swap;

public class ThreadMethods implements Runnable{
    int Type;
    TableView<Element> table;
    ObservableList<Element> elements;

    int key;
    public ThreadMethods(TableView<Element> tableView, ObservableList<Element> elements1, int type){
        elements = elements1;
        table = tableView;
        Type = type;
    }
    public ThreadMethods(TableView<Element> tableView, ObservableList<Element> elements1, int type, int key){
        elements = elements1;
        table = tableView;
        this.key = key;
        Type = type;
    }
    @Override
    public void run() {
        boolean stop=true;
        while(stop) {
            try {
               stop = this.startWork();
               elements = null;
               table = null;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    public synchronized boolean startWork() {
        boolean stop;
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {}
        switch (Type) {
            case 1 -> stop = BubbleSortDate();
            case 2 -> stop = InsertSortID();
            case 3 -> stop = ShellSortNames();
            case 4 -> stop = linearSearch();
            default -> stop = false;
        }

        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        return stop;
    }
    public boolean BubbleSortDate() {
        boolean change;
        Element c,d;
        do {
            change = false;
            for (int i = 0; i < elements.size() - 1; i++) {
                c = elements.get(i);
                d = elements.get(i + 1);
                if (c.getDate().isAfter(d.getDate())) {
                    try {
                        elements.set(i, d);
                        elements.set(i + 1, c);
                    } catch (NullPointerException exception){
                        exception.printStackTrace();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception ignored) {}
                    table.refresh();
                    change = true;
                }
            }
        } while (change);
        return false;
    }
    public boolean InsertSortID()
    {
        for (int left = 0; left < elements.size(); left++) {
            Element value = elements.get(left);
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value.getId() < elements.get(i).getId()) {
                    elements.set(i + 1,elements.get(i));
                } else {
                    break;
                }
            }
            elements.set(i + 1,value);
            try {
                Thread.sleep(10);
            } catch (Exception ignored) {}
            table.refresh();
        }
        return false;
    }
    public boolean ShellSortNames()
    {
        int gap = elements.size() / 2;
        while (gap >= 1) {
            for (int right = 0; right < elements.size(); right++) {
                for (int c = right - gap; c >= 0; c -= gap) {
                    if (elements.get(c).getName().compareTo(elements.get(c + gap).getName())>0) {
                            swap(elements, c, c + gap);
                        try {
                            Thread.sleep(10);
                        } catch (Exception ignored) {}
                        table.refresh();
                    }
                }
            }
            gap = gap / 2;
        }
        return false;
    }
    public synchronized boolean linearSearch() {
        for (int index = 0; index < elements.size(); index++) {
            try {
                Thread.sleep(10);
            } catch (Exception ignored) {}
            table.getSelectionModel().select(index);
            if (elements.get(index).getId() == key) {
                return false;
            }
        }
        return false;
    }
}
