package Ecometrica.econ;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author nickpsal
 */
public class restApi {
    private String code;
    private String key;
    private String responseString;
    
    public restApi(String code,String key) {
        this.code = code;
        this.key=key;
    }
    //Παίρνουμε τα δεδομένα JSON απο την σελίδα
    public String getDataGDP(String urlGDP) {
        String url2 = "_NY_GDP_MKTP_CN.json?api_key=";
        String url = urlGDP+code+url2+key;        
        responseString = getData(url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                responseString = response.body().string();
                return responseString;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    //Παίρνουμε τα δεδομένα JSON απο την σελίδα
    public String getDataBP(String urlBP) {
        String url2 = ".json?api_key=";
        String urlOIL = urlBP+code+url2+key;       
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlOIL).build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                responseString = response.body().string();
                return responseString;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    // ΈΛεγχος αν είναι σωστά τα λινκ
    public String getData(String urlGDP) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlGDP).build();
        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                responseString = response.body().string();
                return responseString;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
