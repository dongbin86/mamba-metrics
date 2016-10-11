
# Set environment variables here.

# The java implementation to use. Java 1.6 required.
export JAVA_HOME=/opt/taobao/java

# Collector Log directory for log4j
export AMS_COLLECTOR_LOG_DIR=/var/log/ambari-metrics-collector

# Monitor Log directory for outfile
export AMS_MONITOR_LOG_DIR=/var/log/ambari-metrics-monitor

# Collector pid directory
export AMS_COLLECTOR_PID_DIR=/var/run/ambari-metrics-collector

# Monitor pid directory
export AMS_MONITOR_PID_DIR=/var/run/ambari-metrics-monitor

# AMS HBase pid directory
export AMS_HBASE_PID_DIR=/var/run/ambari-metrics-collector/

# AMS Collector heapsize
export AMS_COLLECTOR_HEAPSIZE=512m

# HBase normalized enabled
export AMS_HBASE_NORMALIZER_ENABLED=True

# HBase compaction policy enabled
export AMS_HBASE_FIFO_COMPACTION_ENABLED=True

# HBase Tables Initialization check enabled
export AMS_HBASE_INIT_CHECK_ENABLED=True

# AMS Collector options
export AMS_COLLECTOR_OPTS="-Djava.library.path=/usr/lib/ams-hbase/lib/hadoop-native"


# AMS Collector GC options
export AMS_COLLECTOR_GC_OPTS="-XX:+UseConcMarkSweepGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/var/log/ambari-metrics-collector/collector-gc.log-`date +'%Y%m%d%H%M'`"
export AMS_COLLECTOR_OPTS="$AMS_COLLECTOR_OPTS $AMS_COLLECTOR_GC_OPTS"