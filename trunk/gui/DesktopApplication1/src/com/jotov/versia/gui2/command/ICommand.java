package com.jotov.versia.gui2.command;

import org.json.JSONException;

/***********************************************************************
 * Module:  ICommand.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Interface ICommand
 ***********************************************************************/


public interface ICommand {
   Object doRequest() throws JSONException;

}