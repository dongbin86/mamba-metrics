package mamba.applicationhistoryservice;

import mamba.applicationhistoryservice.records.*;

import java.io.IOException;

/**
 * Created by sanbing on 10/10/16.
 */
public interface ApplicationHistoryWriter {


    /**
     * This method writes the information of <code>RMApp</code> that is available
     * when it starts.
     *
     * @param appStart the record of the information of <code>RMApp</code> that is
     *                 available when it starts
     * @throws IOException
     */
    void applicationStarted(ApplicationStartData appStart) throws IOException;

    /**
     * This method writes the information of <code>RMApp</code> that is available
     * when it finishes.
     *
     * @param appFinish the record of the information of <code>RMApp</code> that is
     *                  available when it finishes
     * @throws IOException
     */
    void applicationFinished(ApplicationFinishData appFinish) throws IOException;

    /**
     * This method writes the information of <code>RMAppAttempt</code> that is
     * available when it starts.
     *
     * @param appAttemptStart the record of the information of <code>RMAppAttempt</code> that is
     *                        available when it starts
     * @throws IOException
     */
    void applicationAttemptStarted(ApplicationAttemptStartData appAttemptStart)
            throws IOException;

    /**
     * This method writes the information of <code>RMAppAttempt</code> that is
     * available when it finishes.
     *
     * @param appAttemptFinish the record of the information of <code>RMAppAttempt</code> that is
     *                         available when it finishes
     * @throws IOException
     */
    void
    applicationAttemptFinished(ApplicationAttemptFinishData appAttemptFinish)
            throws IOException;

    /**
     * This method writes the information of <code>RMContainer</code> that is
     * available when it starts.
     *
     * @param containerStart the record of the information of <code>RMContainer</code> that is
     *                       available when it starts
     * @throws IOException
     */
    void containerStarted(ContainerStartData containerStart) throws IOException;

    /**
     * This method writes the information of <code>RMContainer</code> that is
     * available when it finishes.
     *
     * @param containerFinish the record of the information of <code>RMContainer</code> that is
     *                        available when it finishes
     * @throws IOException
     */
    void containerFinished(ContainerFinishData containerFinish)
            throws IOException;

}
