/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia.command;

import com.jotov.versia.WorkEnvironment;
import java.util.HashMap;

/***********************************************************************
 * Module:  ACommand.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Interface ACommand
 ***********************************************************************/


public class ACommand implements ICommand {
    private WorkEnvironment workEnvironment;
    public Object doRequest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setParameters(HashMap params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}