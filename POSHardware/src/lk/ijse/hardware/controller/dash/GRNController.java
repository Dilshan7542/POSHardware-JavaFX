package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.hardware.controller.LoginFormController;
import lk.ijse.hardware.controller.find.AddItemFormController;
import lk.ijse.hardware.dto.GrnDTO;
import lk.ijse.hardware.dto.GrnDetailDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.*;
import lk.ijse.hardware.service.custom.impl.*;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.util.enm.Finder;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GRNController {

    private static GRNController grnController;
    public Label lblArthur;
    public Label lblSupplier;
    public Label lblTotalCost;
    public Pane btnPrint;
    public ObservableList<PoListTM> obPoList = FXCollections.observableArrayList();
    SupplierTM supplier;
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    PurchasePoService purchasePoBO=(PurchasePoServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_PO);
    SupplierItemService supplierItemBO=(SupplierItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER_ITEM);
    SupplierService supplierBO=(SupplierServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
    PurchaseGrnService purchaseGrnBO=(PurchaseGrnServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_GRN);
    List<PoTM> allPoList;
    Converter converterTM=new Converter();
    ConverterList converterList=new ConverterList();
    @FXML
    private Label lblPendingCount;
    @FXML
    private JFXComboBox<PoTM> cmbPendingGRN;
    @FXML
    private Label lblPoId;
    @FXML
    private Label lblPoDate;
    @FXML
    private Label lblPoTime;
    @FXML
    private JFXTextField txtInvoiceNumber;
    @FXML
    private JFXDatePicker grnDate;
    @FXML
    private Label lblGrnId;
    @FXML
    private Label lblGrnDate;
    @FXML
    private Label lblGrnTime;
    @FXML
    private JFXButton btnAddItem;
    @FXML
    private JFXButton btnSaveGrn;
    @FXML
    private TableView mainTable;
    @FXML
    private TableColumn<PoListTM, String> colItemCode;
    @FXML
    private TableColumn<PoListTM, String> colDesc;
    @FXML
    private TableColumn<PoListTM, Double> colCostPrice;
    @FXML
    private TableColumn<PoListTM, Integer> colQty;
    @FXML
    private TableColumn colRecode;
    @FXML
    private TableColumn colTotalCost;
    @FXML
    private TableColumn colRemove;

    public static void getOrderValue(GrnTM grn) {
        try {

            if (GRNController.grnController.obPoList.size() > 0) {
                GRNController.grnController.obPoList.clear();
            }
            ItemService itemService=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
            SupplierService supplierService=(SupplierServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
            PurchaseGrnService purchaseGrnService=(PurchaseGrnServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_GRN);

            Converter converter=new Converter();
            GRNController.grnController.supplier = converter.formSupplierTM(supplierService.searchSuppler(grn.getSupplierId()));
            // grn.getSupplierId()


            final List<GrnDetailTM> grnDetails = purchaseGrnService.searchGrnDetail(grn.getGrnId()).stream().map(g -> converter.formGrnDetailTM(g)).collect(Collectors.toList());
            double total = 0.0;
            for (GrnDetailTM g : grnDetails) {
                PoListTM poList = new PoListTM();
                poList.setItemCode(g.getItemCode());
                final ProductTM product = converter.formProductTM(itemService.searchItem(g.getItemCode()));
                poList.setDesc(product.getDescription());
                poList.setCostPrice(g.getCostPrice());
                poList.setQty(g.getQty());
                poList.setRecode(product.getRecode());
                poList.setTotalCost(poList.getQty() * poList.getCostPrice());
                total += poList.getTotalCost();
                GRNController.grnController.obPoList.add(poList);
            }
            GRNController.grnController.lblTotalCost.setText(total + "");
            GRNController.grnController.addLabelData(grn);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void initialize() {

        addComBOx();
        addFields();
        lblArthur.setText(LoginFormController.arthur);

    }

    private void addTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colRecode.setCellValueFactory(new PropertyValueFactory<>("recode"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("hbBox"));
        try {
            boolean b = true;
            for (PoListTM p : obPoList) {
                ProductTM product = converterTM.formProductTM(itemBO.searchItem(p.getItemCode()));
                if (b) {
                    PoTM po = converterTM.formPoTM(purchasePoBO.searchPO(p.getPoId()));
                    supplier = converterTM.formSupplierTM(supplierBO.searchSuppler(po.getSpId()));
                    b = false;
                }
                SupplierListTM supplierList = converterTM.formSupplierListTM(supplierItemBO.searchSupplerItem(p.getItemCode()));
                p.setDesc(product.getDescription());
                p.setRecode(product.getRecode());
                if (supplierList != null) {
                    p.setCostPrice(supplierList.getCostPrice());
                    p.setTotalCost(supplierList.getCostPrice() * p.getQty());
                }

                addRemoveBtn(p);
            }

            lblSupplier.setText(supplier.getName());
            colQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colQty.setOnEditCommit(event -> {
                PoListTM rowValue = event.getRowValue();
                rowValue.setQty(event.getNewValue());
                rowValue.setTotalCost(rowValue.getCostPrice() * event.getNewValue());
                addTotalCost();
                mainTable.refresh();
            });

            colCostPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colCostPrice.setOnEditCommit(event -> {
                PoListTM rowValue = event.getRowValue();
                rowValue.setCostPrice(event.getNewValue());
                rowValue.setTotalCost(rowValue.getQty() * event.getNewValue());
                addTotalCost();
                mainTable.refresh();
            });
            addTotalCost();

            mainTable.setItems(obPoList);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addRemoveBtn(PoListTM po) {
        po.setHbBox(Design.removeBtn());
        po.getHbBox().setOnMouseClicked(event -> {
            obPoList.remove(po);
            addTotalCost();
        });
    }

    private void addFields() {
        btnPrint.setOpacity(0.1);
        btnPrint.setDisable(true);
        try {
            grnDate = DateFormat.hideNextDate(grnDate);
            grnDate = DateFormat.formatDate(grnDate, "YYY/MM/dd");
            lblGrnId.setText(purchaseGrnBO.generateGrnID());
            lblGrnDate.setText(String.valueOf(LocalDate.now()));
            lblGrnTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addComBOx() {
        if (cmbPendingGRN.getValue() != null) {
            cmbPendingGRN.getItems().clear();
        }


        int count = 0;
        try {
            allPoList = converterList.formPoTMList(purchasePoBO.getAllPO());
            List<GrnTM> allGrnList = purchaseGrnBO.getAllGrn().stream().map(g -> converterTM.formGrnTM(g)).collect(Collectors.toList());
            if (allGrnList.size() > 0) {
                for (PoTM p : allPoList) {
                    int num = 0;
                    for (GrnTM g : allGrnList) {
                        if (g.getPoId().equals(p.getPoId())) {
                            num++;
                            break;
                        }
                    }
                    if (num == 0) {
                        cmbPendingGRN.getItems().add(p);
                        count++;

                    }
                }
            } else {
                for (PoTM p : allPoList) {
                    cmbPendingGRN.getItems().addAll(p);
                    count++;
                }
            }
            lblPendingCount.setText(String.format("%02d", count));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnCmbPendingGRN(ActionEvent event) {
        if (obPoList != null) {
            obPoList.clear();
        }
        btnPrint.setOpacity(1);
        btnPrint.setDisable(false);
        if (cmbPendingGRN.getValue() != null) {
            for (PoTM p : allPoList) {
                if (cmbPendingGRN.getValue().equals(p)) {
                    try {
                        lblPoId.setText(p.getPoId());
                        lblPoDate.setText(p.getDate() + "");
                        lblPoTime.setText(p.getTime() + "");
                        List<PoListTM> allPoListDetail = purchasePoBO.getAllPoListDetail().stream().map(pl -> converterTM.formPoListTM(pl)).collect(Collectors.toList());
                        for (PoListTM list : allPoListDetail) {
                            if (p.getPoId().equals(list.getPoId())) {
                                btnAddItem.setDisable(false);
                                btnSaveGrn.setDisable(false);

                                obPoList.add(list);
                            }
                        }
                        addTable();
                        return;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    void btnAddItem(ActionEvent event) throws IOException {
        AddItemFormController.setFinder(Finder.GRN_LIST, this);
        Navigation.alertWindow(Route.ADD_ITEM_LIST);
    }

    @FXML
    void btnGrnReport(ActionEvent event) throws IOException {
        grnController = this;
        Navigation.alertWindow(Route.GRN);
    }

    @FXML
    void btnSaveGrn(ActionEvent event) throws SQLException { //==================================================>>>>>>>>>>>>>

        if (isValidAll()) {
            if (grnDate.getValue() == null) {
                grnDate.setValue(LocalDate.now());
            }
            try {
                List<GrnDetailDTO> grnDetails = new ArrayList<>();
                List<SupplierListTM> supplierLists = new ArrayList<>();
                for (PoListTM p : obPoList) {
                    grnDetails.add(new GrnDetailDTO(lblGrnId.getText(), p.getCostPrice(), p.getQty(), p.getItemCode()));
                    supplierLists.add(new SupplierListTM(supplier.getId(), p.getItemCode(), p.getCostPrice(), p.getQty())); // SEND Qty TO RECODE
                }
                boolean isSaved = purchaseGrnBO.saveGrn(new GrnDTO(
                        lblGrnId.getText(),
                        lblArthur.getText(),
                        txtInvoiceNumber.getText(),
                        Date.valueOf(grnDate.getValue()),
                        Time.valueOf(LocalTime.now()),
                        lblPoId.getText(), supplier.getId(),
                        grnDetails
                ));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "GRN ORDER COMPLETE").show();
                    addComBOx();
                    addFields();
                    clearFields();
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidAll() {
        boolean valid = false;
        valid = Regex.setTextColor(TextFields.INVOICE, txtInvoiceNumber);
        if (!valid) {
            txtInvoiceNumber.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        } else {
            txtInvoiceNumber.setUnFocusColor(Paint.valueOf("Black"));
            if (obPoList.size() > 0) {
                for (PoListTM p : obPoList) {
                    if (p.getQty() <= 0) {
                        new Alert(Alert.AlertType.WARNING, p.getItemCode() + "\t" + p.getDesc() + " Please Add Qty").show();
                        return false;
                    } else if (p.getCostPrice() <= 0) {
                        new Alert(Alert.AlertType.WARNING, p.getItemCode() + "\t" + p.getDesc() + " Please Add Cost Price").show();
                        return false; // IS ALL VALID
                    }
                }
                return true;
            } else {
                return false;
            }
        }

    }

    public void saveValue(AddItemTM addItem) {
        if (addItem != null && cmbPendingGRN.getValue() != null) {
            try {

                PoListTM poList = new PoListTM();
                poList.setItemCode(addItem.getItemCode());
                poList.setRecode(addItem.getRecode());
                poList.setDesc(addItem.getDescription());
                poList.setQty(0);
                SupplierListTM supplierList = converterTM.formSupplierListTM(supplierItemBO.searchSupplerItem(addItem.getItemCode()));
                if (supplierList != null) {
                    poList.setCostPrice(supplierList.getCostPrice());
                }
                addRemoveBtn(poList);
                obPoList.add(poList);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public void keyInvoiceBtn(KeyEvent keyEvent) {
        if (txtInvoiceNumber != null) {
            Regex.setTextColor(TextFields.INVOICE, txtInvoiceNumber);
        }

    }

    public void clearFields() {
        lblPoId.setText("-");
        lblSupplier.setText("-");
        lblPoDate.setText("-");
        lblPoTime.setText("-");
        txtInvoiceNumber.setText("");
        grnDate.getEditor().clear();
        grnDate.getEditor().setText("invoice Date");

    }

    public void addTotalCost() {
        double total = 0;
        for (PoListTM p : obPoList) {
            total += p.getTotalCost();
        }
        lblTotalCost.setText(total + "");
    }

    public void btnPrint(MouseEvent mouseEvent) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("poId", lblPoId.getText());
            map.put("poDate", lblPoDate.getText());
            map.put("poTime", lblPoTime.getText());
            map.put("grnId", lblGrnId.getText());
            map.put("grnDate", lblGrnDate.getText());
            map.put("grnTime", lblGrnTime.getText());
            map.put("arthur", lblArthur.getText());
            map.put("supplier", lblSupplier.getText());
            map.put("invoiceNumber", txtInvoiceNumber.getText());

            map.put("totalCost", lblTotalCost.getText());


            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/lk/ijse/hardware/report/GrnReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanCollectionDataSource(obPoList));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void addLabelData(GrnTM grn) {
        btnPrint.setDisable(false);
        btnPrint.setOpacity(1);
        lblArthur.setText(grn.getArthur());
        lblPoId.setText(grn.getPoId());
        lblSupplier.setText(grn.getSupplierId());
        try {
            final PoTM po = converterTM.formPoTM(purchasePoBO.searchPO(grn.getPoId()));
            lblPoDate.setText(po.getDate() + "");
            lblPoTime.setText(po.getTime() + "");
            lblGrnId.setText(grn.getGrnId());
            lblGrnDate.setText(grn.getDate() + "");
            lblGrnTime.setText(grn.getTime() + "");
            txtInvoiceNumber.setText(grn.getInvoiceId());
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colRecode.setCellValueFactory(new PropertyValueFactory<>("recode"));
            colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
            mainTable.setItems(obPoList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
