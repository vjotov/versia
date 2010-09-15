package com.jotov.versia.command;

/***********************************************************************
 * Module:  GetWorkspaceList.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class GetWorkspaceList
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetWorkspaceList implements ICommand {

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        Map params = new HashMap();
        params.put("release_id", we.getRelease());
        jc.prepareJSONRequest("getReleaseWorkspaces", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        JSONArray workspace_ls = jResponce.getJSONArray("result");
        int code = err.getInt("code");
        if (code == 0) {
            return workspace_ls;
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap params) {
        return;
    }
}
