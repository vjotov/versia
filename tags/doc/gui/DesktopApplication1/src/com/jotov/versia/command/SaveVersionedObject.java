package com.jotov.versia.command;

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
        if(! params.containsKey("vo_id")) params.put("vo_id", we.getCurrentVersionedObjectID());
        if(! params.containsKey("ws_id")) params.put("ws_id", we.getCurrentWs());
        if(! params.containsKey("type")) params.put("type", 1);
        if(! params.containsKey("constructs")) params.put("constructs", we.getCurrentVersionedObjectConstructs());
        params.put("uid", uid);

        jc.prepareJSONRequest("saveVersionedObjectState", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");      
        int code = err.getInt("code");
        if (code == 0) {
            we.setVersionedObject_ls(jResponce.getJSONArray("result"));
            return new Integer(1);
        } else {
            System.err.println("JSON ERROR SaveVersionedObject - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
}
