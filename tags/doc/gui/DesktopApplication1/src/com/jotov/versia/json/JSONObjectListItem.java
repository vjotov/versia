/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia.json;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author v
 */
public class JSONObjectListItem {
    private String displayingValue;
    private JSONObject JO;

    public void setDisplayingValue(String displayingValue) {
        this.displayingValue = displayingValue;
    }

    public String getDisplayingValue() {
        return displayingValue;
    }
    public JSONObjectListItem(JSONObject jo){
        JO = jo;
    }
    public int getInt(String key) throws JSONException{
        return JO.getInt(key);
    }
    public String getString(String key) throws JSONException{
        return JO.getString(key);
    }
    @Override
    public String toString() {
        try {
            return JO.getString(displayingValue);
        } catch (JSONException ex) {
            Logger.getLogger(JSONObjectListItem.class.getName()).log(Level.SEVERE, null, ex);
            return "-----Exception-----";
        }
    }
}
