/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia;

/**
 *
 * @author v
 */
public class workitemInfo {
    private String name;   
    private int wi_id;
    private int place_in_array;

    public workitemInfo(String name, int wi_id, int place_in_array) {
        this.name = name;
        this.wi_id = wi_id;
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

    public int getVo_id() {
        return wi_id;
    }

    public void setVo_id(int vo_id) {
        this.wi_id = vo_id;
    }
    @Override
    public String toString() {
        return name;
    }
}
