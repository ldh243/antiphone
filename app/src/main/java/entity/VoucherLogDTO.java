package entity;

public class VoucherLogDTO {

    public VoucherLogDTO() {
    }

    public VoucherLogDTO(int voucherLogID, boolean voucherLogUserUsed, String date, int userID, int voucherID) {
        this.voucherLogID = voucherLogID;
        this.voucherLogUserUsed = voucherLogUserUsed;
        this.date = date;
        this.userID = userID;
        this.voucherID = voucherID;
    }

    public VoucherLogDTO(boolean voucherLogUserUsed, String date, int userID, int voucherID) {
        this.voucherLogUserUsed = voucherLogUserUsed;
        this.date = date;
        this.userID = userID;
        this.voucherID = voucherID;
    }

    private int voucherLogID;
    private boolean voucherLogUserUsed;
    private String date;
    private int userID;
    private int voucherID;

    public int getVoucherLogID() {
        return voucherLogID;
    }

    public void setVoucherLogID(int voucherLogID) {
        this.voucherLogID = voucherLogID;
    }

    public boolean isVoucherLogUserUsed() {
        return voucherLogUserUsed;
    }

    public void setVoucherLogUserUsed(boolean voucherLogUserUsed) {
        this.voucherLogUserUsed = voucherLogUserUsed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }
}
