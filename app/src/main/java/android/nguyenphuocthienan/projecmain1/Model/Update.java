package android.nguyenphuocthienan.projecmain1.Model;

public class Update {
    private String name,price,imageUrl;

    public Update(){
    }

    public Update(String name,String price, String imageUrl) {
        if (name.trim().equals("")){
            name = "No name";
        }
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
