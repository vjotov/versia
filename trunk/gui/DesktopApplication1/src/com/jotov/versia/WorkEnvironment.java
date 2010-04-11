package com.jotov.versia;

/***********************************************************************
 * Module:  WorkEnvironment.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class WorkEnvironment
 ***********************************************************************/
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WorkEnvironment {

    private int uid;
    private int project;
    private int release;
    private int workspace;
    private int currentProject;
    private int currentRelease;
    private int currentWs;
    private JSONArray jaWorkspace;
    private JSONArray jaProject;
    private JSONArray jaRrelease;
    private JSONArray jaAttachedWorkItems;
    private JSONArray jaAvailableWorkItems;
    private JSONArray jaVersionedObject;
    private JSONObject currentVersionedObject;
    static private WorkEnvironment singleton;

    private WorkEnvironment() {
        uid = 1;
        project = 0;
        release = 0;
        workspace = 0;
        currentProject = 0;
        currentRelease = 0;
        currentWs = 0;
        jaWorkspace = new JSONArray();
        jaProject = new JSONArray();
        jaRrelease = new JSONArray();
        jaAttachedWorkItems = new JSONArray();
        jaAvailableWorkItems = new JSONArray();
        jaVersionedObject = new JSONArray();
        currentVersionedObject = new JSONObject();
    }

    public static WorkEnvironment getWorkEnvironment() {
        if (singleton == null) {
            singleton = new WorkEnvironment();
        }
        return singleton;
    }

    public void openEnvironment() {
        currentProject = project;
        currentRelease = release;
        currentWs = workspace;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int newProject) {
        project = newProject;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int newRelease) {
        release = newRelease;
    }

    public JSONArray getWorkspace_ls() {
        return jaWorkspace;
    }

    public void setWorkspace_ls(JSONArray newWorkspaces) {
        jaWorkspace = newWorkspaces;
    }

    public int getCurrentWs() {
        return currentWs;
    }

    public void setCurrentWs(int newCurrentWs) {
        currentWs = newCurrentWs;
    }

    public JSONArray getVersionedObject_ls() {
        return jaVersionedObject;
    }

    public void setVersionedObject_ls(JSONArray newVersionedObject_ls) {
        jaVersionedObject = newVersionedObject_ls;
    }

    public JSONArray getAttachedWorkItems() {
        return jaAttachedWorkItems;
    }

    public void setAttachedWorkItems(JSONArray attachedWorkItems) {
        this.jaAttachedWorkItems = attachedWorkItems;
    }

    public JSONArray getAvailableWorkItems() {
        return jaAvailableWorkItems;
    }

    public void setAvailableWorkItems(JSONArray availableWorkItems) {
        this.jaAvailableWorkItems = availableWorkItems;
    }

    public JSONArray getProject_ls() {
        return jaProject;
    }

    public void setProject_ls(JSONArray project_ls) {
        this.jaProject = project_ls;
    }

    public JSONArray getRelease_ls() {
        return jaRrelease;
    }

    public void setRelease_ls(JSONArray release_ls) {
        this.jaRrelease = release_ls;
    }

    public JSONObject getCurrentVersionedObject() {
        return currentVersionedObject;
    }

    public void setCurrentVersionedObject(int currentVOID) throws JSONException {
        int len = jaVersionedObject.length();
        for (int i = 0; i < len; i++) {
            JSONObject tmpVO = jaVersionedObject.getJSONObject(i);
            if (currentVOID == tmpVO.getInt("vo_id")) {
                currentVersionedObject = tmpVO;
                return;
            }
        }
        currentVersionedObject = null;
    }

    public int getCurrentVersionedObjectID() throws JSONException {
        return currentVersionedObject.getInt("vo_id");
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getWorkspace() {
        return workspace;
    }

    public void setWorkspace(int workspace) {
        this.workspace = workspace;
    }

    public int getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(int currentProject) {
        this.currentProject = currentProject;
    }

    public int getCurrentRelease() {
        return currentRelease;
    }

    public void setCurrentRelease(int currentRelease) {
        this.currentRelease = currentRelease;
    }

    public boolean isLocalCurrentVersionedObject() {
        try {
            int vector = currentVersionedObject.getInt("v_vector");
            if ((vector & 8) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException ex) {
            return false;
        }
    }

    public boolean isPutBackableCurrentVersionedObject() {
        try {
            int vector = currentVersionedObject.getInt("v_vector");
            if ((vector & 11) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException ex) {
            return false;
        }
    }

    public String getVisibilityVectorString(int vector) {
        StringBuffer sb = new StringBuffer();
        if ((vector & 1) > 0) {
            sb.append("R");
        } else {
            sb.append("_");
        }
        if ((vector & 2) > 0) {
            sb.append("P");
        } else {
            sb.append("_");
        }
        if ((vector & 4) > 0) {
            sb.append("C");
        } else {
            sb.append("_");
        }
        if ((vector & 8) > 0) {
            sb.append("L");
        } else {
            sb.append("_");
        }
        if ((vector & 16) > 0) {
            sb.append("O");
        } else {
            sb.append("_");
        }
        return sb.toString();
    }
}
