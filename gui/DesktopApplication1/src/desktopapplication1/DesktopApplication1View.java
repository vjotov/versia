/*
 * DesktopApplication1View.java
 */
package desktopapplication1;

import com.jotov.versia.gui.NewEditWorkspace;
import com.jotov.versia.gui.VersiaAboutBox;
import com.jotov.versia.json.JSONConnection;
import com.jotov.versia.gui.OpenProduct;
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

/**
 * The application's main frame.
 */
public class DesktopApplication1View extends FrameView {

    private JSONArray projects;
    private JSONArray releases;
    private JSONArray workspaces;
    private JSONArray versionedObjects;
    private JSONObject workitems;
    private JSONObject selectedVO;
    private int selectedProjectID;
    private int selectedReleaseID;
    private int selectedWorkspaceID;
    private int uid = 1;
    DefaultMutableTreeNode wsRoot;
    DefaultTreeModel wsModel;

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        wsRoot = new DefaultMutableTreeNode();
        wsModel = new DefaultTreeModel(wsRoot);
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
            openProduct = new OpenProduct(mainFrame);
            openProduct.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(openProduct);
        if (openProduct.getActionCommand().equals("BTN_OK")) {
            selectedProjectID = openProduct.getSelectedProjectID();
            selectedReleaseID = openProduct.getSelectedReleaseID();

            loadWorkspaces();
            refreshWSButtons(false);
        }
    }

    @Action
    public void showNewWorkspace() {
        JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
        NewEditWorkspace dlg = new NewEditWorkspace(mainFrame);
        dlg.setLocationRelativeTo(mainFrame);

        DesktopApplication1.getApplication().show(dlg);
        if (dlg.getActionCommand().equals("BTN_SAVE")) {
            String newWSName = dlg.getWSName();
            createWS(newWSName);
            loadWorkspaces();
        }
    }

    @Action
    public void showEditWorkspace() {
        JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
        NewEditWorkspace dlg = new NewEditWorkspace(mainFrame);
        dlg.setLocationRelativeTo(mainFrame);

        DesktopApplication1.getApplication().show(dlg);
        if (dlg.getActionCommand().equals("BTN_SAVE")) {
            String newWSName = dlg.getWSName();
            saveWS(newWSName);
            loadWorkspaces();
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
        jspWorkspaces = new javax.swing.JScrollPane();
        jtWorkspaces = new javax.swing.JTree();
        jbNewWorkspace = new javax.swing.JButton();
        jbEditWS = new javax.swing.JButton();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstVersionedObjects = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaJSONTrace = new javax.swing.JTextArea();
        jbViewVODistribution = new javax.swing.JButton();
        jbViewVOHistory = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jmiLogin = new javax.swing.JMenuItem();
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

        jspWorkspaces.setName("jspWorkspaces"); // NOI18N

        jtWorkspaces.setModel(wsModel);
        jtWorkspaces.setName("jtWorkspaces"); // NOI18N
        jtWorkspaces.setNextFocusableComponent(jbCreateVO);
        jtWorkspaces.setRootVisible(false);
        jtWorkspaces.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jtWorkspacesValueChanged(evt);
            }
        });
        jspWorkspaces.setViewportView(jtWorkspaces);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getActionMap(DesktopApplication1View.class, this);
        jbNewWorkspace.setAction(actionMap.get("showNewWorkspace")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        jbNewWorkspace.setText(resourceMap.getString("jbNewWorkspace.text")); // NOI18N
        jbNewWorkspace.setName("jbNewWorkspace"); // NOI18N
        jbNewWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNewWorkspaceActionPerformed(evt);
            }
        });

        jbEditWS.setAction(actionMap.get("showEditWorkspace")); // NOI18N
        jbEditWS.setText(resourceMap.getString("jbEditWS.text")); // NOI18N
        jbEditWS.setName("jbEditWS"); // NOI18N

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

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jlstVersionedObjects.setName("jlstVersionedObjects"); // NOI18N
        jlstVersionedObjects.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstVersionedObjectsValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jlstVersionedObjects);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

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

        org.jdesktop.layout.GroupLayout jpWorkspacesLayout = new org.jdesktop.layout.GroupLayout(jpWorkspaces);
        jpWorkspaces.setLayout(jpWorkspacesLayout);
        jpWorkspacesLayout.setHorizontalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jpWorkspacesLayout.createSequentialGroup()
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jspWorkspaces, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .add(jLabel4))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jpWorkspacesLayout.createSequentialGroup()
                                        .add(jlAttachedWorkitems)
                                        .add(40, 40, 40))
                                    .add(jspAttWI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                                .add(6, 6, 6)
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                    .add(jbAttachWI)
                                    .add(jbDetachWI))
                                .add(5, 5, 5)
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jpWorkspacesLayout.createSequentialGroup()
                                        .add(jLabel1)
                                        .add(80, 80, 80))
                                    .add(jspAvalWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(94, 94, 94))
                            .add(jpWorkspacesLayout.createSequentialGroup()
                                .add(jbNewWorkspace)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbEditWS))
                            .add(jpWorkspacesLayout.createSequentialGroup()
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel3)
                                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jpWorkspacesLayout.createSequentialGroup()
                                        .add(jLabel2)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jtfVOName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)))
                            .add(jpWorkspacesLayout.createSequentialGroup()
                                .add(jbCreateVO)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbPublishVO)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbPutBackVO)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbSaveVO)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbCreateWI)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbViewVODistribution)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jbViewVOHistory)))
                        .add(27, 27, 27))))
        );

        jpWorkspacesLayout.linkSize(new java.awt.Component[] {jbAttachWI, jbDetachWI}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jpWorkspacesLayout.setVerticalGroup(
            jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspacesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jbNewWorkspace, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jbEditWS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jlAttachedWorkitems)
                    .add(jLabel1)
                    .add(jLabel4))
                .add(5, 5, 5)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jbAttachWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jpWorkspacesLayout.createSequentialGroup()
                        .add(27, 27, 27)
                        .add(jbDetachWI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jspAttWI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .add(jspWorkspaces, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jspAvalWI, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbViewVODistribution, 0, 0, Short.MAX_VALUE)
                        .add(jbViewVOHistory, 0, 0, Short.MAX_VALUE))
                    .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbPutBackVO, 0, 0, Short.MAX_VALUE)
                        .add(jbSaveVO, 0, 0, Short.MAX_VALUE)
                        .add(jbCreateWI, 0, 0, Short.MAX_VALUE))
                    .add(jbPublishVO, 0, 0, Short.MAX_VALUE)
                    .add(jbCreateVO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE))
                .add(11, 11, 11)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jtfVOName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2))
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jpWorkspacesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
        );

        jpWorkspacesLayout.linkSize(new java.awt.Component[] {jspAttWI, jspAvalWI, jspWorkspaces}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspaces, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpWorkspaces, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
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

        jmiOpenProduct.setAction(actionMap.get("showOpenProduct")); // NOI18N
        jmiOpenProduct.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
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
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 602, Short.MAX_VALUE)
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

    private void jtWorkspacesValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jtWorkspacesValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtWorkspaces.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        worspaceInfo nodeInfo = (worspaceInfo) node.getUserObject();
        selectedWorkspaceID = nodeInfo.getWs_id();
        loadVOsAndWI();
        refreshWSButtons(true);
        refreshVOButtons(false);
        refreshWIButtons(false);
    }//GEN-LAST:event_jtWorkspacesValueChanged

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

    private void jlstVersionedObjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstVersionedObjectsValueChanged
        // TODO add your handling code here:
        loadSelectedVO();
    }//GEN-LAST:event_jlstVersionedObjectsValueChanged

    private void jbSaveVOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveVOActionPerformed
        // TODO add your handling code here:
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_name", jtfVOName.getText());
            params.put("vo_datum", jtaVODatum.getText());
            params.put("vo_id", selectedVO.getInt("vo_id"));
            params.put("ws_id", selectedWorkspaceID);
            params.put("uid", uid);

            jc.prepareJSONRequest("saveVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
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

            params.put("vo_id", selectedVO.getInt("vo_id"));
            params.put("ws_id", selectedWorkspaceID);

            jc.prepareJSONRequest("publishVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
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

            params.put("vo_id", selectedVO.getInt("vo_id"));
            params.put("ws_id", selectedWorkspaceID);

            jc.prepareJSONRequest("putbackVersionedObjectState", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
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

    private void jbNewWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNewWorkspaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbNewWorkspaceActionPerformed

    private void jbViewVODistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewVODistributionActionPerformed
        // TODO add your handling code here:
         try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();

            params.put("vo_id", selectedVO.getInt("vo_id"));
            params.put("release_id", selectedReleaseID);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbAttachWI;
    private javax.swing.JButton jbCreateVO;
    private javax.swing.JButton jbCreateWI;
    private javax.swing.JButton jbDetachWI;
    private javax.swing.JButton jbEditWS;
    private javax.swing.JButton jbNewWorkspace;
    private javax.swing.JButton jbPublishVO;
    private javax.swing.JButton jbPutBackVO;
    private javax.swing.JButton jbSaveVO;
    private javax.swing.JButton jbViewVODistribution;
    private javax.swing.JButton jbViewVOHistory;
    private javax.swing.JLabel jlAttachedWorkitems;
    private javax.swing.JList jlstAttachedWorkitems;
    private javax.swing.JList jlstNotAttachedWorkitems;
    private javax.swing.JList jlstVersionedObjects;
    private javax.swing.JMenuItem jmiLogin;
    private javax.swing.JMenuItem jmiManageUser;
    private javax.swing.JMenuItem jmiOpenProduct;
    private javax.swing.JMenuItem jmiPermitions;
    private javax.swing.JPanel jpWorkspaces;
    private javax.swing.JScrollPane jspAttWI;
    private javax.swing.JScrollPane jspAvalWI;
    private javax.swing.JScrollPane jspWorkspaces;
    private javax.swing.JTree jtWorkspaces;
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
    private OpenProduct openProduct;

    private void loadWorkspaces() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("release_id", selectedReleaseID);
            jc.prepareJSONRequest("getReleaseWorkspaces", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            workspaces = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code == 0) {
                refreshWorkspaces();
            } else {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshWorkspaces() {
        try {
            wsRoot.removeAllChildren();
            int pr_len = workspaces.length();
            JSONObject tmpWorkspace;
            DefaultMutableTreeNode tmpNode, tmpNode2 = null;
            Map wsMap = new HashMap();
            for (int i = 0; i < pr_len; i++) {
                tmpWorkspace = (JSONObject) workspaces.get(i);
                String name = tmpWorkspace.get("name").toString();
                int ws_id = Integer.parseInt(tmpWorkspace.get("ws_id").toString());
                int ancestor_ws_id = Integer.parseInt(tmpWorkspace.get("ancestor_ws_id").toString());

                if (ancestor_ws_id == 0) {
                    wsRoot.setUserObject(new worspaceInfo(name, ws_id, i));
                    wsMap.put(ws_id, wsRoot);
                } else {
                    tmpNode = new DefaultMutableTreeNode(new worspaceInfo(name, ws_id, i));
                    tmpNode2 = (DefaultMutableTreeNode) wsMap.get(ancestor_ws_id);
                    tmpNode2.add(tmpNode);
                    wsMap.put(ws_id, tmpNode);
                }
            }
            jtWorkspaces.setRootVisible(true);
            jtWorkspaces.expandRow(2);
            ((DefaultTreeModel) jtWorkspaces.getModel()).reload();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void loadVOsAndWI() {
        //System.out.println(" loadVOsAndWI(). Selected workspace is: "+selectedWorkspaceID);
        loadWorkItems();
        loadVersionedObjects();
    }

    private void loadWorkItems() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ws_id", selectedWorkspaceID);
            jc.prepareJSONRequest("getWorkItemList", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            workitems = jResponce.getJSONObject("result");
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
            JSONArray attached = (JSONArray) workitems.get("attached");
            JSONArray notAttached = (JSONArray) workitems.get("not_attached");

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
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ws_id", selectedWorkspaceID);
            jc.prepareJSONRequest("getVisibleVersionedObjectList", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code == 0) {
                refreshVersionedObjects();
            } else {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshVersionedObjects() {
        try {
            int pr_len = versionedObjects.length();
            JSONObject tmpRelease;
            Vector v = new Vector();
            for (int i = 0; i < pr_len; i++) {
                tmpRelease = (JSONObject) versionedObjects.get(i);
                String val = tmpRelease.getString("vp_name") + " - " + transform_vector(tmpRelease.getInt("v_vector"));
                v.add(i, val);
            }
            jlstVersionedObjects.setListData(v);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void attachWorkItem() {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            JSONArray notAttached = (JSONArray) workitems.get("not_attached");
            JSONObject wi = (JSONObject) notAttached.get(jlstNotAttachedWorkitems.getSelectedIndex());
            params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", selectedWorkspaceID);
            jc.prepareJSONRequest("attachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
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
            JSONArray notAttached = (JSONArray) workitems.get("attached");
            JSONObject wi = (JSONObject) notAttached.get(jlstAttachedWorkitems.getSelectedIndex());
            params.put("wi_id", Integer.parseInt(wi.get("wi_id").toString()));
            params.put("ws_id", selectedWorkspaceID);
            jc.prepareJSONRequest("detachWorkItem", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createWS(String newWSName) {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("ancestor_workspace_id", selectedWorkspaceID);
            params.put("new_workspace_name", newWSName);
            jc.prepareJSONRequest("createWorkspace", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
            int code = err.getInt("code");
            if (code != 0) {
                System.err.println("JSON ERROR loadReleases - code:" + code + "; message:" + err.get("message").toString());
            }
        } catch (JSONException ex) {
            Logger.getLogger(DesktopApplication1View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void saveWS(String newWSName) {
        try {
            JSONConnection jc = new JSONConnection();
            Map params = new HashMap();
            params.put("workspace_id", selectedWorkspaceID);
            params.put("new_workspace_name", newWSName);
            jc.prepareJSONRequest("updateWorkspace", params, uid);
            JSONObject jResponce = jc.doRequest(jtaJSONTrace);
            JSONObject err = jResponce.getJSONObject("error");
            versionedObjects = jResponce.getJSONArray("result");
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
            selectedVO = (JSONObject) versionedObjects.get(jlstVersionedObjects.getSelectedIndex());
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

    private void refreshWSButtons(boolean b) {
        jbEditWS.setEnabled(b);
        jbCreateVO.setEnabled(b);
        jbNewWorkspace.setEnabled(b);
    }

    private void refreshWIButtons(boolean b) {
        jbAttachWI.setEnabled(b);
        jbDetachWI.setEnabled(b);
    }

    private void refreshVOButtons(boolean b) {
        jbSaveVO.setEnabled(b);
        jbViewVODistribution.setEnabled(b);
        jbViewVOHistory.setEnabled(b);

        try {
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
