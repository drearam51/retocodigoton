/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author alramirez
 */
public class NetClientGet {
    public String url;
        public String encrypted;
    
    //public static void main(String[] args) {
    public NetClientGet(String url, String encrypted){
        this.url = url;
        this.encrypted = encrypted;
    }
    
    public String getUrl() {
        return url;
    }

    public String getEncrypted() {
        return encrypted;
    }
    
    public String getCode() throws MalformedURLException, IOException{
            
//            this.url = "https://test.evalartapp.com/extapiquest/code_decrypt/"+encrypted;
            String newUrl = this.url + this.encrypted;
//            System.out.println("newUrl=>"+newUrl);
            URL url = new URL(newUrl);
//            URL url = new URL( + encrypted);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String code = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {                
                code = output;
            }
            conn.disconnect();
            return code;
    }
}