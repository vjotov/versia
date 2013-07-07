package com.jotov.versia.command;

/***********************************************************************
 * Module:  CreateWorkItem.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class CreateWorkItem
 ***********************************************************************/

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateWorkItem implements ICommand {
    private HashMap params = new HashMap();

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        params.put("vo_id", we.getCurrentVersionedObjectID());
        params.put("uid", uid);
        params.put("release_id", we.getCurrentRelease());
        jc.prepareJSONRequest("createWorkItem", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        we.setVersionedObject_ls(jResponce.getJSONArray("result"));
        int code = err.getInt("code");
        if (code ==0) {
            return new Integer(1);
        } else {
            System.err.println("JSON ERROR CreateWorkItem - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
}