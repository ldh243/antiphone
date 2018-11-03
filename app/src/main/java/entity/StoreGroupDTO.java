package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StoreGroup entity.
 */
public class StoreGroupDTO implements Serializable {

    private Long storeGroupID;

    private String storeGroupName;

    private Long storeGroupPhone;

    private String storeGroupMail;

    private String storeGroupWeb;

    private String storeGroupDescription;

    private StoreTypeDTO storeType;

    public Long getStoreGroupID() {
        return storeGroupID;
    }

    public void setStoreGroupID(Long storeGroupID) {
        this.storeGroupID = storeGroupID;
    }

    public String getStoreGroupName() {
        return storeGroupName;
    }

    public void setStoreGroupName(String storeGroupName) {
        this.storeGroupName = storeGroupName;
    }

    public Long getStoreGroupPhone() {
        return storeGroupPhone;
    }

    public void setStoreGroupPhone(Long storeGroupPhone) {
        this.storeGroupPhone = storeGroupPhone;
    }

    public String getStoreGroupMail() {
        return storeGroupMail;
    }

    public void setStoreGroupMail(String storeGroupMail) {
        this.storeGroupMail = storeGroupMail;
    }

    public String getStoreGroupWeb() {
        return storeGroupWeb;
    }

    public void setStoreGroupWeb(String storeGroupWeb) {
        this.storeGroupWeb = storeGroupWeb;
    }

    public String getStoreGroupDescription() {
        return storeGroupDescription;
    }

    public void setStoreGroupDescription(String storeGroupDescription) {
        this.storeGroupDescription = storeGroupDescription;
    }

    public StoreTypeDTO getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreTypeDTO storeType) {
        this.storeType = storeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreGroupDTO that = (StoreGroupDTO) o;
        return Objects.equals(storeGroupID, that.storeGroupID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeGroupID);
    }

    @Override
    public String toString() {
        return "StoreGroupDTO{" +
            "storeGroupID='" + storeGroupID + '\'' +
            ", storeGroupName='" + storeGroupName + '\'' +
            ", storeGroupPhone=" + storeGroupPhone +
            ", storeGroupMail='" + storeGroupMail + '\'' +
            ", storeGroupWeb='" + storeGroupWeb + '\'' +
            ", storeGroupDescription='" + storeGroupDescription + '\'' +
            ", storeType=" + storeType +
            '}';
    }
}
