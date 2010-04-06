/*
 * DesktopApplication1View.java
 */
package desktopapplication1;

import com.jotov.versia.WorkEnvironment;
import com.jotov.versia.gui.NewEditWorkspace;
import com.jotov.versia.gui.VersiaAboutBox;
import com.jotov.versia.json.JSONConnection;
import com.jotov.versia.gui.OpenWorkspace;
import com.jotov.versia.gui2.command.CommandFactory;
import com.jotov.versia.gui2.command.ICommand;
import com.jotov.versia.voInfo;
import com.jotov.versia.worspaceInfo;
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
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***********************************************************************
 * Module:  DesktopApplication1View.java
 * Author:  Vladimir Jotov
 * Purpose: Defines the Class DesktopApplication1View
 ***********************************************************************/
public class DesktopApplication1View extends FrameView {

    private WorkEnvironment workEnvironment;
    private int uid = 1;
    DefaultMutableTreeNode workspaceRoot;
    DefaultMutableTreeNode voRoot;
    DefaultTreeModel workspaceModel;
    DefaultTreeModel voModel;

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        workEnvironment = WorkEnvironment.getWorkEnvironment();
        workspaceRoot = new DefaultMutableTreeNode();
        voRoot = new DefaultMutableTreeNode();
        workspaceModel = new DefaultTreeModel(workspaceRoot);
        voModel = new DefaultTreeModel(voRoot);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
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
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
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
            //workEnvironment.setProject(openProduct.getSelectedProjectID());
            //workEnvironment.setRelease(openProduct.getSelectedReleaseID());
            //   loadWorkspaces();
            // refreshWSButtons(false);
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
        jlAttachedWorkitems = new javax.swing.JLabel();
        jspAttWI = new javax.swing.JScrollPane();
        jlstAttachedWorkitems = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jspAvalWI = new javax.swing.JScrollPane();
        jlstNotAttachedWorkitems = new javax.swing.JList();
        jbAttachWI = new javax.swing.JButton();
        jbDetachWI = new javax.swing.JButton();
        jbCreateVO = new javax.swing.JButton();
        jbPublishVO = new javax.swing.JButton();
        jbPutBackVO = new javax.swing.JButton();
        jbSaveVO = new javax.swing.JButton();
        jbCreateWI = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtfVOName = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaVODatum = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaJSONTrace = new javax.swing.JTextArea();
        jbViewVODistribution = new javax.swing.JButton();
        jbViewVOHistory = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtVOs = new javax.swing.JTree();
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
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jpWorkspaces.setEnabled(false);
        jpWorkspaces.setName("jpWorkspaces"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        jlAttachedWorkitems.setText(resourceMap.getString("jlAttachedWorkitems.text")); // NOI18N
        jlAttachedWorkitems.setName("jlAttachedWorkitems"); // NOI18N

        jspAttWI.setName("jspAttWI"); // NOI18N

        jlstAttachedWorkitems.setName("jlstAttachedWorkitems"); // NOI18N
        jlstAttachedWorkitems.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstAttachedWorkitemsValueChanged(evt);
            }
        });
        jspAttWI.setViewportView(jlstAttachedWorkitems);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jspAvalWI.setName("jspAvalWI"); // NOI18N

        jlstNotAttachedWorkitems.setName("jlstNotAttachedWorkitems"); // NOI18N
        jlstNotAttachedWorkitems.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstNotAttachedWorkitemsValueChanged(evt);
            }
        });
        jspAvalWI.setViewportView(jlstNotAttachedWorkitems);

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

        jbCreateVO.setText(resourceMap.getString("jbCreateVO.text")); // NOI18N
        jbCreateVO.setEnabled(false);
        jbCreateVO.setName("jbCreateVO"); // NOI18N

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

        jbSaveVO.setText(resourceMap.getString("jbSaveVO.text")); // NOI18N
        jbSaveVO.setEnabled(false);
        jbSaveVO.setName("jbSaveVO"); // NOI18N
        jbSaveVO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveVOActionPerformed(evt);
            }
        });

        jbCreateWI.setText(resourceMap.getString("jbCreateWI.text")); // NOI18N
        jbCreateWI.setEnabled(false);
        jbCreateWI.setName("jbCreateWI"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtfVOName.setText(resourceMap.getString("jtfVOName.text")); // NOI18N
        jtfVOName.setName("jtfVOName"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jtaVODatum.setColumns(20);
        jtaVODatum.setRows(5);
        jtaVODatum.setName("jtaVODatum"); // NOI18N
        jScrollPane3.setViewportView(jtaVODatum);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jtaJSONTrace.setColumns(20);
        jtaJSONTrace.setRows(5);
        jtaJSONTrace.setName("jtaJSONTrace"); // NOI18N
        jScrollPane1.setViewportView(jtaJSONTrace);

        jbViewVODistribution.setText(resourceMap.getString("jbViewVODistribution.text")); // NOI18N
        jbViewVODistribution.setActionCommand(resourceMap.getString("jbViewVODistribution.actionCommand")); // NOI18N
        jbViewVODistribution.setEnabled(false);
        jbViewVODistribution.setName("jbViewVODistribution"); // NOI18N
        jbViewVODistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewVODistributionActionPerformed(evt);
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

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jtVOs.setModel(voModel);
        jtVOs.setName("jtVOs"); // NOI18N
        jtVOs.setRootVisible(false);
        jScrollPane4.setViewportView(jtVOs);

        org.jdesktop.layout.GroupLayout jpWorkspacesLayout = new org.jdesktop.layout.GroupLayout(jpWorkspaces);
        jpWorkspaces.setLayout(jpWorkspacesLayout);
        jpWorkspacesLayout.setHorizontalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jpWorkspacesLayout.createSequentialGroup()
                                    .add(jLabel3)
                                    .add(61, 61, 61))
                                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                            .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jspAttWI)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jpWorkspacesLayout.createSequentialGroup()
                                    .add(jbCreateVO)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jbPublishVO)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jbPutBackVO)))
                            .add(jlAttachedWorkitems))
                        .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jpWorkspacesLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jpWorkspacesLayout.createSequentialGroup()
                                        .add(jbSaveVO)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jbViewVODistribution)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jbViewVOHistory))
                                    .add(jpWorkspacesLayout.createSequentialGroup()
                                        .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jbDetachWI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .add(jbAttachWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(jpWorkspacesLayout.createSequentialGroup()
                                                .add(jbCreateWI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(2, 2, 2)))
                                        .add(4, 4, 4)
                                        .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel1)
                                            .add(jspAvalWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(22, 22, 22)))
                                .add(312, 312, 312))
                            .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(jpWorkspacesLayout.createSequentialGroup()
                                    .add(jLabel2)
                                    .add(40, 40, 40)
                                    .add(jtfVOName))
                                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 398, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(27, 27, 27))))
        );
        jpWorkspacesLayout.setVerticalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .add(38, 38, 38)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jlAttachedWorkitems)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(jbAttachWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbDetachWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbCreateWI))
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(jspAttWI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                        .add(1, 1, 1))
                    .add(jspAvalWI, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbPutBackVO, 0, 0, Short.MAX_VALUE)
                        .add(jbSaveVO, 0, 0, Short.MAX_VALUE)
                        .add(jbViewVODistribution, 0, 0, Short.MAX_VALUE)
                        .add(jbViewVOHistory, 0, 0, Short.MAX_VALUE))
                    .add(jbPublishVO, 0, 0, Short.MAX_VALUE)
                    .add(jbCreateVO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jtfVOName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2))
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jpWorkspacesLayout.linkSize(new java.awt.Component[] {jspAttWI, jspAvalWI}, org.jdesktop.layout.GroupLayout.VERTICAL);

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

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 749, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(jpWorkspaces);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jlstAttachedWorkitemsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstAttachedWorkitemsValueChanged
        // TODO add your handling code here:
        jbDetachWI.setEnabled(true);
    }//GEN-LAST:event_jlstAttachedWorkitemsValueChanged

    private void jlstNotAttachedWorkitemsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstNotAttachedWorkitemsValueChanged
        // TODO add your handling code here
        jbAttachWI.setEnabled(true);
    }//GEN-LAST:event_jlstNotAttachedWorkitemsValueChanged

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
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_name", jtfVOName.getText());
            params.put("vo_datum", jtaVODatum.getText());
            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("ws_id", workEnvironment.getCurrentWs());
            params.put("uid", uid);
            params.put("type", 1);
            params.put("constructs", 0);

            jc.prepareJSONRequest("saveVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
        refreshVOButtons(true);
    }//GEN-LAST:event_jbSaveVOActionPerformed

    private void jbPublishVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPublishVOActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("ws_id", workEnvironment.getCurrentWs());

            jc.prepareJSONRequest("publishVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
        refreshVOButtons(true);
    }//GEN-LAST:event_jbPublishVOActionPerformed

    private void jbPutBackVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPutBackVOActionPerformed
        // TODO add your handling code here:

        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("ws_id", workEnvironment.getCurrentWs());

            jc.prepareJSONRequest("putbackVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
        refreshVOButtons(true);
    }//GEN-LAST:event_jbPutBackVOActionPerformed

    private void jbViewVODistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewVODistributionActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("release_id", workEnvironment.getRelease());

            jc.prepareJSONRequest("viewVersionedObjectDistribution", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
        refreshVOButtons(true);
    }//GEN-LAST:event_jbViewVODistributionActionPerformed

    private void jbViewVOHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewVOHistoryActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", workEnvironment.getCurrentVersionedObjectID());
            params.put("release_id", workEnvironment.getRelease());

            jc.prepareJSONRequest("viewVersionedObjectHistory", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
        refreshVOButtons(true);
    }//GEN-LAST:event_jbViewVOHistoryActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbAttachWI;
    private javax.swing.JButton jbCreateVO;
    private javax.swing.JButton jbCreateWI;
    private javax.swing.JButton jbDetachWI;
    private javax.swing.JButton jbPublishVO;
    private javax.swing.JButton jbPutBackVO;
    private javax.swing.JButton jbSaveVO;
    private javax.swing.JButton jbViewVODistribution;
    private javax.swing.JButton jbViewVOHistory;
    private javax.swing.JLabel jlAttachedWorkitems;
    private javax.swing.JList jlstAttachedWorkitems;
    private javax.swing.JList jlstNotAttachedWorkitems;
    private javax.swing.JMenuItem jmiLogin;
    private javax.swing.JMenuItem jmiManageUser;
    private javax.swing.JMenuItem jmiOpenProduct;
    private javax.swing.JMenuItem jmiPermitions;
    private javax.swing.JPanel jpWorkspaces;
    private javax.swing.JScrollPane jspAttWI;
    private javax.swing.JScrollPane jspAvalWI;
    private javax.swing.JTree jtVOs;
    private javax.swing.JTextArea jtaJSONTrace;
    private javax.swing.JTextArea jtaVODatum;
    private javax.swing.JTextField jtfVOName;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu usersMenu;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private OpenWorkspace openProduct;

    private void loadWorkItems() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ws_id", workEnvironment.getCurrentWs());
            jc.prepareJSONRequest("getWorkItemList", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
            for (int i = 0; i < pr_len; i++) {
                tmpWorkitem = (JSONObject) attached.get(i);
                v.add(i, tmpWorkitem.get("name"));
            }
            jlstAttachedWorkitems.setListData(v);

            pr_len = notAttached.length();
            v = new Vector();
            for (int i = 0; i < pr_len; i++) {
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
            for (int i = 0; i < pr_len; i++) {
                tmpVO = (JSONObject) vo_ls.get(i);
                val = tmpVO.getString("vp_name") + " - " + transform_vector(tmpVO.getInt("v_vector"));
                voInfo voInfo = new voInfo(val, tmpVO.getInt("vo_id"), i);

                //@todo - to get valid attribute from VO
                int ancestor_ws_id = tmpWS.getInt("ancestor_ws_id");

                if (ancestor_ws_id == 0) {
                    tmpNode = new DefaultMutableTreeNode(voInfo);
                    workspaceRoot.add(tmpNode);
                    wsMap.put(voInfo.getVo_id(), tmpNode);
                } else {
                    tmpNode = new DefaultMutableTreeNode(voInfo);
                    tmpNode2 = (DefaultMutableTreeNode) wsMap.get(ancestor_ws_id);
                    tmpNode2.add(tmpNode);
                    wsMap.put(voInfo.getVo_id(), tmpNode);
                }
            }
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
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
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
            refreshVOButtons(true);
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
    }

    private String transform_vector(int vector) {
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
