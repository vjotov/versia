package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  AttachWorkItem.java
 * Author:  v
 * Purpose: Defines the Class AttachWorkItem
 ***********************************************************************/

import com.jotov.versia.json.JSONConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** @pdOid caaedf86-093d-4cc7-9659-1ae47f58da4b */
public class AttachWorkItem extends ACommand {

    @Override
    public Object doRequest() {
        /*try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            JSONArray notAttached = workEnvironment.getAvailableWorkItems();
            JSONObject wi = (JSONObject) notAttached.get(jlstNotAttachedWorkitems.getSelectedIndex());
            params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", workEnvironment.getCurrentWs());
            jc.prepareJSONRequest("attachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(AttachWorkItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Object(); //*/
        return null;
    }
}