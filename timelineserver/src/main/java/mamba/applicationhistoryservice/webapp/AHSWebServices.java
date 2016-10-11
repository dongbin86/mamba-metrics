package mamba.applicationhistoryservice.webapp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.hadoop.yarn.api.ApplicationBaseProtocol;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.server.webapp.WebServices;
import org.apache.hadoop.yarn.server.webapp.dao.*;
import org.apache.hadoop.yarn.webapp.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Set;
/**
 * Created by dongbin on 2016/10/10.
 */
@Singleton
@Path("/ws/v1/applicationhistory")
public class AHSWebServices extends WebServices {

  @Inject
  public AHSWebServices(ApplicationBaseProtocol appBaseProt) {
    super(appBaseProt);
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public AppsInfo get(@Context HttpServletRequest req,
                      @Context HttpServletResponse res) {
    return getApps(req, res, null, Collections.<String> emptySet(), null, null,
      null, null, null, null, null, null, Collections.<String> emptySet());
  }

  @GET
  @Path("/apps")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public AppsInfo getApps(@Context HttpServletRequest req,
                          @Context HttpServletResponse res, @QueryParam("state") String stateQuery,
                          @QueryParam("states") Set<String> statesQuery,
                          @QueryParam("finalStatus") String finalStatusQuery,
                          @QueryParam("user") String userQuery,
                          @QueryParam("queue") String queueQuery,
                          @QueryParam("limit") String count,
                          @QueryParam("startedTimeBegin") String startedBegin,
                          @QueryParam("startedTimeEnd") String startedEnd,
                          @QueryParam("finishedTimeBegin") String finishBegin,
                          @QueryParam("finishedTimeEnd") String finishEnd,
                          @QueryParam("applicationTypes") Set<String> applicationTypes) {
    init(res);
    validateStates(stateQuery, statesQuery);
    return super.getApps(req, res, stateQuery, statesQuery, finalStatusQuery,
      userQuery, queueQuery, count, startedBegin, startedEnd, finishBegin,
      finishEnd, applicationTypes);
  }

  @GET
  @Path("/apps/{appid}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public AppInfo getApp(@Context HttpServletRequest req,
                        @Context HttpServletResponse res, @PathParam("appid") String appId) {
    init(res);
    return super.getApp(req, res, appId);
  }

  @GET
  @Path("/apps/{appid}/appattempts")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public AppAttemptsInfo getAppAttempts(@Context HttpServletRequest req,
                                        @Context HttpServletResponse res, @PathParam("appid") String appId) {
    init(res);
    return super.getAppAttempts(req, res, appId);
  }

  @GET
  @Path("/apps/{appid}/appattempts/{appattemptid}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public AppAttemptInfo getAppAttempt(@Context HttpServletRequest req,
                                      @Context HttpServletResponse res, @PathParam("appid") String appId,
                                      @PathParam("appattemptid") String appAttemptId) {
    init(res);
    return super.getAppAttempt(req, res, appId, appAttemptId);
  }

  @GET
  @Path("/apps/{appid}/appattempts/{appattemptid}/containers")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public ContainersInfo getContainers(@Context HttpServletRequest req,
                                      @Context HttpServletResponse res, @PathParam("appid") String appId,
                                      @PathParam("appattemptid") String appAttemptId) {
    init(res);
    return super.getContainers(req, res, appId, appAttemptId);
  }

  @GET
  @Path("/apps/{appid}/appattempts/{appattemptid}/containers/{containerid}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public ContainerInfo getContainer(@Context HttpServletRequest req,
                                    @Context HttpServletResponse res, @PathParam("appid") String appId,
                                    @PathParam("appattemptid") String appAttemptId,
                                    @PathParam("containerid") String containerId) {
    init(res);
    return super.getContainer(req, res, appId, appAttemptId, containerId);
  }

  private static void
      validateStates(String stateQuery, Set<String> statesQuery) {
    // stateQuery is deprecated.
    if (stateQuery != null && !stateQuery.isEmpty()) {
      statesQuery.add(stateQuery);
    }
    Set<String> appStates = parseQueries(statesQuery, true);
    for (String appState : appStates) {
      switch (YarnApplicationState.valueOf(appState.toUpperCase())) {
        case FINISHED:
        case FAILED:
        case KILLED:
          continue;
        default:
          throw new BadRequestException("Invalid application-state " + appState
              + " specified. It should be a final state");
      }
    }
  }

}