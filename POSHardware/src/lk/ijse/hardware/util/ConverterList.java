package lk.ijse.hardware.util;

import lk.ijse.hardware.dto.*;

import lk.ijse.hardware.view.tm.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterList {
       public  List<CategoryTM> formCategoryTMList(List<CategoryDTO> list){
            return list.stream().map(c-> new CategoryTM(
                    c.getCategoryId(),
                    c.getName(),
                    c.getParent(),
                    c.getRecode(),
                    c.isStatus(),
                    c.getDate().toLocalDate())).collect(Collectors.toList());
        }
       public  List<CategoryDTO> toCategoryDTOList(List<CategoryTM> list){
            return list.stream().map(c-> new CategoryDTO(
                    c.getId(),
                    c.getName(),
                    c.getParent(),
                    c.getTotalProduct(),
                    c.isDisplayOnPos(),
                    Date.valueOf(c.getCreatedAt()))).collect(Collectors.toList());
        }
        public  List<ProductTM> formProductTMList(List<ItemDTO> list){
           return list.stream().map(itm -> new ProductTM(
                   itm.getItemCode(),
                   itm.getDescription(),
                   itm.getCategoryId(),
                   itm.getRemind(),
                   itm.getUtilPrice(),
                   itm.isStatus(),
                   itm.getDate().toLocalDate(),
                   itm.getRecode(),
                   itm.getQty()
           )).collect(Collectors.toList());
        }
    public  List<ItemDTO> toItemDTOList(List<ProductTM> list){
        return list.stream().map(itm -> new ItemDTO(
                itm.getItemCode(),
                itm.getDescription(),
                itm.getRemind(),
                itm.getUtilPrice(),
                itm.isStatus(),
                itm.getCategoryId(),
                Date.valueOf(itm.getDate()),
                itm.getRecode(),
                itm.getQty(),
                itm.getDiscount()
        )).collect(Collectors.toList());
    }
    public  List<CustomerTM> formCustomerTMList(List<CustomerDTO> list){
           return list.stream().map(c ->new CustomerTM(
                   c.getCid(),
                   c.getCustomerId(),
                   c.getName(),
                   c.getEmail(),
                   c.getAddress(),
                   c.getSalary(),
                   c.getPhone(),
                   c.getDate().toLocalDate()
           )).collect(Collectors.toList());
    }
    public  List<CustomerDTO> toCustomerDTOList(List<CustomerTM> list){
        return list.stream().map(c ->new CustomerDTO(
                c.getId(),
                c.getCustomerId(),
                c.getName(),
                c.getEmail(),
                c.getAddress(),
                c.getSalary(),
                c.getPhone(),
                Date.valueOf(c.getDate())
        )).collect(Collectors.toList());
    }
    public  List<OrdersTM> formOrderTMList(List<OrdersDTO> list){
           return list.stream().map(l ->
                   new OrdersTM(
                           l.getOrderId(),
                           l.getCashier(),
                           l.getDate().toLocalDate(),
                           l.getTime().toLocalTime(),
                           l.getCid(),
                           l.getOrderType(),
                           l.isStatus(),
                           l.getNetDiscount()
                   )
                   ).collect(Collectors.toList());
    }
    public  List<PoTM> formPoTMList(List<PoDTO> list){
           return list.stream().map(po ->
                   new PoTM(
                      po.getPoId(),
                      po.getArthur(),
                      po.getDate().toLocalDate(),
                           po.getTime().toLocalTime(),
                           po.getSpId(),
                           po.getRequestDate().toLocalDate()
                   )
                   ).collect(Collectors.toList());
    }
    public  List<SupplierTM> formSupplierTMList(List<SupplierDTO> list){
           return list.stream().map(l ->
                   new SupplierTM(
                        l.getSpId(),
                        l.getName(),
                        l.getEmail(),
                        l.getAddress(),
                        l.getPhone(),
                        l.getRecode(),
                        l.isStatus()
                   )
                   ).collect(Collectors.toList());
    }
    public  List<SupplierListTM> formSupplierListTMList(List<SupplierItemDTO> list){
           return list.stream().map(l ->
                   new SupplierListTM(
                       l.getSpId(),
                       l.getItemCode(),
                       l.getCostPrice(),
                       l.getRecode()
                   )
                   ).collect(Collectors.toList());
    }
}
