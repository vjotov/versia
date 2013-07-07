/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author v
 */
public class VOHistoryTableModel extends AbstractTableModel {
    private JSONArray head;
    private JSONArray contentData;

    public int getRowCount() {
        return contentData.length();
    }

    @Override
    public String getColumnName(int column) {
        try {
            return head.getString(column);
        } catch(Exception ex){
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public int getColumnCount() {
        try {
            JSONObject ja = contentData.getJSONObject(1);
            return ja.length();
        } catch (JSONException ex) {
            Logger.getLogger(VOHistoryTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            String key = head.getString(columnIndex);
            JSONObject jo = contentData.getJSONObject(rowIndex);
            return jo.get(key);
            //return ja.get(columnIndex);
        } catch (JSONException ex) {
            Logger.getLogger(VOHistoryTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public JSONArray getContentData() {
        return contentData;
    }

    public JSONArray getHead() {
        return head;
    }

    public void setContentData(JSONArray contentData) {
        this.contentData = contentData;
    }

    public void setHead(JSONArray head) {
        this.head = head;
    }

}
