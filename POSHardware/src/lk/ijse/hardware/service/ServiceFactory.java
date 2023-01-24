package lk.ijse.hardware.service;

import lk.ijse.hardware.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.hardware.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory(){}
    public static ServiceFactory getInstance(){
        return serviceFactory==null ? serviceFactory=new ServiceFactory():serviceFactory;
    }
    public SuperService getService(ServiceType serviceType){
        switch (serviceType) {
            case CATEGORY:
                return new CategoryServiceImpl();
            case CUSTOMER:
                return new CustomerServiceImpl();
            case PURCHASE_GRN:
                return new PurchaseGrnServiceImpl();
            case ITEM:
                return new ItemServiceImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderServiceImpl();
            case PAYMENT:
                return new PaymentServiceImpl();
            case PURCHASE_PAYMENT:
                return new PurchasePaymentServiceImpl();
            case PURCHASE_PO:
                return new PurchasePoServiceImpl();
            case QUERY:
                return new QueryServiceImpl();
            case SUPPLIER:
                return new SupplierServiceImpl();
            case SUPPLIER_ITEM:
                return new SupplierItemServiceImpl();
            case USER:
                return new UserServiceImpl();
            case WASTAGE:
                return new WastageServiceImpl();
            default:
                return null;
        }
    }
}
