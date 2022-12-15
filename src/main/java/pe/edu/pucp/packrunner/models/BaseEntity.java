package pe.edu.pucp.packrunner.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  @Getter
  @Setter
  private long id;

  @Getter
  @Setter
  @Column(name = "code")
  private String code;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "DATETIME", nullable = false)
  @Getter
  protected Date creationDate = new Date();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "DATETIME", nullable = false)
  @Getter
  protected Date modificationDate = new Date();

  @Column(name = "active")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Getter
  @Setter
  private int active = 1;

  @PreUpdate
  private void onUpdate() {
    modificationDate = addHoursToJavaUtilDate(new Date(), -5);
  }

  @PrePersist
  private void onCreate() {
    creationDate = modificationDate = addHoursToJavaUtilDate(new Date(), -5);
  }

  public Date addHoursToJavaUtilDate(Date date, int hours) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hours);
    return calendar.getTime();
  }

}
