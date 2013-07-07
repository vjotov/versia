/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia.json;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.json.JSONArray;


/**
 *
 * @author v
 */
public class JSONObjectListModel implements ListModel {
    JSONArray arr;
    public int getSize() {
        return arr.length();
    }

    public Object getElementAt(int i) {
        try {
            return arr.get(i);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    public void addListDataListener(ListDataListener ll) {
        throw new UnsupportedOperationException("Not supported yet. 1");
    }

    public void removeListDataListener(ListDataListener ll) {
        throw new UnsupportedOperationException("Not supported yet. 2");
    }

}
