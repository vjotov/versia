package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  GetWorkspaceList.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class GetWorkspaceList
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GetWorkItemList implements ICommand {

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        Map params = new HashMap();
        params.put("ws_id", we.getCurrentWs());
        jc.prepareJSONRequest("getWorkItemList", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        JSONObject workitems = jResponce.getJSONObject("result");
        int code = err.getInt("code");
        if (code == 0) {
            we.setAttachedWorkItems(workitems.getJSONArray("attached"));
            we.setAvailableWorkItems(workitems.getJSONArray("not_attached"));
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
