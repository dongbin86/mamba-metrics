package mamba.metricsservice.records;


import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.util.Records;

/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ApplicationStartData {


  public static ApplicationStartData newInstance(ApplicationId applicationId,
      String applicationName, String applicationType, String queue,
      String user, long submitTime, long startTime) {
    ApplicationStartData appSD = Records.newRecord(ApplicationStartData.class);
    appSD.setApplicationId(applicationId);
    appSD.setApplicationName(applicationName);
    appSD.setApplicationType(applicationType);
    appSD.setQueue(queue);
    appSD.setUser(user);
    appSD.setSubmitTime(submitTime);
    appSD.setStartTime(startTime);
    return appSD;
  }


  public abstract ApplicationId getApplicationId();


  public abstract void setApplicationId(ApplicationId applicationId);


  public abstract String getApplicationName();


  public abstract void setApplicationName(String applicationName);


  public abstract String getApplicationType();


  public abstract void setApplicationType(String applicationType);


  public abstract String getUser();


  public abstract void setUser(String user);


  public abstract String getQueue();


  public abstract void setQueue(String queue);


  public abstract long getSubmitTime();


  public abstract void setSubmitTime(long submitTime);


  public abstract long getStartTime();


  public abstract void setStartTime(long startTime);

}
