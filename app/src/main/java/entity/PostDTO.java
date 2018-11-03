package entity;

public class PostDTO {
    private int postID;
    private int imageDir;
    private String banner;
    private String name;
    private String title;
    private double originalPrice;
    private int discountRate;
    private String description;
    private String startDate;
    private String toDate;
    private int totalNumber;
    private int remainNumber;

    public int getImageDir() {
        return imageDir;
    }

    public void setImageDir(int imageDir) {
        this.imageDir = imageDir;
    }
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getRemainNumber() {
        return remainNumber;
    }

    public void setRemainNumber(int remainNumber) {
        this.remainNumber = remainNumber;
    }

    public PostDTO() {
    }

    public PostDTO(int postID, String name, int discountRate) {
        this.postID = postID;
        this.name = name;
        this.discountRate = discountRate;
    }

    public PostDTO(int postID, int imageDir, String name, String title, int discountRate) {
        this.postID = postID;
        this.imageDir = imageDir;
        this.name = name;
        this.title = title;
        this.discountRate = discountRate;
    }

    public PostDTO(int postID, String banner, String name, String title, double originalPrice, int discountRate, String description, String startDate, String toDate, int totalNumber, int remainNumber) {
        this.postID = postID;
        this.banner = banner;
        this.name = name;
        this.title = title;
        this.originalPrice = originalPrice;
        this.discountRate = discountRate;
        this.description = description;
        this.startDate = startDate;
        this.toDate = toDate;
        this.totalNumber = totalNumber;
        this.remainNumber = remainNumber;
    }
}
