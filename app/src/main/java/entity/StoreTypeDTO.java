package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StoreType entity.
 */
public class StoreTypeDTO implements Serializable {


    private String storeTypeID;

    private String storeTypeName;

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreTypeDTO that = (StoreTypeDTO) o;
        return Objects.equals(storeTypeID, that.storeTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeTypeID);
    }

    @Override
    public String toString() {
        return "StoreTypeDTO{" +
            "storeTypeID='" + storeTypeID + '\'' +
            ", storeTypeName='" + storeTypeName + '\'' +
            '}';
    }
}
