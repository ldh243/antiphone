package entity;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActivitiesLog entity.
 */
public class ActivitiesLogDTO implements Serializable {

    private Long activitiesLogID;

    private Instant activitiesLogDate;

    private Long activitiesLogPointReceived;

    private Long activitiesLogAchievedTime;

    private String username;


    public Long getActivitiesLogID() {
        return activitiesLogID;
    }

    public void setActivitiesLogID(Long activitiesLogID) {
        this.activitiesLogID = activitiesLogID;
    }

    public Instant getActivitiesLogDate() {
        return activitiesLogDate;
    }

    public void setActivitiesLogDate(Instant activitiesLogDate) {
        this.activitiesLogDate = activitiesLogDate;
    }

    public Long getActivitiesLogPointReceived() {
        return activitiesLogPointReceived;
    }

    public void setActivitiesLogPointReceived(Long activitiesLogPointReceived) {
        this.activitiesLogPointReceived = activitiesLogPointReceived;
    }

    public Long getActivitiesLogAchievedTime() {
        return activitiesLogAchievedTime;
    }

    public void setActivitiesLogAchievedTime(Long activitiesLogAchievedTime) {
        this.activitiesLogAchievedTime = activitiesLogAchievedTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivitiesLogDTO activitiesLogDTO = (ActivitiesLogDTO) o;
        if (activitiesLogDTO.getActivitiesLogID() == null || getActivitiesLogID() == null) {
            return false;
        }
        return Objects.equals(getActivitiesLogID(), activitiesLogDTO.getActivitiesLogID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getActivitiesLogID());
    }

    @Override
    public String toString() {
        return "ActivitiesLogDTO{" +
                "activitiesLogID=" + activitiesLogID +
                ", activitiesLogDate=" + activitiesLogDate +
                ", activitiesLogPointReceived=" + activitiesLogPointReceived +
                ", activitiesLogAchievedTime=" + activitiesLogAchievedTime +
                ", username='" + username + '\'' +
                '}';
    }
}
