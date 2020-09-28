package ascione.agata.service.singleton;

import android.graphics.Bitmap;

public class ImageSingleton {

    private Bitmap image;

    private static final ImageSingleton ourInstance = new ImageSingleton();

    public static ImageSingleton getInstance() {
        return ourInstance;
    }

    private ImageSingleton() {
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
