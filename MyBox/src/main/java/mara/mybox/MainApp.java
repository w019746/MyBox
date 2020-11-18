package mara.mybox;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mara.mybox.controller.MyBoxLoadingController;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.logger;
import mara.mybox.value.CommonFxValues;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2020-11-7
 * @License Apache License Version 2.0
 */
public class MainApp extends Application {

    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            if (AppVariables.MyboxConfigFile == null
                    || !AppVariables.MyboxConfigFile.exists()
                    || !AppVariables.MyboxConfigFile.isFile()) {
                MyBoxSetup(stage);
            } else {
                MyBoxLoading(stage);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            stage.close();
        }
    }

    public static void MyBoxSetup(Stage stage) throws Exception {
        try {
            String lang = Locale.getDefault().getLanguage().toLowerCase();
            ResourceBundle bundle;
            if (lang.startsWith("zh")) {
                bundle = CommonValues.BundleZhCN;
            } else {
                bundle = CommonValues.BundleEn;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(CommonValues.MyBoxSetupFxml), bundle);
            Pane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("MyBox v" + CommonValues.AppVersion);
            stage.getIcons().add(CommonFxValues.AppIcon);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            logger.error(e.toString());
            stage.close();
        }
    }

    public static void MyBoxLoading(Stage stage) throws Exception {
        try {
            String lang = Locale.getDefault().getLanguage().toLowerCase();
            ResourceBundle bundle;
            if (lang.startsWith("zh")) {
                bundle = CommonValues.BundleZhCN;
            } else {
                bundle = CommonValues.BundleEn;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(CommonValues.MyBoxLoadingFxml), bundle);
            Pane pane = fxmlLoader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("MyBox v" + CommonValues.AppVersion);
            stage.getIcons().add(CommonFxValues.AppIcon);
            stage.setScene(scene);
            stage.show();
            MyBoxLoadingController loadController = (MyBoxLoadingController) fxmlLoader.getController();
            loadController.run();
        } catch (Exception e) {
            logger.error(e.toString());
            stage.close();
        }
    }

}
