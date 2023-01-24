package lk.ijse.hardware.dao;

import lk.ijse.hardware.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){}
    public static DaoFactory getInstance(){
        return daoFactory ==null ? daoFactory =new DaoFactory(): daoFactory;
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType) {
            case CATEGORY:
                return new CategoryDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case GRN:
                return new GrnDAOImpl();
            case GRN_DETAIL:
                return new GrnDetailDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrdersDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PO:
                return new PoDAOImpl();
            case PO_DETAIL:
                return new PoDetailDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case SUPPLIER_ITEM:
                return new SupplierItemDAOImpl();
            case USER:
                return new UserDAOImpl();
            case WASTAGE:
                return new WastageDAOImpl();
            default:
                return null;

        }
    }

}

