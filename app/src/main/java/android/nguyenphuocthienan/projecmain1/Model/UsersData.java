package android.nguyenphuocthienan.projecmain1.Model;

public class UsersData {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String imageUrl;

    public UsersData() {
    }

    public UsersData(String userId, String username, String email, String phone, String imageURL) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.imageUrl = imageURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }
}
