package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ExportController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn workerColumn;
    @FXML
    private TableColumn avgColumn;

    private double sum = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void initData(HashMap<String, Double> avgs) {
        for (Double d : avgs.values()) {
            sum += d;
        }
    }
}
