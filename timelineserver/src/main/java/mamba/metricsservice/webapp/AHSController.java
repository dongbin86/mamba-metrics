package mamba.metricsservice.webapp;

import com.google.inject.Inject;
import org.apache.hadoop.yarn.webapp.Controller;

/**
 * Created by dongbin on 2016/10/10.
 */
public class AHSController extends Controller {

    @Inject
    AHSController(RequestContext ctx) {
        super(ctx);
    }

    @Override
    public void index() {
        setTitle("Application History");
    }

    public void app() {
        render(AppPage.class);
    }

    public void appattempt() {
        render(AppAttemptPage.class);
    }

    public void container() {
        render(ContainerPage.class);
    }

    /**
     * Render the logs page.
     */
    public void logs() {
        render(AHSLogsPage.class);
    }
}
