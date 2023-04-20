package service;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.PictureNode;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class ChangeService {

    public static int howChange=0;
    public static Stage stage;
    public static ArrayList<PictureNode> files;
    public static ArrayList<PictureNode> selectedPictures;
    public static File file;
    public static ImageView origin,change;
    public static double originHeight,originWidth;
}
