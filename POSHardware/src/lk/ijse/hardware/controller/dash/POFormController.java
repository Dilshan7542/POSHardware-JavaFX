package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.hardware.controller.LoginFormController;
import lk.ijse.hardware.controller.find.AddItemFormController;
import lk.ijse.hardware.dto.PoDTO;
import lk.ijse.hardware.dto.PoDetailDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.PurchasePoService;
import lk.ijse.hardware.service.custom.SupplierItemService;
import lk.ijse.hardware.service.custom.SupplierService;
import lk.ijse.hardware.service.custom.impl.*;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.util.enm.Finder;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.view.tm.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class POFormController {

    public static ObservableList<SupplierListTM> obSupplerList = FXCollections.observableArrayList();
    public Label lblPoId;
    public Label lblDate;
    public JFXComboBox<SupplierTM> cmbSupplier;
    public JFXDatePicker poDate;
    public TableColumn colItem;
    public TableColumn colDesc;
    public TableColumn<SupplierListTM, Integer> colQty;
    public TableColumn colCostPrice;
    public TableColumn colRecode;
    public TableColumn colTotalCost;
    public TableColumn colRemove;
    public TableView mainTable;
    public Label lblTime;
    public Label lblArthur;
    public JFXButton btnSavePo;
    public JFXButton btnClearQty;
    public JFXButton btnAddItem;
    List<SupplierListTM> allSupplierList;
    PurchasePoService purchasePoBO=(PurchasePoServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_PO);
    SupplierService supplierBO=(SupplierServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
    SupplierItemService supplierItemBO=(SupplierItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER_ITEM);
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();

    public void initialize() {
        addComboBox();
        lblArthur.setText(LoginFormController.arthur);

    }


    private void addComboBox() {
        try {

            btnClearQty.setDisable(true);
            btnSavePo.setDisable(true);
            poDate = DateFormat.formatDate(poDate);
            poDate.setValue(LocalDate.now().plusDays(1));
            lblDate.setText(LocalDate.now() + ""); // add date
            lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            lblPoId.setText(purchasePoBO.generatePOID());
            List<SupplierTM> allSupplier = converterList.formSupplierTMList(supplierBO.getAllSuppler());
            for (SupplierTM s : allSupplier) {
                cmbSupplier.getItems().addAll(s);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addToTable() {
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colRecode.setCellValueFactory(new PropertyValueFactory<>("recode"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("hbBox"));

        try {
            if (obSupplerList != null) {
                obSupplerList.clear();
            }
            allSupplierList = converterList.formSupplierListTMList(supplierItemBO.getAllSupplerItem());
            for (SupplierListTM s : allSupplierList) {
                if (cmbSupplier.getValue().getId().equals(s.getSpId())) {
                    ProductTM product = converterTM.formProductTM(itemBO.searchItem(s.getItemCode()));
                    s.setQty(0);
                    s.setDescription(product.getDescription());
                    s.setTotalCost(s.getCostPrice() * s.getQty());
                    obSupplerList.add(s);
                }
            }
            colQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colQty.setOnEditCommit(event -> {
                SupplierListTM rowValue = event.getRowValue();
                rowValue.setQty(event.getNewValue());

                rowValue.setTotalCost(event.getNewValue() * rowValue.getCostPrice());
                mainTable.refresh();
                btnClearQty.setDisable(false);
                btnSavePo.setDisable(false);

            });

            mainTable.setItems(obSupplerList);

            removeBtn();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeBtn() {
        for (SupplierListTM s : obSupplerList) {
            s.setHbBox(Design.removeBtn());
            s.getHbBox().setOnMouseClicked(event -> {
                obSupplerList.remove(s);
                mainTable.refresh();

            });
        }

    }


    public void btnIAddItem(ActionEvent actionEvent) throws IOException {
        AddItemFormController.setFinder(Finder.PO_LIST, this);
        Navigation.alertWindow(Route.ADD_ITEM_LIST);


    }

    public void btnSearchPo(ActionEvent actionEvent) {
    }

    public void btnCmbSupplier(ActionEvent actionEvent) {

        if (obSupplerList != null) {
            obSupplerList.clear();
        }
        if (cmbSupplier.getValue() != null) {
            btnAddItem.setDisable(false);
            addToTable();
        }
    }

    public void setValue(AddItemTM item) {
        try {

            SupplierListTM supplierList = new SupplierListTM(cmbSupplier.getValue().getId(), item.getItemCode(), 0, 0);
            supplierList.setHbBox(Design.removeBtn());
            supplierList.getHbBox().setOnMouseClicked(event -> {
                obSupplerList.remove(supplierList);
            });
            ProductTM product = converterTM.formProductTM(itemBO.searchItem(supplierList.getItemCode()));
            supplierList.setDescription(product.getDescription());
            obSupplerList.add(supplierList);
            mainTable.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnClearQty(ActionEvent actionEvent) {
        ObservableList<SupplierListTM> obTemp = FXCollections.observableArrayList();
        for (SupplierListTM s : obSupplerList) {
            obTemp.add(s);
        }
        for (SupplierListTM s : obTemp) {
            if (s.getQty() <= 0) {
                obSupplerList.remove(s);
            }
        }

    }

    public void btnSAvePo(ActionEvent actionEvent) throws SQLException {
        Optional result = Design.getAlertConfirmation();
        List<PoListTM> poLists = new ArrayList<>();
        boolean isQtyAdd = false;
        for (SupplierListTM s : obSupplerList) {
            if (s.getQty() > 0) {
                poLists.add(new PoListTM(lblPoId.getText(), s.getItemCode(), s.getQty()));
                isQtyAdd = true;
            }
        }
        if (result.get() == ButtonType.APPLY && isQtyAdd) {
            boolean isSave = purchasePoBO.savePO(new PoDTO(
                    lblPoId.getText(),
                    lblArthur.getText(),
                    Date.valueOf(LocalDate.now()),
                    Time.valueOf(LocalTime.now()),
                    cmbSupplier.getValue().getId(),
                    Date.valueOf(poDate.getValue()),
                    poLists.stream().map(p -> new PoDetailDTO(p.getPoId(), p.getItemCode(), p.getQty())).collect(Collectors.toList())
            ));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "PoTM OrdersTM Saved!!").show();
                obSupplerList.clear();
                cmbSupplier.getItems().clear();
                addComboBox();

            }

        }


    }

}



























