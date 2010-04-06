package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  GetVersionedObjectList.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class GetVersionedObjectList
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetVersionedObjectList implements ICommand {

    public Object doRequest() throws JSONException {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            int uid = we.getUid();
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ws_id", we.getCurrentWs());
            jc.prepareJSONRequest("getVisibleVersionedObjectList", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            JSONArray vo_ls = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code == 0) {
                we.setVersionedObject_ls(vo_ls);
                return vo_ls;
            } else {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
                return null;
            }
    }

    public void setParameters(HashMap params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
