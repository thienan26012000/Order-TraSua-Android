package android.nguyenphuocthienan.projecmain1.Images;

public class ImageList {
    private String imageUrl;

    // Constructor
    public ImageList() {
    }
    public ImageList(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Get && Set
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
