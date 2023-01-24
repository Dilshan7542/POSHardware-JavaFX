package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hardware.dto.CategoryDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.CategoryService;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.impl.CategoryServiceImpl;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.util.Sound;
import lk.ijse.hardware.view.tm.CategoryTM;
import lk.ijse.hardware.view.tm.ProductTM;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CategoryFormController {

    public TableView mainTable;
    @FXML
    private JFXTextField txtCategory;
    @FXML
    private JFXComboBox<String> cmbPatent;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colParent;
    @FXML
    private TableColumn colTotalProduct;
    @FXML
    private TableColumn<?, ?> colDisplay;
    @FXML
    private TableColumn<?, ?> colTime;
    @FXML
    private TableColumn<?, ?> coldEdit;
    @FXML
    private TableColumn<?, ?> colRemove;
    String categoryId;
    boolean isUpdate;
    ObservableList<CategoryTM> obCategoryList = FXCollections.observableArrayList();
    CategoryService categoryBO=(CategoryServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
ConverterList converterList=new ConverterList();

    public void initialize() {

        displayTable();
        addComboBox();


    }


    private void addComboBox() {

        for (CategoryTM c : obCategoryList) {
            cmbPatent.getItems().addAll(c.getName());

        }
    }

    private void displayTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colParent.setCellValueFactory(new PropertyValueFactory<>("parent"));
        colTotalProduct.setCellValueFactory(new PropertyValueFactory<>("totalProduct"));
        colDisplay.setCellValueFactory(new PropertyValueFactory<>("displayOnPos"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        coldEdit.setCellValueFactory(new PropertyValueFactory<>("hbBOxEdit"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("hbBOxDelete"));
        try {

            List<CategoryTM> categories = converterList.formCategoryTMList(categoryBO.getAllCategory());
            List<ProductTM> allProductTM = converterList.formProductTMList(itemBO.getAllItem());
            for (CategoryTM c : categories) {
                obCategoryList.add(c);
                for (ProductTM p : allProductTM) {
                    if (p.getCategoryId().equals(c.getName())) {
                        c.setTotalProduct(c.getTotalProduct() + 1);
                    }
                }
                c.setHbBOxEdit(Design.editBtn());
                c.getHbBOxEdit().setOnMouseClicked(event -> {
                    txtCategory.setText(c.getName());
                    categoryId = c.getId();
                    for (CategoryTM btn : obCategoryList) {
                        btn.getHbBOxDelete().setOpacity(0.1);
                        btn.getHbBOxDelete().setDisable(true);
                        btn.getHbBOxEdit().setOpacity(0.1);
                        btn.getHbBOxEdit().setDisable(true);

                    }
                    c.getHbBOxEdit().setOpacity(1);
                    isUpdate = true;

                });
                c.setHbBOxDelete(Design.removeBtn());
                c.getHbBOxDelete().setOnMouseClicked(event -> {
                    if (c.getTotalProduct() > 0) {
                        Sound.errorSound();
                        new Alert(Alert.AlertType.WARNING, "Total ProductTM until 0 You Cant Delete Category").show();
                    } else {
                        Optional<ButtonType> result = Design.getAlertConfirmation();
                        if (result.get() == ButtonType.APPLY) {

                            try {
                                if (categoryBO.deleteCategory(c.getId())) {
                                    Sound.infoSound();
                                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                                    obCategoryList.remove(c);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

            }
            categories = null;
            System.gc();

            mainTable.setItems(obCategoryList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void btnAdd(javafx.event.ActionEvent actionEvent) {

        if (txtCategory.getText().trim().isEmpty()) {
            Sound.infoSound();
            new Alert(Alert.AlertType.INFORMATION, "Please Input value").show();
            return;
        }
        mainTable.getItems().clear();
        try {
            if (!isUpdate) {

                String id = categoryBO.generateCategoryID();
                final boolean save = categoryBO.saveCategory(new CategoryDTO(id, txtCategory.getText(),
                        cmbPatent.getValue(), 0, false, Date.valueOf(LocalDate.now())));
                if (save) {
                    cmbPatent.getSelectionModel().clearSelection();
                    txtCategory.setText("");
                    initialize();
                }
            } else {
                if (categoryBO.updateCategoryName(categoryId, txtCategory.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                    cmbPatent.getSelectionModel().clearSelection();
                    txtCategory.setText("");
                    isUpdate = false;
                    initialize();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


















