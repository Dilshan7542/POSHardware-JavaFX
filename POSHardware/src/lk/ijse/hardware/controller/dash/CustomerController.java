package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.hardware.controller.POSMainFormController;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.CustomerService;
import lk.ijse.hardware.service.custom.impl.CustomerServiceImpl;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.view.tm.*;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomerController {

    public JFXButton btnAddOrder;
    public static Route route;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtPhone;
    public JFXTextField txtSearch;
    public TableColumn<CustomerTM,String> colId;
    public TableColumn<CustomerTM,String> colName;
    public TableColumn<CustomerTM,String> colEmail;
    public TableColumn<CustomerTM,String> colAddress;
    public TableColumn<CustomerTM,String> colPhone;
    public TableColumn<CustomerTM,Double> colSalary;
    public TableColumn colEdit;
    public TableColumn colViewDetail;
    public TableView<CustomerTM> mainTable;
    public Label lblErrorIdValid;
    public TableColumn colDate;
    boolean isUpdate=false;
    ConverterList converterList=new ConverterList();
    CustomerTM customer;
    CustomerService customerBO=(CustomerServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    ObservableList<CustomerTM> obCustomerList= FXCollections.observableArrayList();
    public void initialize(){
            addBtnControl();
            addTable();
    }

    private void addTable() {

        if(obCustomerList.size()>0){
            obCustomerList.clear();
        }
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        colViewDetail.setCellValueFactory(new PropertyValueFactory<>("viewDetailBtn"));
        try {
            List<CustomerTM> allCustomer = converterList.formCustomerTMList(customerBO.getAllCustomer());
            for(CustomerTM c: allCustomer){
                obCustomerList.add(c);
            }
            addButton();
            mainTable.setItems(obCustomerList);
            mainTable.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addButton() {
        for(CustomerTM c:obCustomerList){
            c.setEditBtn(Design.editBtn());
            c.getEditBtn().setOnMouseClicked(event -> {
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are Sure Edit For Data ?");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ButtonType.APPLY,ButtonType.CANCEL);
                Optional<ButtonType> result=alert.showAndWait();
                if(result.get()==ButtonType.APPLY) {
                    disableAllBtn();
                    c.getEditBtn().setDisable(false);
                    c.getEditBtn().setOpacity(1);
                    setTextFields(c);
                    customer=c;
                    isUpdate=true;
                }
            });
            c.setViewDetailBtn(Design.viewDetailBtn());

        }
    }

    private void setTextFields(CustomerTM c) {

        txtId.setText(c.getCustomerId());
        txtId.setStyle("-fx-fill: red");
        txtName.setText(c.getName());
        txtEmail.setText(c.getEmail());
        txtPhone.setText(c.getPhone());


    }

    private void disableAllBtn() {
        for(CustomerTM c:obCustomerList){
            c.getEditBtn().setDisable(true);
            c.getEditBtn().setOpacity(0.1);
            c.getViewDetailBtn().setDisable(true);
            c.getViewDetailBtn().setOpacity(0.1);
        }
    }

    private void addBtnControl() {
        if(Route.CUSTOMER.equals(route)){
            btnAddOrder.setVisible(false);
        }else{
            btnAddOrder.setVisible(true);
        }
    }

    public void btnAdd(ActionEvent actionEvent) {

                try {
                    if (validText() instanceof Boolean) {

                        if (!isUpdate) {


                            boolean isSaved = customerBO.saveCustomer(new CustomerDTO(
                                    customerBO.generateCustomerID(),
                                    txtId.getText(),
                                    txtName.getText(),
                                    txtEmail.getText(),
                                    txtAddress.getText(),
                                    0.0,
                                    txtPhone.getText(),
                                    Date.valueOf(LocalDate.now())
                            ));
                            if (isSaved) {
                                clearFields();
                                addTable();
                                new Alert(Alert.AlertType.INFORMATION, "Successfully").show();


                            }else {
                            new Alert(Alert.AlertType.WARNING, "Please Enter Valid Value").show();
                             }
                        } else {
                            isUpdate=false;
                            final boolean isSaved = customerBO.updateCustomer(new CustomerDTO(
                                    customer.getId(),
                                    txtId.getText(),
                                    txtName.getText(),
                                    txtEmail.getText(),
                                    txtAddress.getText(),
                                    customer.getSalary(),
                                    txtPhone.getText(),
                                    Date.valueOf(LocalDate.now())
                            ));
                            if(isSaved){
                                new Alert(Alert.AlertType.INFORMATION, "Update Successfully").show();
                                clearFields();
                                    addTable();

                                    for(CustomerTM c :obCustomerList){
                                        c.getViewDetailBtn().setDisable(false);
                                        c.getViewDetailBtn().setOpacity(1);
                                        c.getEditBtn().setDisable(false);
                                        c.getEditBtn().setOpacity(1);
                                    }
                            }

                        }
                        }
                    } catch(SQLException | ClassNotFoundException e){
                        e.printStackTrace();
                    }



    }

    public void btnAddOrder(ActionEvent actionEvent) {
        CustomerTM customer = mainTable.getSelectionModel().getSelectedItem();
        if(customer!=null){
            POSMainFormController.getAllFields(Route.CUSTOMER,customer);
            Navigation.navigationHide();
        }

    }
    public void onKeyId(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(isUpdate){
            lblErrorIdValid.setText(null);
            if(Regex.setTextColor(TextFields.LANKAN_ID,txtId)) {
                isKeyEnter(keyEvent, txtName);
            }
        }else{
        if(customerBO.searchCustomerIdOrNIC(txtId.getText())){
            lblErrorIdValid.setText("Customer Already Exist!");
            txtId.setFocusColor(Paint.valueOf("Red"));

        }else{
            lblErrorIdValid.setText(null);
        if(Regex.setTextColor(TextFields.LANKAN_ID,txtId)) {
            isKeyEnter(keyEvent, txtName);
        }
        }

        }
    }
    public void onKeyName(KeyEvent keyEvent) {
        if(Regex.setTextColor(TextFields.NAME,txtName)) {
            isKeyEnter(keyEvent, txtEmail);
        }
    }

    public void onKeyEmail(KeyEvent keyEvent) {
        if(Regex.setTextColor(TextFields.EMAIL,txtEmail)) {
            isKeyEnter(keyEvent, txtAddress);
        }
    }

    public void onKeyAddress(KeyEvent keyEvent) {
        if(Regex.setTextColor(TextFields.ADDRESS,txtAddress)) {
            isKeyEnter(keyEvent, txtPhone);
        }
    }

    public void onKeyPhone(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtPhone);


    }public void isKeyEnter(KeyEvent keyEvent,JFXTextField textField) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            textField.requestFocus();
        }
    }  private Object validText() throws SQLException, ClassNotFoundException {
            if(isUpdate){
                if(!Regex.setTextColor(TextFields.LANKAN_ID,txtId)) {
                   return txtId;
                }
            }else{
            if(customerBO.searchCustomerIdOrNIC(txtId.getText())){
                txtId.setUnFocusColor(Paint.valueOf("Red"));
                return txtId;
            }
            }
            if(!Regex.setTextColor(TextFields.LANKAN_ID,txtId)){
                txtId.setUnFocusColor(Paint.valueOf("Red"));
                return txtId;
            }else{
                if(!Regex.setTextColor(TextFields.NAME,txtName)){
                    txtName.setUnFocusColor(Paint.valueOf("Red"));
                    return txtName;
                }else{
                    boolean valid=Regex.setTextColor(TextFields.EMAIL,txtEmail);
                    if(txtEmail.getText().trim().isEmpty()){
                        txtEmail.setText("No Email");
                        valid=true;
                    }
                    if(!valid){
                        txtEmail.setUnFocusColor(Paint.valueOf("Red"));
                        return txtEmail;
                    }else{
                        valid=Regex.setTextColor(TextFields.ADDRESS,txtAddress);
                        if(txtAddress.getText().trim().isEmpty()){
                            txtAddress.setText("No Address");
                            valid=true;
                        }
                        if(!valid){
                            txtAddress.setUnFocusColor(Paint.valueOf("Red"));
                            return  txtAddress;
                        }else{
                            valid=Regex.setTextColor(TextFields.PHONE,txtPhone);
                            if(txtPhone.getText().trim().isEmpty()){
                                txtPhone.setText("No Phone");
                                valid=true;
                            }
                            if(!valid){
                                txtPhone.setUnFocusColor(Paint.valueOf("Red"));
                                return txtPhone;
                            }


                        }

                    }
                }
            }


        txtName.setUnFocusColor(Paint.valueOf("black"));
        txtEmail.setUnFocusColor(Paint.valueOf("Black"));
        txtAddress.setUnFocusColor(Paint.valueOf("Black"));
        txtPhone.setUnFocusColor(Paint.valueOf("Black"));
        return true;

    } public void clearFields(){
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtPhone.clear();
    }

    private void addId() {
        try {
            txtId.setText(customerBO.generateCustomerID());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
