package application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class RootAnchorPane extends AnchorPane {
    private MenuBar menuBar;
    private Menu fileMenu, statisticsMenu, helpMenu;
    private MenuItem closeItem, exportItem, aboutItem;

    private WorkerOverview workerOverview;

    public RootAnchorPane() {
        initComponents();
        addComponents();
        disableComponents(true);
        addHandlers();
    }

    private void addHandlers() {

    }

    private void disableComponents(boolean disabled) {
        exportItem.setDisable(disabled);
        workerOverview.setVisible(!disabled);
    }

    private void addComponents() {
        fileMenu.getItems().add(closeItem);
        statisticsMenu.getItems().add(closeItem);
        helpMenu.getItems().add(exportItem);

        menuBar.getMenus().addAll(fileMenu, statisticsMenu, helpMenu);


    }

    private void initComponents() {
        menuBar = new MenuBar();

        fileMenu = new Menu("File");
        closeItem = new MenuItem("Close");

        statisticsMenu = new Menu("Statistics");
        exportItem = new MenuItem("Export");

        helpMenu = new Menu("Help");
        aboutItem = new MenuItem("About");
    }
}