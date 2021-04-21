package net.steampunkfoundry.techdemo.client.printclient.controller;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.steampunkfoundry.techdemo.client.printclient.dto.Pdf;
import net.steampunkfoundry.techdemo.client.printclient.login.LoginManager;
import net.steampunkfoundry.techdemo.client.printclient.login.service.CaseClient;
import net.steampunkfoundry.techdemo.client.printclient.print.PrintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaseListController implements Initializable {

    private final PrintManager printManager;
    @Autowired
    CaseClient caseClient;
    @FXML
    private Button printButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TableView<Pdf> caseTable;

    /**
     * Constructor
     *
     * @param printManager printManager
     */
    public CaseListController(PrintManager printManager) {

        this.printManager = printManager;
    }


    /**
     * initialize the controller with the loginManager
     *
     * @param loginManager loginManager
     */
    public void initLoginManager(final LoginManager loginManager) {
        printButton.setOnAction(actionEvent -> printSelectedPdf());
        refreshButton.setOnAction(actionEvent -> refreshCaseList());
    }

    /**
     * refresh the case list.
     */
    private void refreshCaseList() {
        List<Pdf> pdfs = caseClient.getPdfs();
        Logger.getLogger(CaseListController.class.getName())
                .log(Level.INFO, "received " + pdfs.size() + " pdfs");
        caseTable.getItems().clear();
        caseTable.getItems().addAll(pdfs);
        caseTable.getSelectionModel().select(0);
    }

    /**
     * print the selected PDF
     */
    private void printSelectedPdf() {
        try {
            String printerName = printManager
                    .print(caseTable.getSelectionModel().getSelectedItem().getPdfContent());
            ButtonType loginButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Print");
            dialog.getDialogPane().getButtonTypes().add(loginButtonType);
            dialog.getDialogPane()
                    .setContentText("Printed " + caseTable.getSelectionModel().getSelectedItem()
                            .getPdfDescription() + " to default printer: " + printerName);
            dialog.showAndWait();
        } catch (PrinterException e) {
            Logger.getLogger(CaseListController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(CaseListController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Pdf> pdfs = caseClient.getPdfs();
        Logger.getLogger(CaseListController.class.getName())
                .log(Level.INFO, "received " + pdfs.size() + " pdfs");
        initializeTableView();
        caseTable.getItems().addAll(pdfs);
        caseTable.getSelectionModel().select(0);
        caseTable.requestFocus();
    }

    /**
     * initialize the TableView
     */
    private void initializeTableView() {
        TableColumn<Pdf, String> column1 = new TableColumn<>("Case Number");
        column1.setCellValueFactory(new PropertyValueFactory<>("caseNumber"));
        column1.prefWidthProperty().bind(caseTable.widthProperty().divide(4));

        TableColumn<Pdf, String> column2 = new TableColumn<>("A Number");
        column2.setCellValueFactory(new PropertyValueFactory<>("anumber"));
        column2.prefWidthProperty().bind(caseTable.widthProperty().divide(4));

        TableColumn<Pdf, String> column3 = new TableColumn<>("PDF Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("pdfDescription"));
        column3.prefWidthProperty().bind(caseTable.widthProperty().divide(2));

        caseTable.getColumns().add(column1);
        caseTable.getColumns().add(column2);
        caseTable.getColumns().add(column3);
    }
}
