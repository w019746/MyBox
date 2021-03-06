package mara.mybox.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import mara.mybox.dev.MyBoxLog;
import mara.mybox.fxml.FxmlControl;
import mara.mybox.fxml.FxmlStage;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2020-11-27
 * @License Apache License Version 2.0
 */
public class MyBoxLogViewerController extends HtmlViewerController {

    public MyBoxLogViewerController() {
        baseTitle = AppVariables.message("MyBoxLogsViewer");
    }

    @Override
    public void afterSceneLoaded() {
        try {
            super.afterSceneLoaded();

            myStage.setWidth(600);
            myStage.setHeight(500);
            myStage.setY(5);
            myStage.setX(FxmlControl.getScreen().getWidth() - 610);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    public void dataAction(ActionEvent event) {
        MyBoxLogController controller = MyBoxLogController.oneOpen();
        if (controller != null) {
            controller.getMyStage().toFront();
        }
    }

    @FXML
    public void clearAction(ActionEvent event) {
        body = null;
        displayHtml(null);
    }

    public void addLog(MyBoxLog myboxLog) {
        body = (body != null ? body : "")
                + "</br><hr></br>\n" + myboxLog.html();
        loadBody(body);
    }

    public void setLogs(List<MyBoxLog> logs) {
        body = "";
        for (MyBoxLog log : logs) {
            body += "</br><hr></br>\n" + log.html();
        }
        loadBody(body);
    }

    public static MyBoxLogViewerController oneOpen() {
        MyBoxLogViewerController controller = null;
        Stage stage = FxmlStage.findStage(message("MyBoxLogsViewer"));
        if (stage != null && stage.getUserData() != null) {
            try {
                controller = (MyBoxLogViewerController) stage.getUserData();
            } catch (Exception e) {
            }
        }
        if (controller == null) {
            controller = (MyBoxLogViewerController) FxmlStage.openStage(CommonValues.MyBoxLogViewerFxml);
        }
        if (controller != null) {
            controller.getMyStage().toFront();
        }
        return controller;
    }

}
