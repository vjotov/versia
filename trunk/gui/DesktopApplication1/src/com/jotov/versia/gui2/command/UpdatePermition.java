package com.jotov.versia.gui2.command;

/***********************************************************************
 * Module:  UpdateProduct.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class UpdateProduct
 ***********************************************************************/

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.json.JSONConnection;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePermition implements ICommand {

    private HashMap params = new HashMap();

    public Object doRequest() throws JSONException {
        WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
        int uid = we.getUid();
        JSONConnection jc = new JSONConnection();

        jc.prepareJSONRequest("updateUserPerminiton", params, uid);
        JSONObject jResponce = jc.doRequest(null);
        JSONObject err = jResponce.getJSONObject("error");
        int code = err.getInt("code");
        if (code == 0) {
            return new Integer(0);
        } else {
            System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            return null;
        }
    }

    /**
     * @param params the params to set
     */
    public void setParameters(HashMap params) {
        this.params = params;
    }
}