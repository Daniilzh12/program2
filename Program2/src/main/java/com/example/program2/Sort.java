package com.example.program2;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import static java.util.Collections.swap;

public  class Sort {
    public static void BubbleSortDate(ObservableList<Element> elements) {
        boolean change;
        Element c,d;
        do {
            change = false;
            for (int i = 0; i < elements.size() - 1; i++) {
                c = elements.get(i);
                d = elements.get(i + 1);
                if (c.getDate().isAfter(d.getDate())) {
                    elements.set(i, d);
                    elements.set(i + 1, c);
                    try {
                        Thread.sleep(50);
                    } catch (Exception ignored) {}
                    change = true;
                }
            }
        } while (change);
    }
    public static void InsertSortID(ObservableList<Element> elements) {
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
                Thread.sleep(50);
            } catch (Exception ignored) {}
        }
    }
    public static void ShellSortNames( ObservableList<Element> elements) {
        int gap = elements.size() / 2;
        while (gap >= 1) {
            for (int right = 0; right < elements.size(); right++) {
                for (int c = right - gap; c >= 0; c -= gap) {
                    if (elements.get(c).getName().compareTo(elements.get(c + gap).getName())>0) {
                        swap(elements, c, c + gap);
                        try {
                            Thread.sleep(50);
                        } catch (Exception ignored) {}
                    }
                }
            }
            gap = gap / 2;
        }
    }
    public static void linearSearch(TableView<Element> table, ObservableList<Element> elements, int key) {
        for (int index = 0; index < elements.size(); index++) {
            try {
                Thread.sleep(50);
            } catch (Exception ignored) {}
            table.getSelectionModel().select(index);
            if (elements.get(index).getId() == key) {
                return;
            }
        }
    }
}
