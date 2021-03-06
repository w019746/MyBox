package mara.mybox.controller;

import com.sun.management.OperatingSystemMXBean;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mara.mybox.MainApp;
import mara.mybox.db.DerbyBase;
import mara.mybox.dev.MyBoxLog;
import static mara.mybox.fxml.FxmlControl.badStyle;
import mara.mybox.fxml.FxmlStage;
import mara.mybox.tools.ConfigTools;
import mara.mybox.tools.FileTools;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2020-11-7
 * @License Apache License Version 2.0
 */
public class MyBoxSetupController implements Initializable {

    protected Stage myStage;
    protected Scene myScene;
    protected String lang;
    protected int newJVM;
    protected File configPath;
    protected long totalM;

    @FXML
    protected Pane thisPane;
    @FXML
    protected SplitPane splitPane;
    @FXML
    protected Label titleLabel, fileLabel, currentJvmLabel;
    @FXML
    protected ListView<String> listView;
    @FXML
    protected TextField dataDirInput, jvmInput;
    @FXML
    protected Button openPathButton, okButton;
    @FXML
    protected RadioButton embeddedRadio, networkRadio;
    @FXML
    protected CheckBox hidpiCheck;

    public MyBoxSetupController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lang = Locale.getDefault().getLanguage().toLowerCase();
            if (AppVariables.MyboxConfigFile == null) {
                AppVariables.MyboxConfigFile = ConfigTools.defaultConfigFile();
            }

            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            totalM = osmxb.getTotalPhysicalMemorySize() / (1024 * 1024);

            makeListView();

            makeEditBox();

        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

    protected void makeListView() {
        try {
            configPath = new File(System.getProperty("user.home") + File.separator + "mybox");
            if (!configPath.isDirectory()) {
                FileTools.delete(configPath);
            }
            if (!configPath.exists()) {
                configPath.mkdirs();
            }
            listView.getItems().add(0, message(lang, "Default"));
            for (File file : configPath.listFiles()) {
                String fname = file.getName().toLowerCase();
                if (!file.isFile() || !fname.startsWith("mybox") || !fname.endsWith(".ini")) {
                    continue;
                }
                listView.getItems().add(file.getName());
            }
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String name) {
                    try {
                        if (name == null || name.isBlank()) {
                            return;
                        }
                        if (message(lang, "Default").equals(name)) {
                            dataDirInput.setText(configPath.getAbsolutePath() + File.separator + "data_v" + CommonValues.AppVersion);
                            embeddedRadio.fire();
                            hidpiCheck.setSelected(false);
                            final long jvmM = Runtime.getRuntime().maxMemory() / (1024 * 1024);
                            String m = message(lang, "PhysicalMemory") + ": " + totalM + "MB"
                                    + "    " + message(lang, "JvmXmx") + ": " + jvmM + "MB";
                            currentJvmLabel.setText(m);
                            jvmInput.setText(jvmM + "");
                        } else {
                            File file = new File(configPath + File.separator + name);
                            String MyBoxDataPath = ConfigTools.readValue(file, "MyBoxDataPath");
                            if (MyBoxDataPath != null) {
                                dataDirInput.setText(MyBoxDataPath);
                            }
                            String DerbyMode = ConfigTools.readValue(file, "DerbyMode");
                            if ("client".equals(DerbyMode)) {
                                networkRadio.fire();
                            } else {
                                embeddedRadio.fire();
                            }
                            String DisableHidpi = ConfigTools.readValue(file, "DisableHidpi");
                            hidpiCheck.setSelected("true".equals(DisableHidpi));
                            String JVMmemory = ConfigTools.readValue(file, "JVMmemory");
                            if (JVMmemory != null && JVMmemory.startsWith("-Xms") && JVMmemory.endsWith("m")) {
                                jvmInput.setText(JVMmemory.substring(4, JVMmemory.length() - 1));
                            }
                        }
                    } catch (Exception e) {
                        MyBoxLog.error(e.toString());
                    }
                }
            });
            listView.getSelectionModel().select(0);

        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

    protected void makeEditBox() {
        try {
            titleLabel.setText("MyBox v" + CommonValues.AppVersion);
            fileLabel.setText(AppVariables.MyboxConfigFile.getAbsolutePath());

            jvmInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    try {
                        int v = Integer.valueOf(jvmInput.getText());
                        if (v > 50 && v < totalM - 50) {
                            jvmInput.setStyle(null);
                            newJVM = v;
                        } else {
                            jvmInput.setStyle(badStyle);
                        }
                    } catch (Exception e) {
                        jvmInput.setStyle(badStyle);
                    }
                }
            });

            okButton.disableProperty().bind(
                    dataDirInput.textProperty().isEmpty()
                            .or(jvmInput.textProperty().isEmpty())
                            .or(jvmInput.styleProperty().isEqualTo(badStyle))
            );

        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

    public Stage getMyStage() {
        if (myStage == null) {
            if (thisPane != null) {
                myScene = thisPane.getScene();
                if (myScene != null) {
                    myStage = (Stage) myScene.getWindow();
                    myStage.setUserData(this);
                }
            }
        }
        return myStage;
    }

    @FXML
    protected void selectDataPath(ActionEvent event) {
        try {
            String defaultPath = AppVariables.MyboxDataPath != null
                    ? AppVariables.MyboxDataPath : ConfigTools.defaultDataPath();
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setInitialDirectory(new File(defaultPath));
            File directory = chooser.showDialog(getMyStage());
            if (directory == null) {
                return;
            }
            dataDirInput.setText(directory.getPath());
        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

    @FXML
    protected void openDataPath(ActionEvent event) {
        FxmlStage.browseURI(getMyStage(), configPath.toURI());
    }

    @FXML
    protected void okAction(ActionEvent event) {
        try {
            File dataPath = new File(dataDirInput.getText());
            if (!dataPath.exists()) {
                dataPath.mkdirs();
            } else if (!dataPath.isDirectory()) {
                FileTools.delete(dataPath);
                dataPath.mkdirs();
            }
            if (dataPath.exists() && dataPath.isDirectory()) {
                AppVariables.MyboxDataPath = dataPath.getAbsolutePath();
                ConfigTools.writeConfigValue("MyBoxDataPath", AppVariables.MyboxDataPath);
            } else {
                FxmlStage.alertError(getMyStage(),
                        MessageFormat.format(message(lang, "UserPathFail"), dataPath));
                return;
            }
            DerbyBase.mode = networkRadio.isSelected() ? "client" : "embedded";
            ConfigTools.writeConfigValue("DerbyMode", DerbyBase.mode);
            long jvmM = Runtime.getRuntime().maxMemory() / (1024 * 1024);
            if (newJVM != jvmM && newJVM > 50) {
                ConfigTools.writeConfigValue("JVMmemory", "-Xms" + newJVM + "m");
            }
            ConfigTools.writeConfigValue("DisableHidpi", hidpiCheck.isSelected() ? "true" : "false");

            MainApp.MyBoxLoading(getMyStage());
        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

}
