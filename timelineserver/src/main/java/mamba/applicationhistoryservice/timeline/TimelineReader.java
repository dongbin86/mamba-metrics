package mamba.applicationhistoryservice.timeline;

import org.apache.hadoop.yarn.api.records.timeline.TimelineEntities;
import org.apache.hadoop.yarn.api.records.timeline.TimelineEntity;
import org.apache.hadoop.yarn.api.records.timeline.TimelineEvents;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by dongbin on 2016/10/10.
 */
public interface TimelineReader {

    /**
     * Default limit for {@link #getEntities} and {@link #getEntityTimelines}.
     */
    final long DEFAULT_LIMIT = 100;

    TimelineEntities getEntities(String entityType,
                                 Long limit, Long windowStart, Long windowEnd, String fromId, Long fromTs,
                                 NameValuePair primaryFilter, Collection<NameValuePair> secondaryFilters,
                                 EnumSet<Field> fieldsToRetrieve) throws IOException;

    TimelineEntity getEntity(String entityId, String entityType, EnumSet<Field>
            fieldsToRetrieve) throws IOException;

    TimelineEvents getEntityTimelines(String entityType,
                                      SortedSet<String> entityIds, Long limit, Long windowStart,
                                      Long windowEnd, Set<String> eventTypes) throws IOException;


    /**
     * Possible fields to retrieve for {@link #getEntities} and {@link #getEntity}
     * .
     */
    enum Field {
        EVENTS,
        RELATED_ENTITIES,
        PRIMARY_FILTERS,
        OTHER_INFO,
        LAST_EVENT_ONLY
    }
}
