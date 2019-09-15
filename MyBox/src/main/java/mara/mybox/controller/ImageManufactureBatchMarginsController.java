package mara.mybox.controller;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import mara.mybox.fxml.FxmlControl;
import static mara.mybox.fxml.FxmlControl.badStyle;
import mara.mybox.fxml.FxmlImageManufacture;
import mara.mybox.image.ImageManufacture;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.logger;
import static mara.mybox.value.AppVariables.message;

/**
 * @Author Mara
 * @CreateDate 2018-9-26
 * @Description
 * @License Apache License Version 2.0
 */
public class ImageManufactureBatchMarginsController extends ImageManufactureBatchController {

    protected int width, distance;
    private ImageManufactureMarginsController.OperationType opType;

    @FXML
    protected ToggleGroup opGroup;
    @FXML
    protected ComboBox marginWidthBox;
    @FXML
    protected ColorPicker marginsColorPicker;
    @FXML
    protected CheckBox marginsTopCheck, marginsBottomCheck, marginsLeftCheck, marginsRightCheck,
            preAlphaCheck;
    @FXML
    private HBox colorBox, distanceBox, widthBox, setBox;
    @FXML
    private TextField distanceInput;
    @FXML
    protected RadioButton blurMarginsRadio;
    @FXML
    protected ImageView preAlphaTipsView;

    public ImageManufactureBatchMarginsController() {
        baseTitle = AppVariables.message("ImageManufactureBatchMargins");
    }

    @Override
    public void initializeNext() {
        try {

            startButton.disableProperty().unbind();
            startButton.disableProperty().bind(Bindings.isEmpty(targetPathInput.textProperty())
                    .or(targetPathInput.styleProperty().isEqualTo(badStyle))
                    .or(Bindings.isEmpty(tableView.getItems()))
                    .or(marginWidthBox.getEditor().styleProperty().isEqualTo(badStyle))
                    .or(marginsTopCheck.styleProperty().isEqualTo(badStyle))
            );

        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    @Override
    public void afterSceneLoaded() {
        super.afterSceneLoaded();

        checkOperationType();
        distanceInput.setText("20");
        marginWidthBox.getSelectionModel().select(0);

    }

    @Override
    public void initOptionsSection() {
        try {
            opGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov,
                        Toggle old_toggle, Toggle new_toggle) {
                    checkOperationType();
                }
            });

            distanceInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    checkColor();
                }
            });

            marginWidthBox.getItems().addAll(Arrays.asList(
                    "50", "20", "10", "5", "100", "200", "300", "150", "500"));
            marginWidthBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String oldValue, String newValue) {
                    if (isSettingValues) {
                        return;
                    }
                    checkMarginWidth();
                }
            });

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    private void checkOperationType() {
        setBox.getChildren().clear();

        RadioButton selected = (RadioButton) opGroup.getSelectedToggle();
        if (message("AddMargins").equals(selected.getText())) {
            opType = ImageManufactureMarginsController.OperationType.AddMargins;
            setBox.getChildren().addAll(colorBox, widthBox);
            checkMarginWidth();
            distanceInput.setStyle(null);

        } else if (message("CutMarginsByWidth").equals(selected.getText())) {
            opType = ImageManufactureMarginsController.OperationType.CutMarginsByWidth;
            setBox.getChildren().addAll(widthBox);
            checkMarginWidth();
            distanceInput.setStyle(null);

        } else if (message("CutMarginsByColor").equals(selected.getText())) {
            opType = ImageManufactureMarginsController.OperationType.CutMarginsByColor;
            setBox.getChildren().addAll(colorBox, distanceBox);
            marginWidthBox.getEditor().setStyle(null);
            checkColor();

        } else if (message("BlurMargins").equals(selected.getText())) {
            opType = ImageManufactureMarginsController.OperationType.BlurMargins;
            setBox.getChildren().addAll(widthBox, preAlphaTipsView, preAlphaCheck);
            checkMarginWidth();
            distanceInput.setStyle(null);

        }
        FxmlControl.refreshStyle(setBox);

    }

    private void checkMarginWidth() {
        try {
            width = Integer.valueOf((String) marginWidthBox.getSelectionModel().getSelectedItem());
            if (width > 0) {
                FxmlControl.setEditorNormal(marginWidthBox);
            } else {
                width = 0;
                FxmlControl.setEditorBadStyle(marginWidthBox);
            }

        } catch (Exception e) {
            width = 0;
            FxmlControl.setEditorBadStyle(marginWidthBox);
        }
    }

    protected void checkColor() {
        try {
            distance = Integer.valueOf(distanceInput.getText());
            distanceInput.setStyle(null);
            if (distance >= 0 && distance <= 255) {
                distanceInput.setStyle(null);
            } else {
                distanceInput.setStyle(badStyle);
                distance = 0;
            }
        } catch (Exception e) {
            distanceInput.setStyle(badStyle);
            distance = 0;
        }
    }

    @FXML
    public void setTransparentAction() {
        marginsColorPicker.setValue(Color.TRANSPARENT);
    }

    @FXML
    public void setBlackAction() {
        marginsColorPicker.setValue(Color.BLACK);
    }

    @FXML
    public void setWhiteAction() {
        marginsColorPicker.setValue(Color.WHITE);
    }

    @Override
    protected BufferedImage handleImage(BufferedImage source) {
        try {
            BufferedImage target;
            switch (opType) {
                case CutMarginsByWidth:
                    target = ImageManufacture.cutMargins(source,
                            FxmlImageManufacture.toAwtColor(marginsColorPicker.getValue()),
                            marginsTopCheck.isSelected(), marginsBottomCheck.isSelected(),
                            marginsLeftCheck.isSelected(), marginsRightCheck.isSelected());
                    break;
                case CutMarginsByColor:
                    target = ImageManufacture.cutMargins(source,
                            width,
                            marginsTopCheck.isSelected(), marginsBottomCheck.isSelected(),
                            marginsLeftCheck.isSelected(), marginsRightCheck.isSelected());
                    break;
                case AddMargins:
                    target = ImageManufacture.addMargins(source,
                            FxmlImageManufacture.toAwtColor(marginsColorPicker.getValue()), width,
                            marginsTopCheck.isSelected(), marginsBottomCheck.isSelected(),
                            marginsLeftCheck.isSelected(), marginsRightCheck.isSelected());
                    break;
                case BlurMargins:
                    if (preAlphaCheck.isSelected()) {
                        target = ImageManufacture.blurMarginsNoAlpha(source,
                                width,
                                marginsTopCheck.isSelected(), marginsBottomCheck.isSelected(),
                                marginsLeftCheck.isSelected(), marginsRightCheck.isSelected());

                    } else {
                        target = ImageManufacture.blurMarginsAlpha(source,
                                width,
                                marginsTopCheck.isSelected(), marginsBottomCheck.isSelected(),
                                marginsLeftCheck.isSelected(), marginsRightCheck.isSelected());

                    }
                    break;
                default:
                    return null;
            }

            return target;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }

    }
}
