package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  GetProducts.java
 * Author:  v
 * Purpose: Defines the Class GetProducts
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** @pdOid 127819af-948d-4ff1-92cb-f9e4e7b11c0e */
public class GetProducts implements ICommand {

    public Object doRequest() throws JSONException {

        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();
        
        JSONConnection jc = new JSONConnection();
        Map params = new HashMap();
        jc.prepareJSONRequest("getProductList", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        JSONArray projects = jResponce.getJSONArray("result");        
        int code = Integer.parseInt(err.get("code").toString());
        if (code == 0) {
            return projects;
        } else {
            System.err.println("JSON ERROR - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }
}
