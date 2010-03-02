/*
 * DesktopApplication1.java
 */

package desktopapplication1;

import com.jotov.versia.json.JSONConnection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class DesktopApplication1 extends SingleFrameApplication {
    private static String ConfigurationFile = "versia.cfg";
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        Properties properties = new Properties();
        String RepositoryURL = null;
        try {
            properties.load(new FileInputStream(ConfigurationFile));
            RepositoryURL = properties.getProperty("repository_url");
            if (RepositoryURL == null){
                RepositoryURL = initializeConfiguration(properties);
            }
        } catch (FileNotFoundException e) {
            RepositoryURL = initializeConfiguration(properties);
        } catch (IOException e) {
        }

        JSONConnection.repositoryURL = RepositoryURL;
        show(new DesktopApplication1View(this));
    }
    private String initializeConfiguration(Properties properties) {
        properties.setProperty("repository_url","http://192.168.56.2/versia/");
        try {
                properties.store(new FileOutputStream(ConfigurationFile), null);
        } catch (IOException ex) { }
        finally {
            return "http://192.168.56.2/versia/";
        }
    }
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DesktopApplication1
     */
    public static DesktopApplication1 getApplication() {
        return Application.getInstance(DesktopApplication1.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(DesktopApplication1.class, args);
    }
}
