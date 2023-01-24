package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.CategoryService;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.impl.CategoryServiceImpl;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.SupplierServiceImpl;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.CategoryTM;
import lk.ijse.hardware.view.tm.ProductTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ProductFormController {

    public TableView mainTable;

    public JFXComboBox<CategoryTM> cmbCategory;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colDate;
    ConverterList converterList = new ConverterList();
    CategoryService categoryBO=(CategoryServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    boolean isUpdate;
    ObservableList<ProductTM> obProductListTM = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private JFXTextField txtDes;
    @FXML
    private JFXTextField txtRemind;
    @FXML
    private JFXTextField txtPrice;
    @FXML
    private JFXComboBox<String> cmbStatus;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private Pane btnPrint;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private TableColumn<?, ?> colItemCode;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private TableColumn<?, ?> colDesc;
    @FXML
    private TableColumn<?, ?> colRemind;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colStatus;
    @FXML
    private TableColumn<?, ?> colEdit;

    public void initialize() {
        loadTable();
        addComboBox();
        addTableSearch();

    }


    private void addComboBox() {
        try {

            List<CategoryTM> allCategory = converterList.formCategoryTMList(categoryBO.getAllCategory());
            for (CategoryTM c : allCategory) {
                cmbCategory.getItems().add(c);
            }
            allCategory = null;
            System.gc();
            cmbStatus.getItems().add("Show");
            cmbStatus.getItems().add("Hide");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRemind.setCellValueFactory(new PropertyValueFactory<>("remind"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("utilPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("btnEdit"));
        try {
            txtItemCode.setText(itemBO.generateItemID());
            List<ProductTM> productsList = converterList.formProductTMList(itemBO.getAllItem());
            for (ProductTM p : productsList) {

                obProductListTM.add(p);

            }
            productsList = null;
            System.gc();
            addControlButton();
            mainTable.setItems(obProductListTM);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addControlButton() {
        for (ProductTM p : obProductListTM) {
            p.setBtnEdit(Design.editBtn());
            p.getBtnEdit().setOnMouseClicked(event -> {
                Sound.infoSound();
                txtItemCode.setText(p.getItemCode());
                txtDes.setText(p.getDescription());
                txtRemind.setText(p.getRemind() + "");
                txtPrice.setText(p.getUtilPrice() + "");
                for (ProductTM btn : obProductListTM) {
                    btn.getBtnEdit().setOpacity(0.1);
                    btn.getBtnEdit().setDisable(true);
                    isUpdate = true;
                }
                p.getBtnEdit().setOpacity(1);
            });

        }
    }

    public void btnAdd(ActionEvent actionEvent) {

        try {
            if (isAllValid()) {
                String categoryId = "C00000";
                if (cmbCategory.getValue() != null) {
                    categoryId = cmbCategory.getValue().getId();
                }

                int remind = Integer.parseInt(txtRemind.getText());
                double price = Double.parseDouble(txtPrice.getText());
                boolean status = false;
                if (cmbStatus.getValue() != null) {

                    status = !cmbStatus.getValue().startsWith("Show");
                }
                if (!isUpdate) {
                    final boolean save = itemBO.saveItem(new ItemDTO(
                            txtItemCode.getText(),
                            txtDes.getText(),
                            remind,
                            price,
                            status,
                            categoryId,
                            Date.valueOf(LocalDate.now()), 0, 0
                    ));
                    if (save) {
                        new Alert(Alert.AlertType.INFORMATION, "saved").show();
                        clearFields();
                        obProductListTM.clear();
                    }
                } else {
                    ItemDTO itemDTO = new ItemDTO();
                    itemDTO.setCategoryId(categoryId);
                    itemDTO.setDescription(txtDes.getText());
                    itemDTO.setRemind(remind);
                    itemDTO.setUtilPrice(price);
                    itemDTO.setItemCode(txtItemCode.getText());
                    itemDTO.setStatus(status);
                    if (itemBO.updateItem(itemDTO)) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                        clearFields();
                    }


                }


                loadTable();
                addTableSearch();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isAllValid() {
        if (txtDes.getText().trim().isEmpty()) {
            return false;
        } else {
            if (!Regex.isTextFieldValid(TextFields.INTEGER, txtRemind.getText())) {
                return false;
            } else {
                return Regex.isTextFieldValid(TextFields.INTEGER_DECIMAL, txtPrice.getText());
            }
        }
    }

    public void btnPrint(MouseEvent mouseEvent) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            double totalPrice = 0.0;
            int qty = 0;
            int discount = 0;
            for (ProductTM productTM : obProductListTM) {
                totalPrice += productTM.getUtilPrice();
                qty += productTM.getQty();
                discount += productTM.getDiscount();

            }
            map.put("qty", qty);
            map.put("totalPrice", totalPrice);
            map.put("discount", discount);
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/lk/ijse/hardware/report/Product.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanCollectionDataSource(obProductListTM));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void onKeyRemind(KeyEvent keyEvent) {
        if (!Regex.setTextColor(TextFields.INTEGER, txtRemind)) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus();
            }
        }
    }

    public void onKeyPrice(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER_DECIMAL, txtPrice);
    }

    public void onKeyDesc(ActionEvent actionEvent) {
        txtRemind.requestFocus();
    }

    public void addTableSearch() {

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ProductTM> filteredData = new FilteredList<>(obProductListTM, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (item.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else // Does not match.
                    if (item.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else return String.valueOf(item.getItemCode()).indexOf(lowerCaseFilter) != -1;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ProductTM> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator tm the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());

        // 5. Add sorted (and filtered) data tm the table.
        mainTable.setItems(sortedData);
    }

    public void clearFields() throws SQLException, ClassNotFoundException {
        txtItemCode.setText(itemBO.generateItemID());
        txtDes.setText("");
        txtRemind.setText("");
        txtPrice.setText("");
        cmbCategory.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
    }
}
