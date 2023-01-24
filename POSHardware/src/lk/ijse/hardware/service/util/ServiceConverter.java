package lk.ijse.hardware.service.util;

import lk.ijse.hardware.dto.*;
import lk.ijse.hardware.entity.*;
import lk.ijse.hardware.view.tm.*;

public class ServiceConverter {
    public Grn toGrn(GrnDTO grnDTO){
        return new Grn(
                grnDTO.getGrnId(),
                grnDTO.getArthur(),
                grnDTO.getInvoice(),
                grnDTO.getDate(),
                grnDTO.getTime(),
                grnDTO.getPoId(),
                grnDTO.getSpId()
        );
    }
    public GrnDTO formGrnDTO(Grn grn){
        return new GrnDTO(
                grn.getGrnId(),
                grn.getArthur(),
                grn.getInvoice(),
                grn.getDate(),
                grn.getTime(),
                grn.getPoId(),
                grn.getSpId()
        );
    }
    public  GrnDetail toGrnDetail(GrnDetailDTO grnDetailDTO){
        return new GrnDetail(
                grnDetailDTO.getGrnId(),
                grnDetailDTO.getCostPrice(),
                grnDetailDTO.getQty(),
                grnDetailDTO.getItemCode()
        );
    }
    public GrnDetailDTO formGrnDetailDTO(GrnDetail grnDetail){
        return new GrnDetailDTO(
                grnDetail.getGrnId(),
                grnDetail.getCostPrice(),
                grnDetail.getQty(),
                grnDetail.getItemCode()
        );
    }
    public Po toPo(PoDTO poDTO){
        return new Po(
            poDTO.getPoId(),
            poDTO.getArthur(),
            poDTO.getDate(),
            poDTO.getTime(),
                poDTO.getSpId(),
                poDTO.getRequestDate()
        );
    }
    public PoDTO formPoDTO(Po poDTO){
        return new PoDTO(
                poDTO.getPoId(),
                poDTO.getArthur(),
                poDTO.getDate(),
                poDTO.getTime(),
                poDTO.getSpId(),
                poDTO.getRequestDate()
        );
    }
    public PoDetail toPoDetail(PoDetailDTO poDetailDTO){
        return new PoDetail(
                poDetailDTO.getPoId(),
                poDetailDTO.getItemCode(),
                poDetailDTO.getQty()
        );
    }
    public PoDetailDTO formPoDetailDTO(PoDetail poDetail){
        return new PoDetailDTO(
                poDetail.getPoId(),
                poDetail.getItemCode(),
                poDetail.getQty()
        );
    }
    public OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO){
        return new OrderDetail(
                orderDetailDTO.getOrderId(),
                orderDetailDTO.getItemCode(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getDiscount(),
                orderDetailDTO.getUtilPrice()
        );
    }
    public OrderDetailDTO formOrderDetail(OrderDetail entity){
        return new OrderDetailDTO(
                entity.getOrderId(),
                entity.getItemCode(),
                entity.getQty(),
                entity.getDiscount(),
                entity.getUtilPrice()
        );
    }
    public Orders toOrders(OrdersDTO orders){
        return  new Orders(
                orders.getOrderId(),
                orders.getCashier(),
                orders.getDate(),
                orders.getTime(),
                orders.getCid(),
                orders.getOrderType(),
                orders.isStatus(),
                orders.getNetDiscount()
        );
    }
    public OrdersDTO formOrders(Orders orders){
        return  new OrdersDTO(
                orders.getOrderId(),
                orders.getCashier(),
                orders.getDate(),
                orders.getTime(),
                orders.getCId(),
                orders.getOrderType(),
                orders.isStatus(),
                orders.getNetDiscount()
        );
    }
    public PaymentDTO formPayment(Payment payment) {
        return new PaymentDTO(
                payment.getPayId(),
                payment.getCash(),
                payment.getCard(),
                payment.getCardIndex(),
                payment.getDate(),
                payment.getTime(),
                payment.getOrderId(),
                payment.getcId()

        );
    }
    public Payment toPayment(PaymentDTO payment) {
        return new Payment(
                payment.getPayId(),
                payment.getCash(),
                payment.getCard(),
                payment.getCardIndex(),
                payment.getDate(),
                payment.getTime(),
                payment.getOrderId(),
                payment.getcId()

        );
    }
    public Category toCategory(CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getCategoryId(),
                categoryDTO.getName(),
                categoryDTO.getParent(),
                categoryDTO.getRecode(),
                categoryDTO.isStatus(),
                categoryDTO.getDate()
        );
    }
    public CategoryDTO formCategory(Category category){
        return new CategoryDTO(
                category.getCategoryId(),
                category.getName(),
                category.getParent(),
                category.getRecode(),
                category.isStatus(),
                category.getDate()
        );
    }
    public CustomerDTO formCustomer(Customer customer){
        return new CustomerDTO(
                customer.getCid(),
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getSalary(),
                customer.getPhone(),
                customer.getDate()
        );
    }
    public Customer toCustomer(CustomerDTO customer){
        return new Customer(
                customer.getCid(),
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getSalary(),
                customer.getPhone(),
                customer.getDate()
        );
    }
    public ItemDTO formItem(Item item){
        return new ItemDTO(
                item.getItemCode(),
                item.getDescription(),
                item.getRemind(),
                item.getUtilPrice(),
                item.isStatus(),
                item.getCategoryId(),
                item.getDate(),
                item.getRecode(),
                item.getQty(),
                item.getDiscount()
        );
    }
    public Item toItem(ItemDTO item){
        return new Item(
                item.getItemCode(),
                item.getDescription(),
                item.getRemind(),
                item.getUtilPrice(),
                item.isStatus(),
                item.getCategoryId(),
                item.getDate(),
                item.getRecode(),
                item.getQty(),
                item.getDiscount()
        );
    }
    public  SupplierDTO formSupplier(Supplier supplier){
        return   new SupplierDTO(
                supplier.getSpId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getRecode(),
                supplier.isStatus()
        );
    }
    public  Supplier toSupplier(SupplierDTO supplier){
        return   new Supplier(
                supplier.getSpId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getRecode(),
                supplier.isStatus()
        );
    }

    public  SupplierItemDTO formSupplierItem(SupplierItem item){
        return  new SupplierItemDTO(
                item.getSpId(),
                item.getItemCode(),
                item.getCostPrice(),
                item.getRecode()
        );
    }

    public  SupplierItem toSupplierItem(SupplierItemDTO item){
        return  new SupplierItem(
                item.getSpId(),
                item.getItemCode(),
                item.getCostPrice(),
                item.getRecode()
        );
    }
    public User toUser(UserDTO user){
        return new User(
                user.getUserId(),
                user.getName(),
                user.getPwd(),
                user.getEmpId(),
                user.getRole(),
                user.isStatus()
        );
    }
    public UserDTO formUser(User user){
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getPwd(),
                user.getEmpId(),
                user.getRole(),
                user.isStatus()
        );
    }
    public Wastage toWastage(WastageDTO wastage){
        return new Wastage(
                wastage.getWastageId(),
                wastage.getArthur(),
                wastage.getReason(),
                wastage.getQty(),
                wastage.getCost(),
                wastage.getDate(),
                wastage.getTime(),
                wastage.getItemCode()
        );
    }
    public WastageDTO fromWastage(Wastage wastage){
        return new WastageDTO(
                wastage.getWastageId(),
                wastage.getArthur(),
                wastage.getReason(),
                wastage.getQty(),
                wastage.getCost(),
                wastage.getDate(),
                wastage.getTime(),
                wastage.getItemCode()
        );
    }
}
