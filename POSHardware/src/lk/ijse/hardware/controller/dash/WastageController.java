package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.hardware.controller.LoginFormController;
import lk.ijse.hardware.controller.find.AddItemFormController;
import lk.ijse.hardware.dto.WastageDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.WastageService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.WastageServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Navigation;
import lk.ijse.hardware.util.Regex;
import lk.ijse.hardware.util.enm.Finder;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.view.tm.AddItemTM;
import lk.ijse.hardware.view.tm.ProductTM;
import lk.ijse.hardware.view.tm.WastageTM;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class WastageController {
    public JFXButton btnAddItem;
    public JFXTextField txtSearch;
    public Label lblItemCode;
    public Label lblDesc;
    public JFXTextField txtQty;
    public JFXTextArea txtReason;
    public TableView mainTable;
    public TableColumn colArthur;
    public TableColumn colItemCode;
    public TableColumn colReason;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colCostPrice;
    public TableColumn colTotalCost;
    public TableColumn colDate;
    public TableColumn colTime;
    public ObservableList<WastageTM> obWastageListTM = FXCollections.observableArrayList();
    public Label lblArthur;
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    WastageService wastageBO=(WastageServiceImpl) ServiceFactory.getInstance().getService(ServiceType.WASTAGE);

    public void initialize() {
        lblArthur.setText(LoginFormController.arthur);
        addTable();
        addSearch();

    }

    private void addSearch() {
        try {
            final List<ProductTM> allProductTM = converterList.formProductTMList(itemBO.getAllItem());
            String[] names = new String[allProductTM.size()];
            for (int i = 0; i < allProductTM.size(); i++) {
                final ProductTM productTM = allProductTM.get(i);
                names[i] = productTM.getItemCode();

            }
            TextFields.bindAutoCompletion(txtSearch, names);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addTable() {
        if (obWastageListTM.size() > 0) {
            obWastageListTM.clear();
        }
        colArthur.setCellValueFactory(new PropertyValueFactory<>("arthur"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            final List<WastageTM> allWastageTM = wastageBO.getAllWastage().stream().map(w -> new WastageTM(
                    w.getWastageId(),
                    w.getArthur(),
                    w.getReason(),
                    w.getQty(),
                    w.getCost(),
                    w.getDate().toLocalDate(),
                    w.getTime().toLocalTime(),
                    w.getItemCode()
            )).collect(Collectors.toList());
            for (WastageTM w : allWastageTM) {
                final ProductTM productTM = converterTM.formProductTM(itemBO.searchItem(w.getItemCode()));
                w.setDesc(productTM.getDescription());
                w.setCost(productTM.getUtilPrice());
                w.setTotalCost(w.getQty() * w.getCost());
                obWastageListTM.add(w);

            }
            mainTable.setItems(obWastageListTM);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void btnSaveWastage(ActionEvent actionEvent) {
        if (!txtReason.getText().trim().isEmpty()) {
            if (Regex.isTextFieldValid(lk.ijse.hardware.util.enm.TextFields.INTEGER, txtQty.getText())) {
                try {
                    int qty = Integer.parseInt(txtQty.getText());
                    final ProductTM productTM = converterTM.formProductTM(itemBO.searchItem(lblItemCode.getText()));
                    final boolean save = wastageBO.saveWastage(new WastageDTO(
                            wastageBO.generateWastageID(),
                            lblArthur.getText(),
                            txtReason.getText(),
                            qty,
                            productTM.getUtilPrice(),
                            Date.valueOf(LocalDate.now()),
                            Time.valueOf(LocalTime.now()),
                            productTM.getItemCode()
                    ));
                    if (save) {
                        if (itemBO.removeQty(productTM.getItemCode(), qty)) {
                            new Alert(Alert.AlertType.INFORMATION, "Removed on the Stock").show();
                            addTable();
                        }


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void btnIAddItem(ActionEvent actionEvent) throws IOException {
        AddItemFormController.setFinder(Finder.WASTAGE_LIST, this);
        Navigation.alertWindow(Route.ADD_ITEM_LIST);

    }


    public void saveValue(AddItemTM addItem) {
        if (addItem != null) {
            txtSearch.setText(addItem.getItemCode());
            lblItemCode.setText(addItem.getItemCode());
            lblDesc.setText(addItem.getDescription());

        }

    }

    public void keyQtyEvent(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.hardware.util.enm.TextFields.INTEGER, txtQty);
    }
}
