/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia;

/**
 *
 * @author v
 */
public class worspaceInfo {
    private String name;   
    private int ws_id;
    private int place_in_array;

    public worspaceInfo(String name, int ws_id, int place_in_array) {
        this.name = name;
        this.ws_id = ws_id;
        this.place_in_array = place_in_array;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlace_in_array() {
        return place_in_array;
    }

    public void setPlace_in_array(int place_in_array) {
        this.place_in_array = place_in_array;
    }

    public int getWs_id() {
        return ws_id;
    }

    public void setWs_id(int ws_id) {
        this.ws_id = ws_id;
    }
    @Override
    public String toString() {
        return name;
    }
}
