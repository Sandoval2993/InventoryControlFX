package view;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageProcessor {
    private Image image;
    private File imageFile;
    private byte[] imageBytes;

    public ImageProcessor() throws IOException {
        this.imageFile = new File("src/img/addImage-x512.png");
        this.imageBytes = convertImageToBytes(this.imageFile);
        this.image = buildImage(this.imageBytes);
//        this.image = new Image(this.imageFile.toURI().toString());
    }

    public ImageProcessor(File imageFile) throws IOException {
        this.imageFile = imageFile;
        this.imageBytes = convertImageToBytes(this.imageFile);
        this.image = buildImage(this.imageBytes);
    }

    public ImageProcessor(byte[] imageBytes) {
        this.imageBytes = imageBytes;
        this.image = buildImage(this.imageBytes);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Image buildImage(byte[] bytes) {
        Image image = new Image(new ByteArrayInputStream(bytes));
        return image;
    }

    public byte[] convertImageToBytes(File file) throws IOException {
        FileInputStream fileInputStream = null;
        byte[] bytes = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return bytes;
    }
}
