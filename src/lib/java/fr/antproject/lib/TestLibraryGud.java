package fr.antproject.lib;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

public class TestLibraryGud {

    //griser l'image
    public void GrayFilter(BufferedImage colorImage){
        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(colorImage.getSource(), filter);
        Image mage = Toolkit.getDefaultToolkit().createImage(producer);
    }
    //faire un seuil tous les pixels qui sont en dessous d'une certain luminosité sont a 0 sinon ils sont a 200


    //contour l'image

}
