package mara.mybox.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import mara.mybox.controller.ImageManufactureController.ImageOperation;
import mara.mybox.data.ConvolutionKernel;
import mara.mybox.data.DoubleRectangle;
import mara.mybox.data.StringTable;
import mara.mybox.dev.MyBoxLog;
import mara.mybox.fxml.FxmlControl;
import mara.mybox.fxml.FxmlStage;
import mara.mybox.image.ImageBinary;
import mara.mybox.image.ImageColor;
import mara.mybox.image.ImageConvolution;
import mara.mybox.image.ImageGray;
import mara.mybox.image.ImageManufacture;
import mara.mybox.image.ImageManufacture.Direction;
import mara.mybox.image.ImageMosaic;
import mara.mybox.image.ImageQuantization;
import mara.mybox.image.ImageQuantization.KMeansClusteringQuantization;
import mara.mybox.image.ImageQuantization.QuantizationAlgorithm;
import mara.mybox.image.ImageScope;
import mara.mybox.image.PixelsOperation;
import mara.mybox.image.PixelsOperation.OperationType;
import mara.mybox.image.file.ImageFileWriters;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonValues;

/**
 * @Author Mara
 * @CreateDate 2019-9-2
 * @Description
 * @License Apache License Version 2.0
 */
public class ImageManufactureEffectsController extends ImageManufactureOperationController {

    protected List<Color> quantizationColors;

    @FXML
    protected ImageManufactureEffectsOptionsController optionsController;
    @FXML
    protected Button demoButton, paletteAddButton;

    @Override
    public void initPane() {
        try {
            optionsController.setValues(this);

        } catch (Exception e) {
            MyBoxLog.error(e.toString());
        }
    }

    @Override
    protected void paneExpanded() {
        imageController.showRightPane();
        optionsController.checkEffectType();
        FxmlControl.setTooltip(paletteAddButton, message("AddInColorPalette"));
        paletteAddButton.setVisible(false);
    }

    @FXML
    @Override
    public void okAction() {
        quantizationColors = null;
        paletteAddButton.setVisible(false);
        optionsController.actualLoopLabel.setText("");
        if (imageController == null || optionsController.effectType == null) {
            return;
        }
        synchronized (this) {
            if (task != null && !task.isQuit()) {
                return;
            }
            task = new SingletonTask<Void>() {

                private Image newImage;
                private String value = null;
                private ImageQuantization quantization;
                private int actualLoop = -1;

                @Override
                protected boolean handle() {
                    try {
                        PixelsOperation pixelsOperation;
                        ImageConvolution imageConvolution;
                        switch (optionsController.effectType) {
                            case EdgeDetect:
                                if (optionsController.eightLaplaceRadio.isSelected()) {
                                    optionsController.kernel = ConvolutionKernel.makeEdgeDetectionEightNeighborLaplace();
                                } else if (optionsController.eightLaplaceExcludedRadio.isSelected()) {
                                    optionsController.kernel = ConvolutionKernel.makeEdgeDetectionEightNeighborLaplaceInvert();
                                } else if (optionsController.fourLaplaceRadio.isSelected()) {
                                    optionsController.kernel = ConvolutionKernel.makeEdgeDetectionFourNeighborLaplace();
                                } else if (optionsController.fourLaplaceExcludedRadio.isSelected()) {
                                    optionsController.kernel = ConvolutionKernel.makeEdgeDetectionFourNeighborLaplaceInvert();
                                } else {
                                    return false;
                                }
                                optionsController.kernel.setGray(optionsController.valueCheck.isSelected());
                                imageConvolution = ImageConvolution.create().
                                        setImage(imageView.getImage()).setScope(scopeController.scope).
                                        setKernel(optionsController.kernel);
                                newImage = imageConvolution.operateFxImage();
                                break;
                            case Emboss:
                                optionsController.kernel = ConvolutionKernel.makeEmbossKernel(
                                        optionsController.intPara1, optionsController.intPara2, optionsController.valueCheck.isSelected());
                                imageConvolution = ImageConvolution.create().
                                        setImage(imageView.getImage()).setScope(scopeController.scope).
                                        setKernel(optionsController.kernel);
                                newImage = imageConvolution.operateFxImage();
                                break;
                            case Quantization:
                                quantization = ImageQuantization.create(imageView.getImage(),
                                        scopeController.scope, optionsController.quantizationAlgorithm,
                                        optionsController.quanColors, optionsController.bitDepth,
                                        true, optionsController.quanDitherCheck.isSelected());
                                if (optionsController.quantizationAlgorithm == QuantizationAlgorithm.KMeansClustering) {
                                    KMeansClusteringQuantization q = (KMeansClusteringQuantization) quantization;
                                    q.getKmeans().setMaxIteration(optionsController.kmeansLoop);
                                    newImage = q.operateFxImage();
                                    actualLoop = q.getKmeans().getLoopCount();
                                } else {
                                    newImage = quantization.operateFxImage();
                                }
                                value = optionsController.intPara1 + "";
                                break;
                            case Thresholding:
                                pixelsOperation = PixelsOperation.create(imageView.getImage(), scopeController.scope, optionsController.effectType);
                                pixelsOperation.setIntPara1(optionsController.intPara1);
                                pixelsOperation.setIntPara2(optionsController.intPara2);
                                pixelsOperation.setIntPara3(optionsController.intPara3);
                                pixelsOperation.setIsDithering(false);
                                newImage = pixelsOperation.operateFxImage();
                                break;
                            case BlackOrWhite:
                                ImageBinary imageBinary;
                                switch (optionsController.intPara1) {
                                    case 2:
                                        imageBinary = new ImageBinary(imageView.getImage(), scopeController.scope, -1);
                                        break;
                                    case 3:
                                        imageBinary = new ImageBinary(imageView.getImage(), scopeController.scope, optionsController.intPara2);
                                        value = optionsController.intPara2 + "";
                                        break;
                                    default:
                                        int t = ImageBinary.calculateThreshold(imageView.getImage());
                                        imageBinary = new ImageBinary(imageView.getImage(), scopeController.scope, t);
                                        value = t + "";
                                        break;
                                }
                                imageBinary.setIsDithering(optionsController.valueCheck.isSelected());
                                newImage = imageBinary.operateFxImage();
                                break;
                            case Gray:
                                ImageGray imageGray = new ImageGray(imageView.getImage(), scopeController.scope);
                                newImage = imageGray.operateFxImage();
                                break;
                            case Sepia:
                                pixelsOperation = PixelsOperation.create(imageView.getImage(), scopeController.scope, optionsController.effectType);
                                pixelsOperation.setIntPara1(optionsController.intPara1);
                                newImage = pixelsOperation.operateFxImage();
                                value = optionsController.intPara1 + "";
                                break;
                            case Mosaic: {
                                ImageMosaic mosaic
                                        = ImageMosaic.create(imageView.getImage(), scopeController.scope, ImageMosaic.MosaicType.Mosaic, optionsController.intPara1);
                                newImage = mosaic.operateFxImage();
                                value = optionsController.intPara1 + "";
                            }
                            break;
                            case FrostedGlass: {
                                ImageMosaic mosaic
                                        = ImageMosaic.create(imageView.getImage(), scopeController.scope, ImageMosaic.MosaicType.FrostedGlass, optionsController.intPara1);
                                newImage = mosaic.operateFxImage();
                                value = optionsController.intPara1 + "";
                            }
                            break;
                            default:
                                return false;
                        }
                        if (task == null || isCancelled()) {
                            return false;
                        }
                        return newImage != null;
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override
                protected void whenSucceeded() {
                    imageController.popSuccessful();
                    imageController.updateImage(ImageOperation.Effects, optionsController.effectType.name(), value, newImage, cost);
                    if (quantization != null) {
                        String name = null;
                        if (imageController.sourceFile != null) {
                            name = imageController.sourceFile.getName();
                        }
                        StringTable table = quantization.countTable(name);
                        if (table != null && optionsController.quanDataCheck.isSelected()) {
                            HtmlViewerController controller
                                    = (HtmlViewerController) FxmlStage.openStage(CommonValues.HtmlViewerFxml);
                            controller.loadTable(table);
                        }
                        if (actualLoop >= 0) {
                            optionsController.actualLoopLabel.setText(message("ActualLoop") + ":" + actualLoop);
                        }
                        List<ImageQuantization.ColorCount> sortedCounts = quantization.getSortedCounts();
                        if (sortedCounts != null && !sortedCounts.isEmpty()) {
                            quantizationColors = new ArrayList<>();
                            for (int i = 0; i < sortedCounts.size(); ++i) {
                                ImageQuantization.ColorCount count = sortedCounts.get(i);
                                Color color = ImageColor.converColor(count.color);
                                quantizationColors.add(color);
                            }
                            paletteAddButton.setVisible(true);
                        }
                    }
                }
            };
            imageController.openHandlingStage(task, Modality.WINDOW_MODAL);
            task.setSelf(task);
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    protected void demo() {
        if (imageView.getImage() == null) {
            return;
        }
        imageController.popInformation(message("WaitAndHandling"));
        demoButton.setDisable(true);
        Task demoTask = new Task<Void>() {
            private List<String> files;

            @Override
            protected Void call() {

                try {
                    files = new ArrayList<>();
                    BufferedImage image = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                    image = ImageManufacture.scaleImageLess(image, 1000000);

                    PixelsOperation pixelsOperation;
                    ImageConvolution imageConvolution;
                    ConvolutionKernel kernel;
                    BufferedImage bufferedImage;
                    String tmpFile;

                    BufferedImage outlineSource = SwingFXUtils.fromFXImage(new Image("img/NetworkTools.png"), null);
                    ImageScope scope = new ImageScope(SwingFXUtils.toFXImage(image, null));
                    scope.setScopeType(ImageScope.ScopeType.Outline);
                    if (sourceFile != null) {
                        scope.setFile(sourceFile.getAbsolutePath());
                    }
                    scope.setRectangle(new DoubleRectangle(0, 0, image.getWidth(), image.getHeight()));
                    BufferedImage[] outline = ImageManufacture.outline(outlineSource,
                            scope.getRectangle(), image.getWidth(), image.getHeight(),
                            false, ImageColor.converColor(Color.WHITE), false);
                    scope.setOutlineSource(outlineSource);
                    scope.setOutline(outline[1]);

                    ImageQuantization quantization
                            = ImageQuantization.create(image, scope,
                                    QuantizationAlgorithm.PopularityQuantization, 16, 4, false, true);
                    bufferedImage = quantization.operateImage();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Posterizing") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    pixelsOperation = PixelsOperation.create(
                            image, scope, OperationType.Thresholding);
                    pixelsOperation.setIntPara1(128);
                    pixelsOperation.setIntPara2(0);
                    pixelsOperation.setIntPara3(255);
                    pixelsOperation.setIsDithering(false);
                    bufferedImage = pixelsOperation.operateImage();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Thresholding") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    ImageGray imageGray = new ImageGray(image, scope);
                    bufferedImage = imageGray.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Gray") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    pixelsOperation = PixelsOperation.create(
                            image, scope, OperationType.Sepia);
                    pixelsOperation.setIntPara1(60);
                    bufferedImage = pixelsOperation.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Sepia") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    ImageBinary imageBinary = new ImageBinary(imageView.getImage(), scope, -1);
                    imageBinary.setIsDithering(true);
                    bufferedImage = imageBinary.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("BlackOrWhite") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    kernel = ConvolutionKernel.makeEdgeDetectionEightNeighborLaplace();
                    imageConvolution = ImageConvolution.create().
                            setImage(image).setScope(scope).setKernel(kernel);
                    bufferedImage = imageConvolution.operateImage();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("EdgeDetection") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    kernel = ConvolutionKernel.makeEmbossKernel(Direction.Top, 3, true);
                    imageConvolution = ImageConvolution.create().
                            setImage(image).setScope(scope).setKernel(kernel);
                    bufferedImage = imageConvolution.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Emboss") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    ImageMosaic mosaic = ImageMosaic.create(
                            image, scope, ImageMosaic.MosaicType.Mosaic, 30);
                    bufferedImage = mosaic.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("Mosaic") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                    mosaic = ImageMosaic.create(image, scope,
                            ImageMosaic.MosaicType.FrostedGlass, 20);
                    bufferedImage = mosaic.operate();
                    tmpFile = AppVariables.MyBoxTempPath + File.separator
                            + message("FrostedGlass") + ".png";
                    if (ImageFileWriters.writeImageFile(bufferedImage, tmpFile)) {
                        files.add(tmpFile);
                    }

                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                demoButton.setDisable(false);
                if (files.isEmpty()) {
                    return;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ImagesBrowserController controller
                                    = (ImagesBrowserController) FxmlStage.openStage(CommonValues.ImagesBrowserFxml);
                            controller.loadFiles(files);
                        } catch (Exception e) {
                            MyBoxLog.error(e.toString());
                        }
                    }
                });

            }

        };
        Thread thread = new Thread(demoTask);
        thread.setDaemon(true);
        thread.start();

    }

    public void addColors() {
        if (quantizationColors == null || quantizationColors.isEmpty()) {
            return;
        }
        ColorPaletteManageController controller = ColorPaletteManageController.oneOpen();
        if (controller != null) {
            controller.addColors(quantizationColors);
        }
    }

}
