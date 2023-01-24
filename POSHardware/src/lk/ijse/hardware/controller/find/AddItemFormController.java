package lk.ijse.hardware.controller.find;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.hardware.controller.dash.GRNController;
import lk.ijse.hardware.controller.dash.POFormController;
import lk.ijse.hardware.controller.dash.SupplierListController;
import lk.ijse.hardware.controller.dash.WastageController;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Navigation;
import lk.ijse.hardware.util.enm.Finder;
import lk.ijse.hardware.view.tm.AddItemTM;
import lk.ijse.hardware.view.tm.PoListTM;
import lk.ijse.hardware.view.tm.ProductTM;
import lk.ijse.hardware.view.tm.SupplierListTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddItemFormController<T> {
    static Object obj;
    private static Finder finder;
    public TableColumn colItem;
    public TableColumn colDes;
    public TableColumn colQty;
    public TableColumn colPrice;
    public JFXTextField txtSearch;
    public TableView mainTable;
    public JFXButton btnAddItem;
    public ObservableList<AddItemTM> obItemList = FXCollections.observableArrayList();
    ArrayList<String> idArrayList;
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    ConverterList converterList = new ConverterList();

    public static Finder getFinder() {
        return finder;
    }

    public static void setFinder(Finder finder, Object obj) {
        AddItemFormController.finder = finder;
        AddItemFormController.obj = obj;
    }

    public void initialize() {

        addData();
        addTable();
        addTableSearch();

    }

    private void addTable() {
        if (obItemList != null) {
            obItemList.clear();
        }
        try {
            colItem.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            List<ProductTM> allProductTM = converterList.formProductTMList(itemBO.getAllItem());
            for (ProductTM p : allProductTM) {
                AddItemTM item = new AddItemTM();
                item.setItemCode(p.getItemCode());
                item.setDescription(p.getDescription());
                item.setQty(p.getQty());
                item.setPrice(p.getUtilPrice());
                item.setRecode(p.getRecode());

                obItemList.add(item);
            }
            ObservableList<AddItemTM> tempOb = FXCollections.observableArrayList();
            for (AddItemTM itm : obItemList) {
                tempOb.add(itm);
            }
            for (AddItemTM itm : tempOb) {
                for (String s : idArrayList) {
                    if (itm.getItemCode().equals(s)) {
                        obItemList.remove(itm);

                    }
                }
            }
            tempOb = null;


            mainTable.setItems(obItemList);
            mainTable.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddItem(ActionEvent actionEvent) {
        AddItemTM addItemTM = (AddItemTM) mainTable.getSelectionModel().getSelectedItem();
        if (addItemTM == null) return;

        switch (finder) {
            case SUPPLIER_LIST:
                SupplierListController supplierList = (SupplierListController) obj;
                supplierList.setValue(addItemTM);

                break;
            case PO_LIST:
                POFormController poList = (POFormController) obj;
                poList.setValue(addItemTM);
                break;
            case GRN_LIST:
                GRNController grnList = (GRNController) obj;
                grnList.saveValue(addItemTM);
                break;
            case WASTAGE_LIST:
                WastageController wastage = (WastageController) obj;
                wastage.saveValue(addItemTM);
                break;
        }
        if (addItemTM != null) {
            Navigation.navigationHide();
        }

    }

    public void addData() {
        idArrayList = new ArrayList<>();
        switch (finder) {
            case SUPPLIER_LIST:
                SupplierListController listController = (SupplierListController) obj;
                for (SupplierListTM s : listController.obSupplierListTM) {
                    idArrayList.add(s.getItemCode());
                }
                break;
            case PO_LIST:
                POFormController poList = (POFormController) obj;
                for (SupplierListTM s : POFormController.obSupplerList) {
                    idArrayList.add(s.getItemCode());
                }
                break;
            case GRN_LIST:
                GRNController grnList = (GRNController) obj;
                for (PoListTM p : grnList.obPoList) {
                    idArrayList.add(p.getItemCode());
                }
                break;
            case WASTAGE_LIST:
                System.out.println("wastage");
                break;
        }
    }

    public void btnSearch(ActionEvent actionEvent) {


    }

    public void onKeySearch(KeyEvent keyEvent) {

    }

    public void addTableSearch() {

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AddItemTM> filteredData = new FilteredList<>(obItemList, b -> true);

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
                } else return String.valueOf(item.getPrice()).indexOf(lowerCaseFilter) != -1;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<AddItemTM> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator tm the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());

        // 5. Add sorted (and filtered) data tm the table.
        mainTable.setItems(sortedData);
    }

    public void tableClick(MouseEvent mouseEvent) {
        if (mainTable.getSelectionModel().getSelectedIndex() != -1) {
            btnAddItem.setDisable(false);
        }
    }
}
