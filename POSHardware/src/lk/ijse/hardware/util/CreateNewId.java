package lk.ijse.hardware.util;

public class CreateNewId {
    public static String generateId(String startName,String id){
        if(id !=null){
            String[] newId=id.split(startName);
            int addId =Integer.parseInt(newId[1]);
            addId++;
            return String.format(startName+"%05d",addId);
        }

        return startName+"00000";

    }
}
