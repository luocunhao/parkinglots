package cn.xlink.parkinglots.client.utils;

import java.util.HashMap;
import java.util.Map;

public class Verify {

   public static Boolean VerifyProjectId(String project_id){
       Boolean verify_flage=false;
       String verify_project_id="http://52.83.132.163/v2/realty-master-data/projects/"+project_id;
       Map<String,String>headerMap=new HashMap<>();
       headerMap.put("Content-Type","application/json");
       headerMap.put("Access-token","RkQ5MTE5QTgyMzI0NDI1N0EzRjlENzdENkFDOUEyOUM2MDJDNjUzRUIxMkI3NTBBNzNBMDk0MzBGRDk1QzI0OQ==");
       HttpResponse result= Http.Get(verify_project_id,headerMap);
       if(result.getResponseCode()==200){
           verify_flage=true;
       }
       return verify_flage;
   }

}
