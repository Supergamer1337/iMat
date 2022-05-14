package com.example.imat.utils;

import com.example.imat.IMat;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class StyleUtils {
    public static void toggleHoverImage(boolean isHovered, String hoverPath, String normalPath, ImageView imageView) {
        Image image = new Image(isHovered ? IMat.class.getResourceAsStream(hoverPath) : IMat.class.getResourceAsStream(normalPath));
        imageView.setImage(image);
    }

    public static void roundBackgroundImage(AnchorPane imagePane, double amount ) {
        Rectangle rect = new Rectangle(180,185);
        rect.setArcHeight(amount);
        rect.setArcWidth(amount);
        imagePane.setClip(rect);
    }

    public static void roundBackgroundImage(AnchorPane imagePane) {
        roundBackgroundImage(imagePane, 30.0);
    }

    public static void coverBackgroundImage(AnchorPane imagePane, String imagePath) {
        imagePane.setStyle(
                "-fx-background-image: url('" + imagePath + "');"
                        + "-fx-background-size: auto 100%;"
                        + "-fx-background-position: center center;"
                        + "-fx-background-repeat: no-repeat;"
        );
    }
}
