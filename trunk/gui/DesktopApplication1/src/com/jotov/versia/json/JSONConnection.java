/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import javax.swing.JTextArea;
import org.json.JSONObject;

/**
 *
 * @author v
 */
public class JSONConnection {

    public static String repositoryURL;
    private JSONObject jsonRequest = new JSONObject();

    
    public JSONObject doRequest(JTextArea jta) {
        JSONObject jo;// = new JSONObject();
        try{
            String strRequest = jsonRequest.toString();
            if(jta != null) jta.append("<< "+strRequest+"\n");

            URL srvURL = new URL(repositoryURL+"json_controller.php?json="+URLEncoder.encode(strRequest, "UTF-8"));
            URLConnection conection = srvURL.openConnection();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));

            StringBuffer strBuff = new StringBuffer();
            String strTmp;
            while((strTmp = in.readLine()) != null) {
                strBuff.append(strTmp);
                if(jta != null) jta.append(">> "+strTmp+"\n");
            }
            String strResponce = strBuff.toString();

            jo = new JSONObject(strResponce);
            in.close();
        } catch(Exception ex) {
            jo = new JSONObject();
            ex.printStackTrace(System.err);
        }
        return jo;
    }
    public void prepareJSONRequest(String method, Map parameters, int uid){
        try{
            jsonRequest.put("method", method);
            jsonRequest.put("params", parameters);
            jsonRequest.put("id", uid);
        } catch(Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
