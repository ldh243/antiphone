package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ListStore entity.
 */
public class StoreDTO implements Serializable {

    private Long storeID;

    private String storeName;

    private String storeAddress;

    private String storeLatitude;

    private String storeLongitude;

    private StoreGroupDTO storeGroup;


    public Long getStoreID() {
        return storeID;
    }

    public void setStoreID(Long storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(String storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public String getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public StoreGroupDTO getStoreGroup() {
        return storeGroup;
    }

    public void setStoreGroup(StoreGroupDTO storeGroup) {
        this.storeGroup = storeGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDTO storeDTO = (StoreDTO) o;
        return Objects.equals(storeID, storeDTO.storeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeID);
    }

    @Override
    public String toString() {
        return "ID: " + this.storeID + "\tName: " + this.storeName + "\tAddress: " + storeAddress;
    }
}
