package entity;

import java.io.Serializable;
import java.util.Objects;

public class VoucherDTO implements Serializable {

    private Long voucherID;

    private String voucherNumber;

    private Boolean voucherStatus;

    private Long voucherPointRequired;

    private PostDTO post;

    public Long getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(Long voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Boolean isVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(Boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public Long getVoucherPointRequired() {
        return voucherPointRequired;
    }

    public void setVoucherPointRequired(Long voucherPointRequired) {
        this.voucherPointRequired = voucherPointRequired;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherDTO that = (VoucherDTO) o;
        return Objects.equals(voucherID, that.voucherID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherID);
    }

    @Override
    public String toString() {
        return "VoucherDTO{" +
                "voucherID=" + voucherID +
                ", voucherNumber='" + voucherNumber + '\'' +
                ", voucherStatus=" + voucherStatus +
                ", voucherPointRequired=" + voucherPointRequired +
                ", post=" + post +
                '}';
    }
}