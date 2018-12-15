package mara.mybox.controller;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import static mara.mybox.controller.BaseController.logger;
import mara.mybox.fxml.FxmlTools;
import mara.mybox.objects.ImageFileInformation;
import mara.mybox.tools.FileTools;
import mara.mybox.imagefile.ImageFileReaders;
import mara.mybox.objects.AppVaribles;
import static mara.mybox.objects.AppVaribles.getMessage;
import mara.mybox.objects.CommonValues;
import mara.mybox.objects.FileInformation;
import mara.mybox.objects.ImageAttributes;
import mara.mybox.objects.ImageInformation;
import mara.mybox.tools.DateTools;
import mara.mybox.tools.ValueTools;

/**
 * @Author Mara
 * @CreateDate 2018-6-24
 * @Description
 * @License Apache License Version 2.0
 */
public abstract class ImageBaseController extends BaseController {

    protected ImageInformation imageInformation;
    protected Image image;
    protected ImageAttributes attributes;
    protected Map<String, Long> sizes;

    protected class ProcessParameters {

        protected File sourceFile;
        protected int startIndex, currentIndex, currentTotalHandled;
        protected String status, targetPath, targetPrefix, targetRootPath, finalTargetName;
        protected Date startTime, endTime;
        protected boolean fill, aSize, aColor, aCompression, aQuality, isBatch, createSubDir;
    }

    protected ProcessParameters actualParameters, previewParameters, currentParameters;

    @FXML
    protected ScrollPane scrollPane;
    @FXML
    protected ImageView imageView;
    @FXML
    protected Pane imageConverterAttributes;
    @FXML
    protected ImageConverterAttributesController imageConverterAttributesController;

    public ImageBaseController() {
        targetPathKey = "ImageTargetPath";
        creatSubdirKey = "ImageCreatSubdir";
        fillZeroKey = "ImageFillZero";
        previewKey = "ImagePreview";
        sourcePathKey = "ImageSourcePath";
        appendColorKey = "ImageAppendColor";
        appendCompressionTypeKey = "ImageAppendCompressionType";
        appendDensityKey = "ImageAppendDensity";
        appendQualityKey = "ImageAppendQuality";
        appendSizeKey = "ImageAppendSize";

        fileExtensionFilter = CommonValues.ImageExtensionFilter;
    }

    @Override
    protected void initializeNext() {
        try {

            initializeNext2();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    protected void initializeNext2() {

    }

    @FXML
    @Override
    protected void startProcess(ActionEvent event) {
        isPreview = false;
        makeActualParameters();
        currentParameters = actualParameters;
        paused = false;
        doCurrentProcess();
    }

    @FXML
    protected void preview(ActionEvent event) {
        isPreview = true;
        makeActualParameters();
        previewParameters = copyParameters(actualParameters);
        previewParameters.status = "start";
        currentParameters = previewParameters;
        doCurrentProcess();
    }

    protected void doCurrentProcess() {

    }

    public void loadImage(final File file, final boolean onlyInformation) {
        sourceFile = file;
        imageInformation = null;
        image = null;
        final String fileName = file.getPath();
        getMyStage().setTitle(getBaseTitle() + " " + fileName);
        Task loadTask = new Task<Void>() {

            @Override
            protected Void call() {
                try {
                    ImageFileInformation imageFileInformation = ImageFileReaders.readImageFileMetaData(fileName);
                    if (imageFileInformation == null
                            || imageFileInformation.getImagesInformation() == null
                            || imageFileInformation.getImagesInformation().isEmpty()) {
                        return null;
                    }
                    imageInformation = imageFileInformation.getImageInformation();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            afterInfoLoaded();
                        }
                    });
                    if (imageFileInformation.getImagesInformation().size() > 1) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                handleMultipleImages();
                            }
                        });
                        return null;
                    }
                    sizes = new HashMap<>();
                    boolean needSampled = ImageFileReaders.needSampled(imageInformation, 1, sizes);
                    String format = FileTools.getFileSuffix(fileName).toLowerCase();
                    if (!"raw".equals(format) && !onlyInformation) {
                        BufferedImage bufferImage;
                        if (needSampled) {
                            bufferImage = ImageFileReaders.readFileByWidth(format, fileName, sizes.get("sampledWidth").intValue());
                        } else {
                            bufferImage = ImageIO.read(file);
                        }
                        image = SwingFXUtils.toFXImage(bufferImage, null);
                        imageInformation.setImage(image);
                        imageInformation.setIsSampled(needSampled);
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            afterImageLoaded();
                        }
                    });
                } catch (Exception e) {
                    logger.debug(e.toString());
                }
                return null;
            }
        };
        openHandlingStage(loadTask, Modality.WINDOW_MODAL);
        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void loadImage(final String fileName) {
        try {
            sourceFile = new File(fileName).getAbsoluteFile(); // Must convert to AbsoluteFile!
            if (sourceFileInput != null) {
                sourceFileInput.setText(sourceFile.getAbsolutePath());
            } else {
                loadImage(sourceFile, false);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public void loadImage(File sourceFile, Image image, ImageInformation imageInformation) {
        this.sourceFile = sourceFile;
        this.imageInformation = imageInformation;
        this.image = image;
        afterImageLoaded();
    }

    public void loadImage(ImageInformation imageInformation) {
        this.sourceFile = new File(imageInformation.getFilename());
        this.imageInformation = imageInformation;
        this.image = imageInformation.getImage();
        afterImageLoaded();
    }

    public void loadImage(final Image inImage) {
        sourceFile = null;
        imageInformation = null;
        image = inImage;
        afterImageLoaded();
    }

    protected void afterInfoLoaded() {

    }

    protected void afterImageLoaded() {

    }

    protected void handleMultipleImages() {

    }

    protected void showImageInformation(ImageInformation info) {
        try {
            if (info == null) {
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CommonValues.ImageInformationFxml), AppVaribles.CurrentBundle);
            Pane root = fxmlLoader.load();
            final ImageInformationController controller = fxmlLoader.getController();
            Stage imageInformationStage = new Stage();
            controller.setMyStage(imageInformationStage);
            imageInformationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (!controller.stageClosing()) {
                        event.consume();
                    }
                }
            });

            imageInformationStage.setTitle(getMyStage().getTitle());
            imageInformationStage.initModality(Modality.NONE);
            imageInformationStage.initStyle(StageStyle.DECORATED);
            imageInformationStage.initOwner(null);
            imageInformationStage.getIcons().add(CommonValues.AppIcon);
            imageInformationStage.setScene(new Scene(root));
            imageInformationStage.show();

            controller.loadInformation(info);

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    protected void showImageMetaData(ImageInformation info) {
        try {
            if (info == null) {
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CommonValues.ImageMetaDataFxml), AppVaribles.CurrentBundle);
            Pane root = fxmlLoader.load();
            final ImageMetaDataController controller = fxmlLoader.getController();
            Stage imageInformationStage = new Stage();
            controller.setMyStage(imageInformationStage);
            imageInformationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (!controller.stageClosing()) {
                        event.consume();
                    }
                }
            });

            imageInformationStage.setTitle(getMyStage().getTitle());
            imageInformationStage.initModality(Modality.NONE);
            imageInformationStage.initStyle(StageStyle.DECORATED);
            imageInformationStage.initOwner(null);
            imageInformationStage.getIcons().add(CommonValues.AppIcon);
            imageInformationStage.setScene(new Scene(root));
            imageInformationStage.show();

            controller.loadData(info);

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    protected void multipleFilesGenerated(final List<String> fileNames) {
        try {
            if (fileNames == null || fileNames.isEmpty()) {
                return;
            }
            String path = new File(fileNames.get(0)).getParent();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(getMyStage().getTitle());
            String info = MessageFormat.format(AppVaribles.getMessage("GeneratedFilesResult"),
                    fileNames.size(), "\"" + path + "\"");
            int num = fileNames.size();
            if (num > 10) {
                num = 10;
            }
            for (int i = 0; i < num; i++) {
                info += "\n    " + fileNames.get(i);
            }
            if (fileNames.size() > num) {
                info += "\n    ......";
            }
            getMyStage();
            alert.setContentText(info);
            ButtonType buttonOpen = new ButtonType(AppVaribles.getMessage("OpenTargetPath"));
            ButtonType buttonBrowse = new ButtonType(AppVaribles.getMessage("Browse"));
            ButtonType buttonBrowseNew = new ButtonType(AppVaribles.getMessage("BrowseInNew"));
            ButtonType buttonClose = new ButtonType(AppVaribles.getMessage("Close"));
            alert.getButtonTypes().setAll(buttonBrowseNew, buttonBrowse, buttonOpen, buttonClose);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonOpen) {
                Desktop.getDesktop().browse(new File(path).toURI());
            } else if (result.get() == buttonBrowse) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CommonValues.ImagesViewerFxml), AppVaribles.CurrentBundle);
                Pane pane = fxmlLoader.load();
                final ImagesViewerController controller = fxmlLoader.getController();
                controller.setMyStage(myStage);
                myStage.setScene(new Scene(pane));
                controller.setBaseTitle(AppVaribles.getMessage("MultipleImagesViewer"));
                controller.loadImages(fileNames);
            } else if (result.get() == buttonBrowseNew) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CommonValues.ImagesViewerFxml), AppVaribles.CurrentBundle);
                Pane pane = fxmlLoader.load();
                final ImagesViewerController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                controller.setMyStage(stage);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        if (!controller.stageClosing()) {
                            event.consume();
                        }
                    }
                });
                stage.setScene(new Scene(pane));
                stage.show();
                controller.setBaseTitle(AppVaribles.getMessage("MultipleImagesViewer"));
                controller.loadImages(fileNames);
            }

        } catch (Exception e) {
            logger.debug(e.toString());
        }

    }

    protected void makeActualParameters() {
        if (actualParameters != null && paused) {
            actualParameters.startIndex = actualParameters.currentIndex;
            return;
        }
        actualParameters = new ProcessParameters();
        actualParameters.currentIndex = 0;

        if (fillZero != null) {
            actualParameters.fill = fillZero.isSelected();
            AppVaribles.setUserConfigValue(fillZeroKey, actualParameters.fill);
        }

        if (targetSelectionController != null) {
            actualParameters.targetRootPath = targetSelectionController.targetPathInput.getText();
            actualParameters.targetPath = actualParameters.targetRootPath;
            if (targetSelectionController.subdirCheck != null) {
                actualParameters.createSubDir = targetSelectionController.subdirCheck.isSelected();
                AppVaribles.setUserConfigValue(creatSubdirKey, actualParameters.createSubDir);
            }
        }
        if (targetPrefixInput != null) {
            actualParameters.targetPrefix = targetFileInput.getText();
        }

        if (imageConverterAttributesController != null) {
            actualParameters.aSize = appendSize.isSelected();
            actualParameters.aColor = appendColor.isSelected();
            actualParameters.aCompression = appendCompressionType.isSelected();
            actualParameters.aQuality = appendQuality.isSelected();

            AppVaribles.setUserConfigValue(appendSizeKey, actualParameters.aSize);
            AppVaribles.setUserConfigValue(appendColorKey, actualParameters.aColor);
            AppVaribles.setUserConfigValue(appendCompressionTypeKey, actualParameters.aCompression);
            AppVaribles.setUserConfigValue(appendQualityKey, actualParameters.aQuality);
        }

        makeMoreParameters();
    }

    protected void makeMoreParameters() {

    }

    protected void makeSingleParameters() {
        actualParameters.isBatch = false;

        sourceFiles = new ArrayList();
        sourceFiles.add(sourceFile);
        actualParameters.sourceFile = sourceFile;

        if (targetSelectionController != null) {
            if (targetSelectionController.targetPrefixInput != null) {
                actualParameters.targetPrefix = targetSelectionController.targetPrefixInput.getText();
            }
            if (targetSelectionController.targetFileInput != null) {
                actualParameters.targetPrefix = targetSelectionController.targetFileInput.getText();
            }
        }

    }

    protected void makeBatchParameters() {
        actualParameters.isBatch = true;

        sourceFilesInformation = filesTableController.getTableData();
        if (sourceFilesInformation == null || sourceFilesInformation.isEmpty()) {
            actualParameters = null;
            return;
        }
        sourceFiles = new ArrayList();
        for (FileInformation f : sourceFilesInformation) {
            sourceFiles.add(new File(f.getFileName()));
        }
    }

    protected ProcessParameters copyParameters(ProcessParameters theConversion) {
        ProcessParameters newConversion = new ProcessParameters();
        newConversion.aColor = theConversion.aColor;
        newConversion.aCompression = theConversion.aCompression;
        newConversion.aSize = theConversion.aSize;
        newConversion.aQuality = theConversion.aQuality;
        newConversion.currentTotalHandled = theConversion.currentTotalHandled;
        newConversion.sourceFile = theConversion.sourceFile;
        newConversion.targetRootPath = theConversion.targetRootPath;
        newConversion.targetPath = theConversion.targetPath;
        newConversion.targetPrefix = theConversion.targetPrefix;
        newConversion.fill = theConversion.fill;
        newConversion.createSubDir = theConversion.createSubDir;
        newConversion.status = theConversion.status;
        newConversion.startTime = theConversion.startTime;
        newConversion.isBatch = theConversion.isBatch;
        return newConversion;
    }

    @Override
    protected void updateInterface(final String newStatus) {
        currentParameters.status = newStatus;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    if (operationBarController.fileProgressBar != null) {
                        operationBarController.fileProgressBar.setProgress(currentParameters.currentIndex / sourceFiles.size());
                        operationBarController.fileProgressValue.setText(currentParameters.currentIndex + " / " + sourceFiles.size());
                    }
                    switch (newStatus) {
                        case "StartFile":
                            operationBarController.statusLabel.setText(currentParameters.sourceFile.getName() + " "
                                    + getMessage("Handling...") + " "
                                    + getMessage("StartTime")
                                    + ": " + DateTools.datetimeToString(currentParameters.startTime));
                            if (operationBarController.fileProgressBar != null) {
                                operationBarController.fileProgressBar.setProgress(currentParameters.currentIndex / sourceFiles.size());
                                operationBarController.fileProgressValue.setText(currentParameters.currentIndex + " / " + sourceFiles.size());
                            }
                            break;

                        case "Started":
                            operationBarController.startButton.setText(AppVaribles.getMessage("Cancel"));
                            operationBarController.startButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    cancelProcess(event);
                                }
                            });
                            operationBarController.pauseButton.setVisible(true);
                            operationBarController.pauseButton.setDisable(false);
                            operationBarController.pauseButton.setText(AppVaribles.getMessage("Pause"));
                            operationBarController.pauseButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    pauseProcess(event);
                                }
                            });
                            paraBox.setDisable(true);
                            break;

                        case "CompleteFile":
                            showCost();
                            if (operationBarController.fileProgressBar != null) {
                                operationBarController.fileProgressBar.setProgress(currentParameters.currentIndex / sourceFiles.size());
                                operationBarController.fileProgressValue.setText(currentParameters.currentIndex + " / " + sourceFiles.size());
                            }
                            break;

                        case "Done":
                            if (isPreview || !currentParameters.isBatch) {
                                if (currentParameters.finalTargetName == null
                                        || !new File(currentParameters.finalTargetName).exists()) {
                                    alertInformation(AppVaribles.getMessage("NoDataNotSupported"));
                                } else {
                                    openImageManufactureInNew(currentParameters.finalTargetName);
                                }
                            }

                        default:
                            if (paused) {
                                operationBarController.startButton.setText(AppVaribles.getMessage("Cancel"));
                                operationBarController.startButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        cancelProcess(event);
                                    }
                                });
                                operationBarController.pauseButton.setVisible(true);
                                operationBarController.pauseButton.setDisable(false);
                                operationBarController.pauseButton.setText(AppVaribles.getMessage("Continue"));
                                operationBarController.pauseButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        startProcess(event);
                                    }
                                });
                                paraBox.setDisable(true);
                            } else {
                                operationBarController.startButton.setText(AppVaribles.getMessage("Start"));
                                operationBarController.startButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        startProcess(event);
                                    }
                                });
                                operationBarController.pauseButton.setVisible(false);
                                operationBarController.pauseButton.setDisable(true);
                                paraBox.setDisable(false);
                            }
                            showCost();
                            if (operationBarController.miaoCheck.isSelected()) {
                                FxmlTools.miao3();
                            }

                    }

                } catch (Exception e) {
                    logger.error(e.toString());
                }

            }
        });

    }

    protected void showCost() {
        if (operationBarController.statusLabel == null) {
            return;
        }
        long cost = (new Date().getTime() - currentParameters.startTime.getTime()) / 1000;
        double avg = 0;
        if (currentParameters.currentTotalHandled != 0) {
            avg = ValueTools.roundDouble3((double) cost / currentParameters.currentTotalHandled);
        }
        String s;
        if (paused) {
            s = getMessage("Paused");
        } else {
            s = getMessage(currentParameters.status);
        }
        s += ". " + getMessage("HandledThisTime") + ": " + currentParameters.currentTotalHandled + " "
                + getMessage("Cost") + ": " + cost + " " + getMessage("Seconds") + ". "
                + getMessage("Average") + ": " + avg + " " + getMessage("SecondsPerItem") + ". "
                + getMessage("StartTime") + ": " + DateTools.datetimeToString(currentParameters.startTime) + ", "
                + getMessage("EndTime") + ": " + DateTools.datetimeToString(new Date());
        operationBarController.statusLabel.setText(s);
    }

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ImageAttributes attributes) {
        this.attributes = attributes;
    }

    public ProcessParameters getActualParameters() {
        return actualParameters;
    }

    public void setActualParameters(ProcessParameters actualParameters) {
        this.actualParameters = actualParameters;
    }

    public ProcessParameters getPreviewParameters() {
        return previewParameters;
    }

    public void setPreviewParameters(ProcessParameters previewParameters) {
        this.previewParameters = previewParameters;
    }

    public ProcessParameters getCurrentParameters() {
        return currentParameters;
    }

    public void setCurrentParameters(ProcessParameters currentParameters) {
        this.currentParameters = currentParameters;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Map<String, Long> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, Long> sizes) {
        this.sizes = sizes;
    }

}
