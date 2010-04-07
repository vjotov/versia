/*
 * DesktopApplication1View.java
 */
package desktopapplication1;

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.gui.VersiaAboutBox;
import com.jotov.versia.json.JSONConnection;
import com.jotov.versia.gui.OpenWorkspace;
import com.jotov.versia.gui2.command.CommandFactory;
import com.jotov.versia.gui2.command.ICommand;
import com.jotov.versia.voInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Variables declaration - do not modify
/***********************************************************************
 * Module:  DesktopApplication1View.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class DesktopApplication1View
 ***********************************************************************/
public class DesktopApplication1View extends FrameView {

    private WorkEnvironment workEnvironment;
    //private int uid = 1;
    DefaultMutableTreeNode voRoot, wiAttachedRoot, wiAvailableRoot;
    DefaultTreeModel voModel, wiAttachedModel, wiAvailableModel;

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        workEnvironment = WorkEnvironment.getWorkEnvironment();

        voRoot = new DefaultMutableTreeNode();
        wiAttachedRoot = new DefaultMutableTreeNode();
        wiAvailableRoot = new DefaultMutableTreeNode();
        voModel = new DefaultTreeModel(voRoot);
        wiAttachedModel = new DefaultTreeModel(wiAttachedRoot);
        wiAvailableModel = new DefaultTreeModel(wiAvailableRoot);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        /*ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);//*/

        // connecting action tasks to status bar via TaskMonitor
        //TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        /*taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        //statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    //progressBar.setVisible(true);
                    //progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    //statusAnimationLabel.setIcon(idleIcon);
                    //progressBar.setVisible(false);
                    //progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    //String text = (String) (evt.getNewValue());
                    //statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    //int value = (Integer) (evt.getNewValue());
                    //progressBar.setVisible(true);
                    //progressBar.setIndeterminate(false);
                    //progressBar.setValue(value);
                }
            }
        });//*/
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            aboutBox = new VersiaAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(aboutBox);
    }

    @Action
    public void showOpenProduct() {
        if (openProduct == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            openProduct = new OpenWorkspace(mainFrame);
            openProduct.setLocationRelativeTo(mainFrame);
            openProduct.setTitle("Open Product&Release");
        }
        DesktopApplication1.getApplication().show(openProduct);
        if (openProduct.getActionCommand().equals("BTN_OK")) {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            we.setCurrentProject(we.getProject());
            we.setCurrentRelease(we.getRelease());
            we.setCurrentWs(we.getWorkspace());
            loadVersionedObjects();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jpWorkspaces = new javax.swing.JPanel();
        jpWorkItems = new javax.swing.JPanel();
        jlAttachedWorkitems = new javax.swing.JLabel();
        jspAttachedWorkItems = new javax.swing.JScrollPane();
        jtAttachedWorkItems = new javax.swing.JTree();
        jbAttachWI = new javax.swing.JButton();
        jbDetachWI = new javax.swing.JButton();
        jbCreateWI = new javax.swing.JButton();
        jlAvailableWorkitems = new javax.swing.JLabel();
        jspAvailableWorkItems = new javax.swing.JScrollPane();
        jtAvailableWorkItems = new javax.swing.JTree();
        jpCommands = new javax.swing.JPanel();
        jbCreateVO = new javax.swing.JButton();
        jbSaveVO = new javax.swing.JButton();
        jbChangeVOHat = new javax.swing.JButton();
        jbPublishVO = new javax.swing.JButton();
        jbPutBackVO = new javax.swing.JButton();
        jbViewVOHistory = new javax.swing.JButton();
        jbViewVODistribution = new javax.swing.JButton();
        jpVersionedObjects = new javax.swing.JPanel();
        jlVersionedObjects = new javax.swing.JLabel();
        jspVersionedObjects = new javax.swing.JScrollPane();
        jtVOs = new javax.swing.JTree();
        jlVersionedObject = new javax.swing.JLabel();
        jtfVOName = new javax.swing.JTextField();
        jspVODatum = new javax.swing.JScrollPane();
        jtaVODatum = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jmiLogin = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jmiOpenProduct = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        usersMenu = new javax.swing.JMenu();
        jmiManageUser = new javax.swing.JMenuItem();
        jmiPermitions = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        jpWorkspaces.setEnabled(false);
        jpWorkspaces.setMinimumSize(new java.awt.Dimension(847, 508));
        jpWorkspaces.setName("jpWorkspaces"); // NOI18N
        jpWorkspaces.setPreferredSize(new java.awt.Dimension(847, 508));

        jpWorkItems.setMinimumSize(new java.awt.Dimension(300, 100));
        jpWorkItems.setName("jpWorkItems"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        jlAttachedWorkitems.setText(resourceMap.getString("jlAttachedWorkitems.text")); // NOI18N
        jlAttachedWorkitems.setName("jlAttachedWorkitems"); // NOI18N

        jspAttachedWorkItems.setName("jspAttachedWorkItems"); // NOI18N

        jtAttachedWorkItems.setMinimumSize(new java.awt.Dimension(100, 50));
        jtAttachedWorkItems.setName("jtAttachedWorkItems"); // NOI18N
        jtAttachedWorkItems.setPreferredSize(new java.awt.Dimension(100, 50));
        jtAttachedWorkItems.setRootVisible(false);
        jspAttachedWorkItems.setViewportView(jtAttachedWorkItems);

        jbAttachWI.setText(resourceMap.getString("jbAttachWI.text")); // NOI18N
        jbAttachWI.setEnabled(false);
        jbAttachWI.setName("jbAttachWI"); // NOI18N
        jbAttachWI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAttachWIActionPerformed(evt);
            }
        });

        jbDetachWI.setText(resourceMap.getString("jbDetachWI.text")); // NOI18N
        jbDetachWI.setEnabled(false);
        jbDetachWI.setName("jbDetachWI"); // NOI18N
        jbDetachWI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDetachWIActionPerformed(evt);
            }
        });

        jbCreateWI.setText(resourceMap.getString("jbCreateWI.text")); // NOI18N
        jbCreateWI.setEnabled(false);
        jbCreateWI.setName("jbCreateWI"); // NOI18N

        jlAvailableWorkitems.setText(resourceMap.getString("jlAvailableWorkitems.text")); // NOI18N
        jlAvailableWorkitems.setName("jlAvailableWorkitems"); // NOI18N

        jspAvailableWorkItems.setName("jspAvailableWorkItems"); // NOI18N

        jtAvailableWorkItems.setName("jtAvailableWorkItems"); // NOI18N
        jtAvailableWorkItems.setRootVisible(false);
        jspAvailableWorkItems.setViewportView(jtAvailableWorkItems);

        org.jdesktop.layout.GroupLayout jpWorkItemsLayout = new org.jdesktop.layout.GroupLayout(jpWorkItems);
        jpWorkItems.setLayout(jpWorkItemsLayout);
        jpWorkItemsLayout.setHorizontalGroup(
            jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkItemsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpWorkItemsLayout.createSequentialGroup()
                        .add(jspAttachedWorkItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 214, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jbCreateWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jbDetachWI)
                            .add(jbAttachWI)))
                    .add(jlAttachedWorkitems))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jlAvailableWorkitems)
                    .add(jspAvailableWorkItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jpWorkItemsLayout.linkSize(new java.awt.Component[] {jbAttachWI, jbCreateWI, jbDetachWI}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jpWorkItemsLayout.linkSize(new java.awt.Component[] {jspAttachedWorkItems, jspAvailableWorkItems}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jpWorkItemsLayout.setVerticalGroup(
            jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkItemsLayout.createSequentialGroup()
                .add(11, 11, 11)
                .add(jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jlAttachedWorkitems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jlAvailableWorkitems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(jpWorkItemsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jspAvailableWorkItems, 0, 0, Short.MAX_VALUE)
                    .add(jpWorkItemsLayout.createSequentialGroup()
                        .add(jbAttachWI)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbDetachWI)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbCreateWI))
                    .add(jspAttachedWorkItems, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpCommands.setName("jpCommands"); // NOI18N

        jbCreateVO.setText(resourceMap.getString("jbCreateVO.text")); // NOI18N
        jbCreateVO.setEnabled(false);
        jbCreateVO.setName("jbCreateVO"); // NOI18N

        jbSaveVO.setText(resourceMap.getString("jbSaveVO.text")); // NOI18N
        jbSaveVO.setEnabled(false);
        jbSaveVO.setName("jbSaveVO"); // NOI18N
        jbSaveVO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveVOActionPerformed(evt);
            }
        });

        jbChangeVOHat.setText(resourceMap.getString("jbChangeVOHat.text")); // NOI18N
        jbChangeVOHat.setEnabled(false);
        jbChangeVOHat.setName("jbChangeVOHat"); // NOI18N

        jbPublishVO.setText(resourceMap.getString("jbPublishVO.text")); // NOI18N
        jbPublishVO.setEnabled(false);
        jbPublishVO.setName("jbPublishVO"); // NOI18N
        jbPublishVO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPublishVOActionPerformed(evt);
            }
        });

        jbPutBackVO.setText(resourceMap.getString("jbPutBackVO.text")); // NOI18N
        jbPutBackVO.setEnabled(false);
        jbPutBackVO.setName("jbPutBackVO"); // NOI18N
        jbPutBackVO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPutBackVOActionPerformed(evt);
            }
        });

        jbViewVOHistory.setText(resourceMap.getString("jbViewVOHistory.text")); // NOI18N
        jbViewVOHistory.setEnabled(false);
        jbViewVOHistory.setName("jbViewVOHistory"); // NOI18N
        jbViewVOHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewVOHistoryActionPerformed(evt);
            }
        });

        jbViewVODistribution.setText(resourceMap.getString("jbViewVODistribution.text")); // NOI18N
        jbViewVODistribution.setActionCommand(resourceMap.getString("jbViewVODistribution.actionCommand")); // NOI18N
        jbViewVODistribution.setEnabled(false);
        jbViewVODistribution.setName("jbViewVODistribution"); // NOI18N
        jbViewVODistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewVODistributionActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jpCommandsLayout = new org.jdesktop.layout.GroupLayout(jpCommands);
        jpCommands.setLayout(jpCommandsLayout);
        jpCommandsLayout.setHorizontalGroup(
            jpCommandsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jbCreateVO)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbSaveVO)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbChangeVOHat)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbPublishVO)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbPutBackVO)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbViewVODistribution)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jbViewVOHistory)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jpCommandsLayout.linkSize(new java.awt.Component[] {jbChangeVOHat, jbCreateVO, jbPublishVO, jbPutBackVO, jbSaveVO, jbViewVODistribution, jbViewVOHistory}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jpCommandsLayout.setVerticalGroup(
            jpCommandsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpCommandsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jbCreateVO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                    .add(jbSaveVO, 0, 0, Short.MAX_VALUE)
                    .add(jbChangeVOHat, 0, 0, Short.MAX_VALUE)
                    .add(jbPublishVO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                    .add(jbPutBackVO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jbViewVODistribution, 0, 0, Short.MAX_VALUE)
                    .add(jbViewVOHistory, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpCommandsLayout.linkSize(new java.awt.Component[] {jbChangeVOHat, jbCreateVO, jbPublishVO, jbPutBackVO, jbSaveVO, jbViewVODistribution, jbViewVOHistory}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jpVersionedObjects.setName("jpVersionedObjects"); // NOI18N

        jlVersionedObjects.setText(resourceMap.getString("jlVersionedObjects.text")); // NOI18N
        jlVersionedObjects.setName("jlVersionedObjects"); // NOI18N

        jspVersionedObjects.setName("jspVersionedObjects"); // NOI18N

        jtVOs.setModel(voModel);
        jtVOs.setName("jtVOs"); // NOI18N
        jtVOs.setRootVisible(false);
        jtVOs.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jtVOsValueChanged(evt);
            }
        });
        jspVersionedObjects.setViewportView(jtVOs);

        jlVersionedObject.setText(resourceMap.getString("jlVersionedObject.text")); // NOI18N
        jlVersionedObject.setName("jlVersionedObject"); // NOI18N

        jtfVOName.setText(resourceMap.getString("jtfVOName.text")); // NOI18N
        jtfVOName.setName("jtfVOName"); // NOI18N

        jspVODatum.setName("jspVODatum"); // NOI18N

        jtaVODatum.setColumns(20);
        jtaVODatum.setRows(5);
        jtaVODatum.setName("jtaVODatum"); // NOI18N
        jspVODatum.setViewportView(jtaVODatum);

        org.jdesktop.layout.GroupLayout jpVersionedObjectsLayout = new org.jdesktop.layout.GroupLayout(jpVersionedObjects);
        jpVersionedObjects.setLayout(jpVersionedObjectsLayout);
        jpVersionedObjectsLayout.setHorizontalGroup(
            jpVersionedObjectsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpVersionedObjectsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpVersionedObjectsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpVersionedObjectsLayout.createSequentialGroup()
                        .add(jlVersionedObjects)
                        .add(85, 85, 85)
                        .add(jlVersionedObject)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtfVOName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jpVersionedObjectsLayout.createSequentialGroup()
                        .add(jspVersionedObjects, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jspVODatum, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpVersionedObjectsLayout.setVerticalGroup(
            jpVersionedObjectsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpVersionedObjectsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpVersionedObjectsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jlVersionedObjects)
                    .add(jlVersionedObject)
                    .add(jtfVOName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpVersionedObjectsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jspVODatum, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .add(jspVersionedObjects, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jpWorkspacesLayout = new org.jdesktop.layout.GroupLayout(jpWorkspaces);
        jpWorkspaces.setLayout(jpWorkspacesLayout);
        jpWorkspacesLayout.setHorizontalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jpVersionedObjects, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(jpWorkItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jpCommands, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpWorkspacesLayout.setVerticalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .add(jpWorkItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpCommands, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpVersionedObjects, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspaces, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspaces, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jmiLogin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jmiLogin.setActionCommand(resourceMap.getString("jmiLogin.actionCommand")); // NOI18N
        jmiLogin.setEnabled(false);
        jmiLogin.setLabel(resourceMap.getString("jmiLogin.label")); // NOI18N
        jmiLogin.setName("jmiLogin"); // NOI18N
        fileMenu.add(jmiLogin);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setActionCommand(resourceMap.getString("jmiNewProduct.actionCommand")); // NOI18N
        jMenuItem1.setLabel(resourceMap.getString("jmiNewProduct.label")); // NOI18N
        jMenuItem1.setName("jmiNewProduct"); // NOI18N
        fileMenu.add(jMenuItem1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getActionMap(DesktopApplication1View.class, this);
        jmiOpenProduct.setAction(actionMap.get("showOpenProduct")); // NOI18N
        jmiOpenProduct.setText(resourceMap.getString("jmiOpenProduct.text")); // NOI18N
        jmiOpenProduct.setName("jmiOpenProduct"); // NOI18N
        fileMenu.add(jmiOpenProduct);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        usersMenu.setEnabled(false);
        usersMenu.setLabel(resourceMap.getString("usersMenu.label")); // NOI18N
        usersMenu.setName("usersMenu"); // NOI18N

        jmiManageUser.setEnabled(false);
        jmiManageUser.setLabel(resourceMap.getString("jmiManageUser.label")); // NOI18N
        jmiManageUser.setName("jmiManageUser"); // NOI18N
        usersMenu.add(jmiManageUser);

        jmiPermitions.setEnabled(false);
        jmiPermitions.setLabel(resourceMap.getString("jmiPermitions.label")); // NOI18N
        jmiPermitions.setName("jmiPermitions"); // NOI18N
        usersMenu.add(jmiPermitions);

        menuBar.add(usersMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(jpWorkspaces);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jbAttachWIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAttachWIActionPerformed
        // TODO add your handling code here:
        attachWorkItem();

        loadWorkItems();
        refreshWIButtons(true);
    }//GEN-LAST:event_jbAttachWIActionPerformed

    private void jbDetachWIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDetachWIActionPerformed
        // TODO add your handling code here:
        detachWorkItem();

        loadWorkItems();
        refreshWIButtons(false);
    }//GEN-LAST:event_jbDetachWIActionPerformed

    private void jbSaveVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveVOActionPerformed
        // TODO add your handling code here:
//        try {
            //JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_name", jtfVOName.getText());
            params.put("vo_datum", jtaVODatum.getText());
            
            loadVersionedObjects();
//            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
//            params.put("ws_id", workEnvironment.getCurrentWs());
//            params.put("uid", uid);
//            params.put("type", 1);
//            params.put("constructs", 0);

//            jc.prepareJSONRequest("saveVersionedObjectState", params, uid);
//            JSONObject jResponce = jc.doRequest(null);
//            JSONObject err = jResponce.getJSONObject("error");
//            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
//            int code = err.getInt("code");
//            if (code != 0) {
//                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
//            }
            
//        } catch (JSONException ex) {
//            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //refreshVOButtons(true);
    }//GEN-LAST:event_jbSaveVOActionPerformed

    private void jbPublishVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPublishVOActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("ws_id", workEnvironment.getCurrentWs());

            jc.prepareJSONRequest("publishVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }
            loadVersionedObjects();
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refreshVOButtons(true);
    }//GEN-LAST:event_jbPublishVOActionPerformed

    private void jbPutBackVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPutBackVOActionPerformed
        // TODO add your handling code here:

        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("ws_id", workEnvironment.getCurrentWs());

            jc.prepareJSONRequest("putbackVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }
            loadVersionedObjects();
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refreshVOButtons(true);
    }//GEN-LAST:event_jbPutBackVOActionPerformed

    private void jbViewVODistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewVODistributionActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("release_id", workEnvironment.getRelease());

            jc.prepareJSONRequest("viewVersionedObjectDistribution", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            JSONArray Distribution = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }
            diplayVersionedObjectsDistribution(Distribution);
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refreshVOButtons(true);
    }//GEN-LAST:event_jbViewVODistributionActionPerformed

    private void jbViewVOHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewVOHistoryActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("release_id", workEnvironment.getRelease());

            jc.prepareJSONRequest("viewVersionedObjectHistory", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            JSONArray HistoryItems = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }
            diplayVersionedObjectsHistory(HistoryItems);
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refreshVOButtons(true);
    }//GEN-LAST:event_jbViewVOHistoryActionPerformed

    private void jtVOsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jtVOsValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtVOs.getLastSelectedPathComponent();
        if (node == null) {
            clearVOData();
        } else {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            voInfo nodeInfo = (voInfo) node.getUserObject();
            try {
                we.setCurrentVersionedObject(nodeInfo.getVo_id());
            } catch (JSONException ex) {
                Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayVOdata();
        }
    }//GEN-LAST:event_jtVOsValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JButton jbAttachWI;
    private javax.swing.JButton jbChangeVOHat;
    private javax.swing.JButton jbCreateVO;
    private javax.swing.JButton jbCreateWI;
    private javax.swing.JButton jbDetachWI;
    private javax.swing.JButton jbPublishVO;
    private javax.swing.JButton jbPutBackVO;
    private javax.swing.JButton jbSaveVO;
    private javax.swing.JButton jbViewVODistribution;
    private javax.swing.JButton jbViewVOHistory;
    private javax.swing.JLabel jlAttachedWorkitems;
    private javax.swing.JLabel jlAvailableWorkitems;
    private javax.swing.JLabel jlVersionedObject;
    private javax.swing.JLabel jlVersionedObjects;
    private javax.swing.JMenuItem jmiLogin;
    private javax.swing.JMenuItem jmiManageUser;
    private javax.swing.JMenuItem jmiOpenProduct;
    private javax.swing.JMenuItem jmiPermitions;
    private javax.swing.JPanel jpCommands;
    private javax.swing.JPanel jpVersionedObjects;
    private javax.swing.JPanel jpWorkItems;
    private javax.swing.JPanel jpWorkspaces;
    private javax.swing.JScrollPane jspAttachedWorkItems;
    private javax.swing.JScrollPane jspAvailableWorkItems;
    private javax.swing.JScrollPane jspVODatum;
    private javax.swing.JScrollPane jspVersionedObjects;
    private javax.swing.JTree jtAttachedWorkItems;
    private javax.swing.JTree jtAvailableWorkItems;
    private javax.swing.JTree jtVOs;
    private javax.swing.JTextArea jtaVODatum;
    private javax.swing.JTextField jtfVOName;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu usersMenu;
    // End of variables declaration//GEN-END:variables
    //private final Timer messageTimer;
    //private final Timer busyIconTimer;
    //private final Icon idleIcon;
    //private final Icon[] busyIcons = new Icon[15];
    //private int busyIconIndex = 0;
    private JDialog aboutBox;
    private OpenWorkspace openProduct;

    private void loadWorkItems() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ws_id", workEnvironment.getCurrentWs());
            jc.prepareJSONRequest("getWorkItemList", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            JSONObject workitems = jResponce.getJSONObject("result");
            workEnvironment.setAttachedWorkItems(workitems.getJSONArray("attached"));
            workEnvironment.setAvailableWorkItems(workitems.getJSONArray("not_attached"));
            int code = err.getInt("code");
            if (code == 0) {
                refreshWorkitems();
            } else {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshWorkitems() {
        try {
            JSONArray attached = workEnvironment.getAttachedWorkItems();
            JSONArray notAttached = workEnvironment.getAvailableWorkItems();

            int pr_len = attached.length();
            JSONObject tmpWorkitem;
            Vector v = new Vector();
            for (int i = 0; i
                    < pr_len; i++) {
                tmpWorkitem = (JSONObject) attached.get(i);
                v.add(i, tmpWorkitem.get("name"));
            }
            jlstAttachedWorkitems.setListData(v);

            pr_len = notAttached.length();
            v = new Vector();
            for (int i = 0; i
                    < pr_len; i++) {
                tmpWorkitem = (JSONObject) notAttached.get(i);
                v.add(i, tmpWorkitem.get("name"));
            }
            jlstNotAttachedWorkitems.setListData(v);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void loadVersionedObjects() {
        try {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            CommandFactory cf = new CommandFactory();
            ICommand cmd = cf.createCommand(CommandFactory.CmdCode.GET_VERSIONED_OBJECT_LIST);

            JSONArray vo_ls = (JSONArray) cmd.doRequest();
            we.setVersionedObject_ls(vo_ls);
            if (vo_ls != null) {
                displayVersionedObjectsTree();
            }
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayVersionedObjectsTree() {
        try {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            JSONArray vo_ls = we.getVersionedObject_ls();

            voRoot.removeAllChildren();
            int pr_len = vo_ls.length();
            JSONObject tmpVO;
            DefaultMutableTreeNode tmpNode, tmpNode2 = null;
            String val;
            Map wsMap = new HashMap();
            for (int i = 0; i
                    < pr_len; i++) {
                tmpVO = (JSONObject) vo_ls.get(i);
                val = tmpVO.getString("vp_name") + " - " + we.getVisibilityVectorString(tmpVO.getInt("v_vector"));
                voInfo voInfo = new voInfo(val, tmpVO.getInt("vo_id"), i);

                //@todo - to get valid attribute from VO
                int ancestor_ws_id = tmpVO.getInt("constructs");
                if (ancestor_ws_id == 0) {
                    tmpNode = new DefaultMutableTreeNode(voInfo);
                    voRoot.add(tmpNode);
                    wsMap.put(voInfo.getVo_id(), tmpNode);
                } else {
                    tmpNode = new DefaultMutableTreeNode(voInfo);
                    tmpNode2 = (DefaultMutableTreeNode) wsMap.get(ancestor_ws_id);
                    tmpNode2.add(tmpNode);
                    wsMap.put(voInfo.getVo_id(), tmpNode);
                }
            }
            jtVOs.setRootVisible(false);
            ((DefaultTreeModel) jtVOs.getModel()).reload();
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void attachWorkItem() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            JSONArray notAttached = workEnvironment.getAvailableWorkItems();
            JSONObject wi = (JSONObject) notAttached.get(jlstNotAttachedWorkitems.getSelectedIndex());
            params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", workEnvironment.getCurrentWs());
            jc.prepareJSONRequest("attachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void detachWorkItem() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            JSONArray notAttached = workEnvironment.getAttachedWorkItems();
            JSONObject wi = (JSONObject) notAttached.get(jlstAttachedWorkitems.getSelectedIndex());
            params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", workEnvironment.getCurrentWs());
            jc.prepareJSONRequest("detachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(null);
            JSONObject err = jResponce.getJSONObject("error");
            workEnvironment.setVersionedObject_ls(jResponce.getJSONArray("result"));
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadSelectedVO() {
        try {
            JSONArray versionedObjects = workEnvironment.getVersionedObject_ls();
            //@todo da vzema indeksa na VOid ot tree-to
            JSONObject selectedVO = (JSONObject) versionedObjects.get(jlstVersionedObjects.getSelectedIndex());
            workEnvironment.setCurrentVersionedObject(selectedVO.getInt("vo_id"));
            jtfVOName.setText(selectedVO.getString("vp_name"));
            jtaVODatum.setText(selectedVO.getString("vp_datum"));
            //refreshVOButtons(true);
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void diplayVersionedObjectsDistribution(JSONArray Distribution) {
        // TODO - idea - to show in a new window an expanded tree, where nodes with gray means no local version.
        // By selectin a node the user shall be able to view (readonly mode) the version of the VO.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void diplayVersionedObjectsHistory(JSONArray HistoryItems) {
        // TODO - idea - to show in a new window list of all historical changes of VO - source, target, time of version, user initiated the change, work item of the change
    }
    /*
    private void expandAll(JTree tree, boolean expand) {
    TreeNode root = (TreeNode)tree.getModel().getRoot();

    // Traverse tree from root
    expandAll(tree, new TreePath(root), expand);
    }
    private void expandAll(JTree tree, TreePath parent, boolean expand) {
    // Traverse children
    TreeNode node = (TreeNode)parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
    for (Enumeration e=node.children(); e.hasMoreElements(); ) {
    TreeNode n = (TreeNode)e.nextElement();
    TreePath path = parent.pathByAddingChild(n);
    expandAll(tree, path, expand);
    }
    }

    // Expansion or collapse must be done bottom-up
    if (expand) {
    tree.expandPath(parent);
    } else {
    tree.collapsePath(parent);
    }
    }//*/

    private void refreshWIButtons(boolean b) {
        jbAttachWI.setEnabled(b);
        jbDetachWI.setEnabled(b);
    }

    private void clearVOData() {
        jtfVOName.setText("");
        jtaVODatum.setText("");
        jbPublishVO.setEnabled(false);
        jbPutBackVO.setEnabled(false);
        jbSaveVO.setEnabled(false);
        jbViewVODistribution.setEnabled(false);
        jbViewVOHistory.setEnabled(false);
        jbChangeVOHat.setEnabled(false);
    }

    private void displayVOdata() {
        try {
            WorkEnvironment we = WorkEnvironment.getWorkEnvironment();
            JSONObject vo = we.getCurrentVersionedObject();
            jtfVOName.setText(vo.getString("vp_name"));
            jtaVODatum.setText(vo.getString("vp_datum"));
            jbPublishVO.setEnabled(we.isLocalCurrentVersionedObject());
            jbPutBackVO.setEnabled(we.isPutBackableCurrentVersionedObject());
            jbSaveVO.setEnabled(false);
            jbViewVODistribution.setEnabled(true);
            jbViewVOHistory.setEnabled(true);
            jbChangeVOHat.setEnabled(true);
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    private void refreshVOButtons(boolean b) {
    jbSaveVO.setEnabled(b);
    jbViewVODistribution.setEnabled(b);
    jbViewVOHistory.setEnabled(b);

    try {
    JSONObject selectedVO = workEnvironment.getCurrentVersionedObject();
    int vv = selectedVO.getInt("v_vector");
    if ((vv & 8) > 0) { // Local versioned object
    jbCreateWI.setEnabled(b);
    jbPublishVO.setEnabled(b);
    if ((vv & 3) > 0) {
    jbPutBackVO.setEnabled(b);
    } else {
    jbPutBackVO.setEnabled(false);
    }
    } else {
    jbPublishVO.setEnabled(false);
    jbPutBackVO.setEnabled(false);
    jbCreateWI.setEnabled(false);
    }
    } catch (Exception ex) {
    jbPublishVO.setEnabled(false);
    jbPutBackVO.setEnabled(false);
    jbCreateWI.setEnabled(false);
    }


    if (!b) {
    jtfVOName.setText("");
    jtaVODatum.setText("");
    }
    }//*/
}
