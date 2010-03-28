package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  GetReleaseList.java
 * Author:  v
 * Purpose: Defines the Class GetReleaseList
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** @pdOid e7d1561e-eeb6-47f2-b6a5-f59aa8ce0510 */
public class GetReleaseList implements ICommand {

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        Map params = new HashMap();
        params.put("product_id", we.getProject());
        jc.prepareJSONRequest("getReleaseList", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        JSONArray releases = jResponce.getJSONArray("result");
        int code = Integer.parseInt(err.get("code").toString());
        if (code == 0) {
            return releases;
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }
}
