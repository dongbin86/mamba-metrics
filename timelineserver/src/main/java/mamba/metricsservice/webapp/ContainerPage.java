package mamba.metricsservice.webapp;

import org.apache.hadoop.yarn.server.webapp.ContainerBlock;
import org.apache.hadoop.yarn.webapp.SubView;
import org.apache.hadoop.yarn.webapp.YarnWebParams;

import static org.apache.hadoop.yarn.util.StringHelper.join;
/**
 * Created by dongbin on 2016/10/10.
 */
public class ContainerPage extends AHSView {

  @Override
  protected void preHead(Page.HTML<_> html) {
    commonPreHead(html);

    String containerId = $(YarnWebParams.CONTAINER_ID);
    set(TITLE, containerId.isEmpty() ? "Bad request: missing container ID"
        : join("Container ", $(YarnWebParams.CONTAINER_ID)));
  }

  @Override
  protected Class<? extends SubView> content() {
    return ContainerBlock.class;
  }
}