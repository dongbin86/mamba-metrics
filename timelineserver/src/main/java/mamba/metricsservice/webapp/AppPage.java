package mamba.metricsservice.webapp;

import org.apache.hadoop.yarn.server.webapp.AppBlock;
import org.apache.hadoop.yarn.webapp.SubView;
import org.apache.hadoop.yarn.webapp.YarnWebParams;

import static org.apache.hadoop.yarn.util.StringHelper.join;
import static org.apache.hadoop.yarn.webapp.view.JQueryUI.*;
/**
 * Created by dongbin on 2016/10/10.
 */
public class AppPage extends AHSView {

  @Override
  protected void preHead(Page.HTML<_> html) {
    commonPreHead(html);

    String appId = $(YarnWebParams.APPLICATION_ID);
    set(
      TITLE,
      appId.isEmpty() ? "Bad request: missing application ID" : join(
        "Application ", $(YarnWebParams.APPLICATION_ID)));

    set(DATATABLES_ID, "attempts");
    set(initID(DATATABLES, "attempts"), attemptsTableInit());
    setTableStyles(html, "attempts", ".queue {width:6em}", ".ui {width:8em}");
  }

  @Override
  protected Class<? extends SubView> content() {
    return AppBlock.class;
  }

  private String attemptsTableInit() {
    return tableInit().append(", 'aaData': attemptsTableData")
      .append(", bDeferRender: true").append(", bProcessing: true")

      .append("\n, aoColumnDefs: ").append(getAttemptsTableColumnDefs())

      // Sort by id upon page load
      .append(", aaSorting: [[0, 'desc']]}").toString();
  }

  protected String getAttemptsTableColumnDefs() {
    StringBuilder sb = new StringBuilder();
    return sb.append("[\n").append("{'sType':'numeric', 'aTargets': [0]")
      .append(", 'mRender': parseHadoopID }")

      .append("\n, {'sType':'numeric', 'aTargets': [1]")
      .append(", 'mRender': renderHadoopDate }]").toString();
  }
}
