package sample;

import javassist.expr.Cast;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.settings.PandomiumSettings;
import org.panda_lang.pandomium.settings.PandomiumSettingsBuilder;
import org.panda_lang.pandomium.settings.categories.CommandLineSettings;
import org.panda_lang.pandomium.settings.categories.DependenciesSettings;
import org.panda_lang.pandomium.settings.categories.LoaderSettings;
import org.panda_lang.pandomium.settings.categories.NativesSettings;
import org.panda_lang.pandomium.util.os.PandomiumOSType;
import org.panda_lang.pandomium.wrapper.PandomiumBrowser;
import org.panda_lang.pandomium.wrapper.PandomiumClient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static PandomiumBrowser browser;
    private Pandomium pandomium;


    public Main() {
        final JFrame frame = new JFrame();


        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Tab 1", null);
        tab.addTab("Tab 2", null);


        frame.getContentPane().add(tab, BorderLayout.NORTH);

        setupPandomium(frame.getContentPane());

        tab.addChangeListener(e -> {
            System.out.println("Tab: " + tab.getSelectedIndex());
            frame.setTitle(stripWebsite(browser.getCefBrowser().getURL()));

        });



        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        frame.setSize(1720, 840);
        frame.setVisible(true);
    }

    /**
     * @param url
     * @return stripped url, no ".net" or ".com" ect
     */
    private String stripWebsite(String url) {

        return url.replace(".com", "").replace(".net", "").replace("https://", "").replace("http://", "").replace("/", "").replace("www.", "");
    }

    private void setupPandomium(Container contentPane) {
        PandomiumSettings settings = PandomiumSettingsUpdated.getDefaultSettingsBuilder().build();
        this.pandomium = new Pandomium(settings);
        pandomium.initialize();
        PandomiumClient client = pandomium.createClient();

        browser = client.loadURL("https://google.com"); //"C:\\JavaProjects\\JavaBroswer\\src\\main\\java\\sample\\home.html"
        contentPane.add(browser.toAWTComponent());
    }

    public static void main(String[] args) {
        new Main();
    }


    /**
     * @Author Si1kn
     * <p>
     * Needed for being able to update chromium. Project hasn't been updated since 2019.
     */
    private static class PandomiumSettingsUpdated extends PandomiumSettings {

        protected PandomiumSettingsUpdated(CommandLineSettings commandLine, DependenciesSettings dependencies, NativesSettings natives, LoaderSettings loader) {
            super(commandLine, dependencies, natives, loader);
        }

        public static PandomiumSettingsBuilder getDefaultSettingsBuilder() {
            return builder().dependencyURL(PandomiumOSType.OS_WINDOWS, "https://pandomium.panda-lang.org/download/natives/67.0/win64-natives.tar.xz").dependencyURL(PandomiumOSType.OS_MAC, "https://pandomium.panda-lang.org/download/natives/67.0/macosx64-natives.tar.xz").dependencyURL(PandomiumOSType.OS_LINUX, "https://pandomium.panda-lang.org/download/natives/67.0/linux64-natives.tar.xz").nativeDirectory("natives").loadAsync(false);
        }
    }
}
