package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  SaveVersionedObject.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class SaveVersionedObject
 ***********************************************************************/
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class SaveVersionedObject implements ICommand {

    private HashMap params = new HashMap();

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();

        JSONConnection jc = new JSONConnection();
        params.put("vo_id", we.getCurrentVersionedObjectID());
        params.put("ws_id", we.getCurrentWs());
        params.put("uid", uid);
        params.put("type", 1);
        //@todo - to check whether constructs is obligatory parameter
        //        there is an action change VO's hat!
        //params.put("constructs", 0);

        jc.prepareJSONRequest("saveVersionedObjectState", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        we.setVersionedObject_ls(jResponce.getJSONArray("result"));
        int code = err.getInt("code");
        if (code ==0) {
            return new Integer(1);
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
}
