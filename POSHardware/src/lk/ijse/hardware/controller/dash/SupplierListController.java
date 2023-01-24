package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import lk.ijse.hardware.controller.find.AddItemFormController;
import lk.ijse.hardware.dto.SupplierItemDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.SupplierItemService;
import lk.ijse.hardware.service.custom.SupplierService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.SupplierItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.SupplierServiceImpl;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.util.enm.Finder;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.AddItemTM;
import lk.ijse.hardware.view.tm.ProductTM;
import lk.ijse.hardware.view.tm.SupplierListTM;
import lk.ijse.hardware.view.tm.SupplierTM;
import rex.utils.S;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SupplierListController {

    public ObservableList<SupplierListTM> obSupplierListTM = FXCollections.observableArrayList();
    public JFXComboBox<SupplierTM> cmbSupplier;
    public TableColumn<SupplierListTM, String> colItem;
    public TableColumn<SupplierListTM, String> colDes;
    public TableColumn<SupplierListTM, Double> colCostPrice;
    public TableColumn<SupplierListTM, Integer> colRecode;
    public TableColumn<SupplierListTM, Double> colCost;
    public TableColumn<SupplierListTM, String> colRemove;
    public TableView mainTable;
    public JFXButton btnAddItem;
    ConverterList converterList = new ConverterList();
    Converter converterTM = new Converter();
    SupplierService supplierBO=(SupplierServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
    SupplierItemService supplierItemBO=(SupplierItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER_ITEM);
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);

    public void initialize() {

        addComboBox();
        addTable();

    }

    private void addComboBox() {

        try {
            List<SupplierTM> allSupplierTM = converterList.formSupplierTMList(supplierBO.getAllSuppler());
            for (SupplierTM s : allSupplierTM) {
                cmbSupplier.getItems().add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addTable() {

        colItem.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colRecode.setCellValueFactory(new PropertyValueFactory<>("recode"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("hbBox"));


    }

    public void btnAddItem(ActionEvent actionEvent) throws IOException {

        AddItemFormController.setFinder(Finder.SUPPLIER_LIST, this);
        Navigation.alertWindow(Route.ADD_ITEM_LIST);


    }

    public void setValue(AddItemTM item) {

        try {
            for (SupplierListTM s : obSupplierListTM) {
                if (s.getItemCode().equals(item.getItemCode())) {

                    return;
                }
            }


            if (supplierItemBO.saveSupplerItem(new SupplierItemDTO(cmbSupplier.getValue().getId(), item.getItemCode(), 0, 0))) {
                new Alert(Alert.AlertType.INFORMATION, "Completed").show();
                addDataToTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cmbSupplerBtn(ActionEvent actionEvent) {

        if (cmbSupplier.getValue() != null) {
            btnAddItem.setDisable(false);
            addDataToTable();
        }

    }

    public void addDataToTable() {
        if (obSupplierListTM != null) {
            obSupplierListTM.clear();
        }
        try {
            List<SupplierListTM> allSupplierListTM = converterList.formSupplierListTMList(supplierItemBO.getAllSupplerItem());

            for (SupplierListTM s : allSupplierListTM) {
                if (cmbSupplier.getValue().getId().equals(s.getSpId())) {
                    ProductTM productTM = converterTM.formProductTM(itemBO.searchItem(s.getItemCode()));
                    s.setTotalCost(s.getCostPrice() * s.getRecode());
                    s.setHbBox(Design.removeBtn());
                    s.getHbBox().setOnMouseClicked(event -> {
                        removeData(s);
                    });

                    s.setDescription(productTM.getDescription());
                    obSupplierListTM.add(s);
                }

            }
            try {
                colCostPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

                colCostPrice.setOnEditCommit(event -> {

                    if (Regex.isTextFieldValid(TextFields.DOUBLE, event.getNewValue() + "") | Regex.isTextFieldValid(TextFields.INTEGER, event.getNewValue() + "")) {
                        SupplierListTM supplierListTM = event.getRowValue();
                        supplierListTM.setCostPrice(event.getNewValue());

                        boolean isUpdate = false;
                        try {
                            isUpdate = supplierItemBO.updateCostPrice(converterTM.toSupplierListTM(supplierListTM));
                            if (isUpdate) {
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Please Enter Valid Number").show();
                    }
                });

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


            mainTable.setItems(obSupplierListTM);
            mainTable.refresh();
            addTable();
            System.gc();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void removeData(SupplierListTM list) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are Sure Remove This Item");
        alert.getButtonTypes().clear();
        alert.setTitle("Remove");
        alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.APPLY) {
            try {
                if (supplierItemBO.deleteSupplerItem(list.getItemCode())) {
                    obSupplierListTM.remove(list);
                    mainTable.refresh();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
