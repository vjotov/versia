/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia;

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author v
 */
public class EventManager {
    static private HashMap<String, Vector> eventMap;

    static public void registerEvent(String evt) {
        if(! eventMap.containsKey(evt)){
            Vector v = new Vector();
            eventMap.put(evt, v);
        }
    }
    static public void addListener(String evt, EventListener listener) {
        if(eventMap.containsKey(evt)){
            Vector v = eventMap.get(evt);
            v.add(listener);
        }
    }
    static public void removeListener(String evt, EventListener listener) {
        if(eventMap.containsKey(evt)){
            Vector v = eventMap.get(evt);
            v.remove(listener);
        }
    }
    static public void fireEvent(String evt){
        if(eventMap.containsKey(evt)){
            Vector v = eventMap.get(evt);
            Iterator i = v.iterator();
            while(i.hasNext()){
                EventListener listener = (EventListener) i.next();
                listener.processEvent();
            }
        }
    }
}
