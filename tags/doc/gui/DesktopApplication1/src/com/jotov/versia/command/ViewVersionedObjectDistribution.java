package com.jotov.versia.command;

/***********************************************************************
 * Module:  ViewVersionedObjectDistribution.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class ViewVersionedObjectDistribution
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewVersionedObjectDistribution implements ICommand {

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        Map params = new HashMap();
        params.put("vo_id", we.getCurrentVersionedObjectID());
        params.put("release_id", we.getCurrentRelease());
        jc.prepareJSONRequest("viewVersionedObjectDistribution", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        
        //@todo - to check what to do with the result
        JSONArray Distribution = jResponce.getJSONArray("result");

        int code = err.getInt("code");
        if (code == 0) {
            return new Integer(1);
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap params) {
        return;
    }
}
