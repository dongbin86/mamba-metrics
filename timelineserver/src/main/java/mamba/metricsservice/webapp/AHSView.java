package mamba.metricsservice.webapp;

import org.apache.hadoop.yarn.server.webapp.AppsBlock;
import org.apache.hadoop.yarn.webapp.SubView;
import org.apache.hadoop.yarn.webapp.view.TwoColumnLayout;

import static org.apache.hadoop.yarn.util.StringHelper.sjoin;
import static org.apache.hadoop.yarn.webapp.YarnWebParams.APP_STATE;
import static org.apache.hadoop.yarn.webapp.view.JQueryUI.*;

/**
 * Created by dongbin on 2016/10/10.
 */
public class AHSView extends TwoColumnLayout {
    static final int MAX_DISPLAY_ROWS = 100; // direct table rendering
    static final int MAX_FAST_ROWS = 1000; // inline js array

    @Override
    protected void preHead(Page.HTML<_> html) {
        commonPreHead(html);
        set(DATATABLES_ID, "apps");
        set(initID(DATATABLES, "apps"), appsTableInit());
        setTableStyles(html, "apps", ".queue {width:6em}", ".ui {width:8em}");

        // Set the correct title.
        String reqState = $(APP_STATE);
        reqState = (reqState == null || reqState.isEmpty() ? "All" : reqState);
        setTitle(sjoin(reqState, "Applications"));
    }

    protected void commonPreHead(Page.HTML<_> html) {
        set(ACCORDION_ID, "nav");
        set(initID(ACCORDION, "nav"), "{autoHeight:false, active:0}");
    }

    @Override
    protected Class<? extends SubView> nav() {
        return NavBlock.class;
    }

    @Override
    protected Class<? extends SubView> content() {
        return AppsBlock.class;
    }

    private String appsTableInit() {
        // id, user, name, queue, starttime, finishtime, state, status, progress, ui
        return tableInit().append(", 'aaData': appsTableData")
                .append(", bDeferRender: true").append(", bProcessing: true")

                .append("\n, aoColumnDefs: ").append(getAppsTableColumnDefs())

                // Sort by id upon page load
                .append(", aaSorting: [[0, 'desc']]}").toString();
    }

    protected String getAppsTableColumnDefs() {
        StringBuilder sb = new StringBuilder();
        return sb.append("[\n").append("{'sType':'numeric', 'aTargets': [0]")
                .append(", 'mRender': parseHadoopID }")

                .append("\n, {'sType':'numeric', 'aTargets': [5, 6]")
                .append(", 'mRender': renderHadoopDate }")

                .append("\n, {'sType':'numeric', bSearchable:false, 'aTargets': [9]")
                .append(", 'mRender': parseHadoopProgress }]").toString();
    }
}
