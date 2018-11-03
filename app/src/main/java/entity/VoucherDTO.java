package entity;

public class VoucherDTO {
    private int id;
    private boolean status;
    private int point;
    private String codeNumber;
    private int postID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public VoucherDTO( ) {
    }

    public VoucherDTO(int id, boolean status, int point, String codeNumber, int postID) {

        this.id = id;
        this.status = status;
        this.point = point;
        this.codeNumber = codeNumber;
        this.postID = postID;
    }
}
