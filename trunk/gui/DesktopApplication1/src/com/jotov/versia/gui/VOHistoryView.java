/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OpenWorkspace.java
 *
 * Created on 2009-12-31, 10:39:48
 */
package com.jotov.versia.gui;

import com.jotov.versia.VOHistoryTableModel;
import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.gui2.command.CommandFactory;
import com.jotov.versia.gui2.command.ICommand;
import desktopapplication1.DesktopApplication1View;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author v
 */
public class VOHistoryView extends javax.swing.JDialog {

    private VOHistoryTableModel VOHModel;
    private WorkEnvironment workEnvironment;
    private String actionCommand;

    /** Creates new form OpenWorkspace */
    public VOHistoryView(java.awt.Frame parent) {
        super(parent, true);
        workEnvironment = WorkEnvironment.getWorkEnvironment();
        VOHModel = new VOHistoryTableModel();
        loadVOHistoryData();

        initComponents();
        
        getRootPane().setDefaultButton(jbOK);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbOK = new javax.swing.JButton();
        jspVOHistory = new javax.swing.JScrollPane();
        jtblVOHistory = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getActionMap(VOHistoryView.class, this);
        jbOK.setAction(actionMap.get("doOK")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(VOHistoryView.class);
        jbOK.setText(resourceMap.getString("jbOK.text")); // NOI18N
        jbOK.setActionCommand(resourceMap.getString("jbOK.actionCommand")); // NOI18N
        jbOK.setName("jbOK"); // NOI18N

        jspVOHistory.setName("jspVOHistory"); // NOI18N

        jtblVOHistory.setModel(VOHModel);
        jtblVOHistory.setName("jtblVOHistory"); // NOI18N
        jspVOHistory.setViewportView(jtblVOHistory);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jbOK)
                    .add(jspVOHistory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 396, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jspVOHistory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbOK)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        loadVOHistoryData();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbOK;
    private javax.swing.JScrollPane jspVOHistory;
    private javax.swing.JTable jtblVOHistory;
    // End of variables declaration//GEN-END:variables

    @Action
    public void doOK() {
        actionCommand = jbOK.getActionCommand();
        dispose();
    }

    public String getActionCommand() {
        return actionCommand;
    }

    private void loadVOHistoryData() {
        try {
            //HashMap params = new HashMap();
            //params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());

            CommandFactory cf = new CommandFactory();
            ICommand cmd = cf.createCommand(CommandFactory.CmdCode.VIEW_VERSIONED_OBJECT_HISTORY);
            //cmd.setParameters(params);
            JSONObject ResultItems = (JSONObject) cmd.doRequest();
            if (Object.class.isInstance(ResultItems)) {
                bindHistoryModel(ResultItems);
            }
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(this,
                    "Communication Problem.",
                    "Error during history load",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bindHistoryModel(JSONObject Items) {
        try {

            VOHModel.setContentData(Items.getJSONArray("changes"));
            VOHModel.setHead(Items.getJSONArray("head"));
            
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(this,
                    "Problem.",
                    "Error during history display",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
