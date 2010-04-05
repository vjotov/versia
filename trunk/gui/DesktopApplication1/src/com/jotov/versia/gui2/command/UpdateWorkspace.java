package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  UpdateWorkspace.java
 * Author:  v
 * Purpose: Defines the Class UpdateWorkspace
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateWorkspace implements ICommand {

    private HashMap params = new HashMap();
    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        params.put("workspace_id", we.getWorkspace());
        jc.prepareJSONRequest("updateWorkspace", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        int code = err.getInt("code");
        if (code ==0) {
            return new Integer(0);
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }

    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
    public UpdateWorkspace() {
        params = new HashMap();
    }
}
