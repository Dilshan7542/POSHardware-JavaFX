package lk.ijse.hardware.util;

import lk.ijse.hardware.dto.*;
import lk.ijse.hardware.entity.OrderDetail;
import lk.ijse.hardware.entity.SupplierItem;
import lk.ijse.hardware.view.tm.*;

public class Converter {
    public  CustomerTM formCustomerTM(CustomerDTO customerDTO){
        return new CustomerTM(
                customerDTO.getCid(),
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getAddress(),
                customerDTO.getSalary(),
                customerDTO.getPhone(),
                customerDTO.getDate().toLocalDate()
        );
    }
    public  ProductTM formProductTM(ItemDTO itemDTO){
        return new ProductTM(
                itemDTO.getItemCode(),
                itemDTO.getDescription(),
                itemDTO.getCategoryId(),
                itemDTO.getRemind(),
                itemDTO.getUtilPrice(),
                itemDTO.isStatus(),
                itemDTO.getDate().toLocalDate(),
                itemDTO.getRecode(),
                itemDTO.getQty()

        );
    }
    public  PaymentTM formPaymentTM(PaymentDTO paymentDTO){
        return new PaymentTM(
                paymentDTO.getPayId(),
                paymentDTO.getCash(),
                paymentDTO.getCard(),
                paymentDTO.getCardIndex(),
                paymentDTO.getDate().toLocalDate(),
                paymentDTO.getTime().toLocalTime(),
                paymentDTO.getOrderId(),
                paymentDTO.getcId()

        );
    }
    public  PoTM formPoTM(PoDTO poDTO){
      return   new PoTM(
                poDTO.getPoId(),
                poDTO.getArthur(),
                poDTO.getDate().toLocalDate(),
                poDTO.getTime().toLocalTime(),
                poDTO.getSpId(),
                poDTO.getRequestDate().toLocalDate()
        );
    }
    public  PoListTM formPoListTM(PoDetailDTO poDetailDTO){
        return new PoListTM(
                poDetailDTO.getPoId(),
                poDetailDTO.getItemCode(),
                poDetailDTO.getQty()
        );
    }
    public  SupplierTM formSupplierTM(SupplierDTO supplierDTO){
      return   new SupplierTM(
                supplierDTO.getSpId(),
                supplierDTO.getName(),
                supplierDTO.getEmail(),
                supplierDTO.getAddress(),
                supplierDTO.getPhone(),
                supplierDTO.getRecode(),
                supplierDTO.isStatus()
        );
    }

    public  SupplierListTM formSupplierListTM(SupplierItemDTO item){
        return  new SupplierListTM(
                item.getSpId(),
                item.getItemCode(),
                item.getCostPrice(),
                item.getRecode()
        );
    }
    public SupplierItemDTO toSupplierListTM(SupplierListTM item){
        return  new SupplierItemDTO(
                item.getSpId(),
                item.getItemCode(),
                item.getCostPrice(),
                item.getRecode()
        );
    }
    public  GrnTM formGrnTM(GrnDTO grnDTO){
        return new GrnTM(
               grnDTO.getGrnId(),
               grnDTO.getArthur(),
               grnDTO.getInvoice(),
               grnDTO.getDate().toLocalDate(),
                grnDTO.getTime().toLocalTime(),
                grnDTO.getPoId(),
                grnDTO.getSpId()
        );
    }
    public  GrnDetailTM formGrnDetailTM(GrnDetailDTO grnDetailDTO) {
        return new GrnDetailTM(
                grnDetailDTO.getGrnId(),
                grnDetailDTO.getCostPrice(),
                grnDetailDTO.getQty(),
                grnDetailDTO.getItemCode()
        );
    }
    public OrderDetailTM formOrderDetailTM(OrderDetailDTO orderDetailDTO){
        return new OrderDetailTM(
                orderDetailDTO.getOrderId(),
                orderDetailDTO.getItemCode(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getDiscount(),
                orderDetailDTO.getUtilPrice()
        );
    }
    public OrderDetailDTO toOrderDetail(OrderDetailTM orderDetailTM){
        return new OrderDetailDTO(
                orderDetailTM.getOrderId(),
                orderDetailTM.getItemCode(),
                orderDetailTM.getQty(),
                orderDetailTM.getDiscount(),
                orderDetailTM.getUtilPrice()
        );
    }
    public OrdersTM formOrderTM(OrdersDTO ordersDTO){
        return  new OrdersTM(
                ordersDTO.getOrderId(),
                ordersDTO.getCashier(),
                ordersDTO.getDate().toLocalDate(),
                ordersDTO.getTime().toLocalTime(),
                ordersDTO.getCid(),
                ordersDTO.getOrderType(),
                ordersDTO.isStatus(),
                ordersDTO.getNetDiscount()
        );
    }
}
