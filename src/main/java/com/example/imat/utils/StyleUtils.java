package com.example.imat.utils;

import com.example.imat.IMat;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StyleUtils {
    public static void toggleHoverImage(boolean isHovered, String hoverPath, String normalPath, ImageView imageView) {
        Image image = new Image(isHovered ? IMat.class.getResourceAsStream(hoverPath) : IMat.class.getResourceAsStream(normalPath));
        imageView.setImage(image);
    }

}
