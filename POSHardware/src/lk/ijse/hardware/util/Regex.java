package lk.ijse.hardware.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextFields textField,String text){
        String field="";
        switch (textField){
            case ID:
                field="^([A-Z0-9])$";
                break;
            case LANKAN_ID:
                field="^([0-9]){5,10}([V|v]){0,1}$";
                 break;
            case NAME:
                field="^[A-z|\\s]{3,}$";
                break;
            case EMAIL:
                field="^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ADDRESS:
                field="^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case PHONE:
                field="^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case DOUBLE:
                field="^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case INTEGER:
                field="^([0-9]){1,}$";
                break;
            case NONE_CHARACTER:
                field="^[\\W]{1,}$";
                break;
            case INVOICE:
                field="^([A-z 0-9]){1,5}([0-9/_@$+,-]){1,}$";
                break;
            case INTEGER_DECIMAL:
                field="^[0-9]*['.']?[0-9]*$";
                break;
            case EMP_ID:
                field="^([0-9A-z]){2,30}$";
                break;
            case PWD:
                field="^([0-9A-z @_]){4,20}$";
                break;

        }

        Pattern pattern=Pattern.compile(field);
        if(text !=null){
        if(text.trim().isEmpty()){
            return false;
        }
        }else{
            return false;
        }
        Matcher matcher= pattern.matcher(text);

        if(matcher.matches()){
            return true;
        }
       return false;
    }public static boolean isValidId(Route route,String field){
        String value="";
        switch(route){
            case SUPPLIER:
                if(field.startsWith("SP")){
                  value="SP";
                }
                break;
            case CUSTOMER:
                if(field.startsWith("C")){
                value="C";
            }
                break;
        }
        Pattern pattern=Pattern.compile("^"+value+"{1,2}[0-9]{1,}$");
        Matcher matcher= pattern.matcher(value);
        if(matcher.matches()){
            return true;
        }
            return false;
    }public static boolean setTextColor(TextFields location, JFXTextField fields){

        if(Regex.isTextFieldValid(location,fields.getText())){
            fields.setFocusColor(Paint.valueOf("Green"));
                return true;
        }else {
            fields.setFocusColor(Paint.valueOf("Red"));
                return false;
        }
    }
}
