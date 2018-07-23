package cn.xlink.parkinglots.api.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class HttpClientUtil {

    public String doPost(HttpPost httpPost, Map<String,String> map, String charset){
        HttpClient httpClient = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            List<NameValuePair> list = new ArrayList<>();
            for(String key:map.keySet()){
                list.add(new BasicNameValuePair(key,map.get(key)));
            }
            if(list.size()>0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response!=null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity!=null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
    public String doGet(HttpGet httpGet, String charset){
        if(null == charset){
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null){
                    result = EntityUtils.toString(httpEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
