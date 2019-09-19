package mara.mybox;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import mara.mybox.tools.ConfigTools;
import mara.mybox.tools.SystemTools;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.logger;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2019-1-22 14:35:50
 * @Version 1.0
 * @Description
 * @License Apache License Version 2.0
 */
public class MyBox {

    public static String InternalRestartFlag = "MyBoxInternalRestarting";

    // To pass arguments to JavaFx GUI
    // https://stackoverflow.com/questions/33549820/javafx-not-calling-mainstring-args-method/33549932#33549932
    public static void main(String[] args) {
        AppVariables.appArgs = args.clone();

        launchApp();
    }

    public static void launchApp() {
        logger.info("Starting Mybox...");
        logger.info("JVM path: " + System.getProperty("java.home"));

        if (setJVMmemory() && !internalRestart()) {
            restart();

        } else {
            initEnv();
            Application.launch(MainApp.class, AppVariables.appArgs);
        }
    }

    public static String defaultDataPath() {
        File path = new File(System.getProperty("user.dir") + File.separator + "MyBoxData");
        if (!path.exists()) {
            path.mkdirs();
        } else if (!path.isDirectory()) {
            path.delete();
            path.mkdirs();
        }
        if (!path.exists()) {
            path = new File(System.getProperty("user.home") + File.separator + "MyBoxData");
            if (!path.exists()) {
                path.mkdirs();
            } else if (!path.isDirectory()) {
                path.delete();
                path.mkdirs();
            }
        }
        return path.getAbsolutePath();
    }

    public static boolean internalRestart() {
        return AppVariables.appArgs != null
                && AppVariables.appArgs.length > 0
                && InternalRestartFlag.equals(AppVariables.appArgs[0]);
    }

    public static boolean setJVMmemory() {
        String JVMmemory = ConfigTools.readConfigValue("JVMmemory");
        if (JVMmemory == null) {
            return false;
        }
        if (AppVariables.appArgs == null || AppVariables.appArgs.length == 0) {
            return true;
        }
        for (String s : AppVariables.appArgs) {
            if (s.startsWith("-Xms")) {
                return false;
            }
        }
        return true;
    }

    // Set properties before JavaFx starting to make sure they take effect against JavaFX
    public static void initEnv() {
        try {

            // https://pdfbox.apache.org/2.0/getting-started.html
//            System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
            System.setProperty("org.apache.pdfbox.rendering.UsePureJavaCMYKConversion", "true");

            // https://stackoverflow.com/questions/47613006/how-to-disable-scaling-the-ui-on-windows-for-java-9-applications?r=SearchResults
//            System.setProperty("sun.java2d.uiScale", "1.0");
            System.setProperty("prism.allowhidpi", "true".equals(ConfigTools.readConfigValue("DisableHidpi")) ? "false" : "true");

            String rootPath = ConfigTools.readConfigValue("MyBoxDataPath");
            if (rootPath != null) {
                File path = new File(rootPath);
                if (path.exists() && path.isDirectory()) {
                    AppVariables.MyboxDataPath = path.getAbsolutePath();
                } else {
                    AppVariables.MyboxDataPath = defaultDataPath();
                }
            } else {
                AppVariables.MyboxDataPath = defaultDataPath();
            }
            logger.info("MyBox Data Path:" + AppVariables.MyboxDataPath);

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    // Restart with  parameters. Use "ProcessBuilder", instead of "Runtime.getRuntime().exec" which is not safe
    // https://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-application?r=SearchResults
    public static void restart() {
        try {
            String javaHome = System.getProperty("java.home");
            File boundlesJar;
            String os = System.getProperty("os.name").toLowerCase();
            if (SystemTools.isMac()) {
                boundlesJar = new File(javaHome.substring(0, javaHome.length() - "runtime/Contents/Home".length())
                        + "Java" + File.separator + "MyBox-" + CommonValues.AppVersion + ".jar");
            } else {
                boundlesJar = new File(javaHome.substring(0, javaHome.length() - "runtime".length())
                        + "app" + File.separator + "MyBox-" + CommonValues.AppVersion + ".jar");

            }
            if (boundlesJar.exists()) {
                restartBundles(boundlesJar);
            } else {
                restartJar();
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public static void restartBundles(File jar) {
        try {
            logger.info("Restarting Mybox bundles...");

            List<String> commands = new ArrayList<>();
            commands.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");

            String JVMmemory = ConfigTools.readConfigValue("JVMmemory");
            if (JVMmemory != null) {
                commands.add(JVMmemory);
            }

            commands.add("-jar");
            commands.add(jar.getAbsolutePath());

            commands.add(InternalRestartFlag);
            if (AppVariables.appArgs != null) {
                for (String arg : AppVariables.appArgs) {
                    if (arg != null) {
                        commands.add(arg);
                    }
                }
            }

            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.start();

            System.exit(0);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public static void restartJar() {
        try {
            logger.info("Restarting Mybox Jar package...");
            List<String> commands = new ArrayList<>();
            commands.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");

            List<String> jvmArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
            for (String jvmArg : jvmArgs) {
                if (jvmArg != null) {
                    commands.add(jvmArg);
                }
            }

            commands.add("-cp");
            commands.add(ManagementFactory.getRuntimeMXBean().getClassPath());

            String JVMmemory = ConfigTools.readConfigValue("JVMmemory");
            if (JVMmemory != null) {
                commands.add(JVMmemory);
            }

            commands.add(MyBox.class.getName());

            commands.add(InternalRestartFlag);
            if (AppVariables.appArgs != null) {
                for (String arg : AppVariables.appArgs) {
                    if (arg != null) {
                        commands.add(arg);
                    }
                }
            }

            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.start();

            System.exit(0);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

}
