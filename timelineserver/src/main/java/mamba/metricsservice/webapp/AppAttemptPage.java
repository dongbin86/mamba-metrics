package mamba.metricsservice.webapp;

import org.apache.hadoop.yarn.server.webapp.AppAttemptBlock;
import org.apache.hadoop.yarn.webapp.SubView;
import org.apache.hadoop.yarn.webapp.YarnWebParams;
import org.apache.hadoop.yarn.webapp.view.HtmlPage;

import static org.apache.hadoop.yarn.util.StringHelper.join;
import static org.apache.hadoop.yarn.webapp.view.JQueryUI.*;
/**
 * Created by dongbin on 2016/10/10.
 */
public class AppAttemptPage extends AHSView {

  @Override
  protected void preHead(HtmlPage.Page.HTML<HtmlPage._> html) {
    commonPreHead(html);

    String appAttemptId = $(YarnWebParams.APPLICATION_ATTEMPT_ID);
    set(
      TITLE,
      appAttemptId.isEmpty() ? "Bad request: missing application attempt ID"
          : join("Application Attempt ",
            $(YarnWebParams.APPLICATION_ATTEMPT_ID)));

    set(DATATABLES_ID, "containers");
    set(initID(DATATABLES, "containers"), containersTableInit());
    setTableStyles(html, "containers", ".queue {width:6em}", ".ui {width:8em}");
  }

  @Override
  protected Class<? extends SubView> content() {
    return AppAttemptBlock.class;
  }

  private String containersTableInit() {
    return tableInit().append(", 'aaData': containersTableData")
      .append(", bDeferRender: true").append(", bProcessing: true")

      .append("\n, aoColumnDefs: ").append(getContainersTableColumnDefs())

      // Sort by id upon page load
      .append(", aaSorting: [[0, 'desc']]}").toString();
  }

  protected String getContainersTableColumnDefs() {
    StringBuilder sb = new StringBuilder();
    return sb.append("[\n").append("{'sType':'numeric', 'aTargets': [0]")
      .append(", 'mRender': parseHadoopID }]").toString();
  }

}