package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  DetachWorkItem.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class DetachWorkItem
 ***********************************************************************/

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/** @pdOid 4f4dd3f5-aecc-439b-9135-05e51af4fa98 */
public class DetachWorkItem implements ICommand {
HashMap params = new HashMap();
    public Object doRequest() {
        try {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            int uid = we.getUid();

            JSONConnection jc = new JSONConnection();
            //Map params = new HashMap();
            //JSONArray notAttached = we.getAvailableWorkItems();
            //JSONObject wi = (JSONObject) notAttached.get(jlstNotAttachedWorkitems.getSelectedIndex());
            //params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", we.getCurrentWs());
            jc.prepareJSONRequest("detachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            we.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(AttachWorkItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Integer(1); //*/
    }

    public void setParameters(HashMap inParams) {
        params = inParams;
    }
}