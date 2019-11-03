/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Helpers;

import aps.PCB;
import data_structures.IComparable;
import data_structures.IEquatable;
import data_structures.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author cmlima
 */
public class UIHelpers {
    
    public static void showErrorMessage(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();    
    } 

    public static <T extends IComparable & IEquatable> void setTableData(List<T> list, TableView<T> table) {
        ObservableList data = FXCollections.observableArrayList();
        for (int i = 0; i < list.getSize(); i++) {
            data.add(list.get(i));
        }
        table.setItems(data);
    }
    
    public static <T extends IComparable & IEquatable> List<T> getTableData(TableView<T> table) {
        ObservableList<T> oList = table.getItems();
        List<T> list = new List<>();
        for (int i = 0; i < oList.size(); i++) {
            list.add(oList.get(i));
        }
        return list;
    }
 
}
