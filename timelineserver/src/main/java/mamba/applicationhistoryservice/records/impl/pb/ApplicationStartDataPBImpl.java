

package mamba.applicationhistoryservice.records.impl.pb;

import com.google.protobuf.TextFormat;
import mamba.applicationhistoryservice.records.ApplicationStartData;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.impl.pb.ApplicationIdPBImpl;
import org.apache.hadoop.yarn.proto.ApplicationHistoryServerProtos.ApplicationStartDataProto;
import org.apache.hadoop.yarn.proto.ApplicationHistoryServerProtos.ApplicationStartDataProtoOrBuilder;
import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationIdProto;
/**
 * Created by sanbing on 10/10/16.
 */
public class ApplicationStartDataPBImpl extends ApplicationStartData {

  ApplicationStartDataProto proto = ApplicationStartDataProto
    .getDefaultInstance();
  ApplicationStartDataProto.Builder builder = null;
  boolean viaProto = false;

  private ApplicationId applicationId;

  public ApplicationStartDataPBImpl() {
    builder = ApplicationStartDataProto.newBuilder();
  }

  public ApplicationStartDataPBImpl(ApplicationStartDataProto proto) {
    this.proto = proto;
    viaProto = true;
  }

  @Override
  public ApplicationId getApplicationId() {
    if (this.applicationId != null) {
      return this.applicationId;
    }
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasApplicationId()) {
      return null;
    }
    this.applicationId = convertFromProtoFormat(p.getApplicationId());
    return this.applicationId;
  }

  @Override
  public void setApplicationId(ApplicationId applicationId) {
    maybeInitBuilder();
    if (applicationId == null) {
      builder.clearApplicationId();
    }
    this.applicationId = applicationId;
  }

  @Override
  public String getApplicationName() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasApplicationName()) {
      return null;
    }
    return p.getApplicationName();
  }

  @Override
  public void setApplicationName(String applicationName) {
    maybeInitBuilder();
    if (applicationName == null) {
      builder.clearApplicationName();
      return;
    }
    builder.setApplicationName(applicationName);
  }

  @Override
  public String getApplicationType() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasApplicationType()) {
      return null;
    }
    return p.getApplicationType();
  }

  @Override
  public void setApplicationType(String applicationType) {
    maybeInitBuilder();
    if (applicationType == null) {
      builder.clearApplicationType();
      return;
    }
    builder.setApplicationType(applicationType);
  }

  @Override
  public String getUser() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasUser()) {
      return null;
    }
    return p.getUser();
  }

  @Override
  public void setUser(String user) {
    maybeInitBuilder();
    if (user == null) {
      builder.clearUser();
      return;
    }
    builder.setUser(user);
  }

  @Override
  public String getQueue() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    if (!p.hasQueue()) {
      return null;
    }
    return p.getQueue();
  }

  @Override
  public void setQueue(String queue) {
    maybeInitBuilder();
    if (queue == null) {
      builder.clearQueue();
      return;
    }
    builder.setQueue(queue);
  }

  @Override
  public long getSubmitTime() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    return p.getSubmitTime();
  }

  @Override
  public void setSubmitTime(long submitTime) {
    maybeInitBuilder();
    builder.setSubmitTime(submitTime);
  }

  @Override
  public long getStartTime() {
    ApplicationStartDataProtoOrBuilder p = viaProto ? proto : builder;
    return p.getStartTime();
  }

  @Override
  public void setStartTime(long startTime) {
    maybeInitBuilder();
    builder.setStartTime(startTime);
  }

  public ApplicationStartDataProto getProto() {
    mergeLocalToProto();
    proto = viaProto ? proto : builder.build();
    viaProto = true;
    return proto;
  }

  @Override
  public int hashCode() {
    return getProto().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (other.getClass().isAssignableFrom(this.getClass())) {
      return this.getProto().equals(this.getClass().cast(other).getProto());
    }
    return false;
  }

  @Override
  public String toString() {
    return TextFormat.shortDebugString(getProto());
  }

  private void mergeLocalToBuilder() {
    if (this.applicationId != null
        && !((ApplicationIdPBImpl) this.applicationId).getProto().equals(
          builder.getApplicationId())) {
      builder.setApplicationId(convertToProtoFormat(this.applicationId));
    }
  }

  private void mergeLocalToProto() {
    if (viaProto) {
      maybeInitBuilder();
    }
    mergeLocalToBuilder();
    proto = builder.build();
    viaProto = true;
  }

  private void maybeInitBuilder() {
    if (viaProto || builder == null) {
      builder = ApplicationStartDataProto.newBuilder(proto);
    }
    viaProto = false;
  }

  private ApplicationIdProto convertToProtoFormat(ApplicationId applicationId) {
    return ((ApplicationIdPBImpl) applicationId).getProto();
  }

  private ApplicationIdPBImpl convertFromProtoFormat(
      ApplicationIdProto applicationId) {
    return new ApplicationIdPBImpl(applicationId);
  }
}
