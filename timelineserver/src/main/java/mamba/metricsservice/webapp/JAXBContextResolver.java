package mamba.metricsservice.webapp;

import com.google.inject.Singleton;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import org.apache.hadoop.yarn.server.webapp.dao.*;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by dongbin on 2016/10/10.
 */
@Singleton
@Provider
@SuppressWarnings("rawtypes")
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

  private JAXBContext context;
  private final Set<Class> types;

  // you have to specify all the dao classes here
  private final Class[] cTypes = { AppInfo.class, AppsInfo.class,
      AppAttemptInfo.class, AppAttemptsInfo.class, ContainerInfo.class,
      ContainersInfo.class };

  public JAXBContextResolver() throws Exception {
    this.types = new HashSet<Class>(Arrays.asList(cTypes));
    this.context =
        new JSONJAXBContext(JSONConfiguration.natural().rootUnwrapping(false)
          .build(), cTypes);
  }

  @Override
  public JAXBContext getContext(Class<?> objectType) {
    return (types.contains(objectType)) ? context : null;
  }
}
