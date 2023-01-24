package lk.ijse.hardware.dao.util;

public class CreateNewID {
    public static String generateID(String startID, String id){
        if(id !=null){
            final String[] split = id.split(startID);
            int addID=Integer.parseInt(split[1])+1;
            return String.format(startID+"%05d",addID);
        }else{
            return startID+"00000";
        }
    }
}
