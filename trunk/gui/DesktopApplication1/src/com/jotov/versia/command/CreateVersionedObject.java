package com.jotov.versia.command;

/***********************************************************************
 * Module:  CreateVersionedObject.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class CreateVersionedObject
 ***********************************************************************/

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

/** @pdOid 067e32d2-11cb-4528-826e-27b3c4ff6f85 */
public class CreateVersionedObject implements ICommand {
    private HashMap params = new HashMap();
    
    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        params.put("ws_id", we.getCurrentWs());
        params.put("uid", uid);
        params.put("type", 1);
        params.put("constructs", 0);
        jc.prepareJSONRequest("createVersionedObject", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        we.setVersionedObject_ls(jResponce.getJSONArray("result"));
        int code = err.getInt("code");
        if (code ==0) {
            return new Integer(1);
        } else {
            System.err.println("JSON ERROR CreateVersionedObject - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
}