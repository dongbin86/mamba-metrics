package mamba.applicationhistoryservice.webapp;

import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.webapp.view.HtmlBlock;

/**
 * Created by dongbin on 2016/10/10.
 */
public class NavBlock extends HtmlBlock {

    @Override
    public void render(Block html) {
        html.
                div("#nav").
                h3("Application History").
                ul().
                li().a(url("apps"), "Applications").
                ul().
                li().a(url("apps",
                YarnApplicationState.FINISHED.toString()),
                YarnApplicationState.FINISHED.toString()).
                _().
                li().a(url("apps",
                YarnApplicationState.FAILED.toString()),
                YarnApplicationState.FAILED.toString()).
                _().
                li().a(url("apps",
                YarnApplicationState.KILLED.toString()),
                YarnApplicationState.KILLED.toString()).
                _().
                _().
                _().
                _().
                _();
    }
}
