package lk.ijse.hardware.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.hardware.util.enm.Route;

import java.io.IOException;


public class Navigation {
    static Stage stage;
   public static AnchorPane pane;
    public static void navigate(Route route, AnchorPane pane ) throws IOException {

        if(stage !=null){
            stage.close();
        }
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window=null;
        String dashboard="dashboard/";
        String pos="pos/";
        if(route.equals(Route.DASHBOARD)||route.equals(Route.POS)){
         window = (Stage)Navigation.pane.getScene().getWindow();
        }
        switch(route){
            case DASHBOARD:
                window.setTitle("Dashboard");
                initUi("","MainDashBoardForm.fxml");
                break;
            case POS:
                window.setTitle("POS");
                initUi("","POSMainForm.fxml");
                break;
            case DASHBOARD_CENTER:
                initUi("","MainDashBoardForm.fxml");
                break;
            case PAYMENT:
                initUi("","PaymentForm.fxml");
                break;
            case PRODUCT:
                initUi(dashboard,"ProductForm.fxml");
                break;
            case CATEGORY:
                initUi(dashboard,"CategoryForm.fxml");
                        break;
            case SUPPLIER:
                initUi(dashboard,"SupplierForm.fxml");
                break;
            case SUPPLIER_LIST:
                initUi(dashboard,"SupplierList.fxml");
                break;
            case CUSTOMER:
                initUi(dashboard,"CustomerForm.fxml");
                break;
            case CUSTOMER_LIST:
                initUi(dashboard,"CustomerList.fxml");
                break;
            case PO:
                initUi(dashboard,"POForm.fxml");
                break;
            case GRN:
                initUi(dashboard,"GRNForm.fxml");
                break;
            case WASTAGE:
                initUi(dashboard,"Wastage.fxml");
                break;
            case PAID_ORDER:
                initUi(pos,"PaidOrder.fxml");
                break;
            case USER:
                initUi(dashboard,"UserForm.fxml");
                break;



        }

    }public static void initUi(String folder,String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("../view/"+folder+location)));
    }
    public static void alertWindow(Route route) throws IOException {
        if(stage !=null){
            stage.close();
        }
              stage=new Stage();
               String folder="";
               String location="";
        switch (route){
            case ADD_ITEM_LIST:
                stage.setTitle("Add Item");
                folder="find/";
                location="AddItem.fxml";
                break;
            case CUSTOMER:
                stage.setTitle("Customer Detail");
                folder="pos/";
                location="CustomerDetails.fxml";
                break;
            case ORDER:
                folder="pos/";
                location="OrderDetailsForm.fxml";
                break;
            case GRN:
                stage.setTitle("OLD GRN");
                folder="find/";
                location="OldGRN.fxml";

        }
              stage.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("../view/"+folder+location))));
              stage.centerOnScreen();
              stage.show();




    }public static void navigateClose(){
        if(stage !=null){
            stage.close();
        }
    }public static void navigationHide(){
        if(stage !=null){
            stage.hide();
        }
    }


}
