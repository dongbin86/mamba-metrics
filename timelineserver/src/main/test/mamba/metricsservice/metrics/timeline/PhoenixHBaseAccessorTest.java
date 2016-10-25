package mamba.metricsservice.metrics.timeline;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static mamba.metricsservice.metrics.timeline.TimelineMetricConfiguration.HBASE_SITE_CONFIGURATION_FILE;
import static mamba.metricsservice.metrics.timeline.TimelineMetricConfiguration.METRICS_SITE_CONFIGURATION_FILE;

/**
 * @author Dongbin
 * @date 10/25/16
 */
public class PhoenixHBaseAccessorTest {
    private static final Log LOG = LogFactory.getLog(PhoenixHBaseAccessor.class);

    private Configuration hbaseConf;
    private Configuration metricsConf;


    private static final String ZOOKEEPER_CLIENT_PORT = "hbase.zookeeper.property.clientPort";
    private static final String ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    private static final String ZNODE_PARENT = "zookeeper.znode.parent";


    @Before
    public void setUp() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        URL hbaseResUrl = classLoader.getResource(HBASE_SITE_CONFIGURATION_FILE);
        URL amsResUrl = classLoader.getResource(METRICS_SITE_CONFIGURATION_FILE);
        LOG.info("Found hbase site configuration: " + hbaseResUrl);
        LOG.info("Found metric service configuration: " + amsResUrl);
        if (hbaseResUrl == null) {
            LOG.error("Unable to initialize the metrics " +
                    "subsystem. No hbase-site present in the classpath.");
            throw new IllegalStateException("Unable to initialize the metrics " +
                    "subsystem. No hbase-site present in the classpath.");
        }
        if (amsResUrl == null) {
            LOG.error("Unable to initialize the metrics " +
                    "subsystem. No ams-site present in the classpath.");
            throw new IllegalStateException("Unable to initialize the metrics " +
                    "subsystem. No ams-site present in the classpath.");
        }

        hbaseConf = new Configuration(true);
        hbaseConf.addResource(hbaseResUrl.toURI().toURL());
        metricsConf = new Configuration(true);
        metricsConf.addResource(amsResUrl.toURI().toURL());
    }

    @Test
    public void testLoadConfiguration() throws Exception {
        System.out.println(hbaseConf.get(ZOOKEEPER_CLIENT_PORT));
        System.out.println(hbaseConf.get(ZOOKEEPER_QUORUM));
        System.out.println(hbaseConf.get(ZNODE_PARENT));
        System.out.println(metricsConf.get("timeline.metrics.service.default.result.limit"));
    }


    /**
     *
     * 说明
     * 1.phenix的链接方式，DefaultPhoenixDataSource那种就可以，jdbc:phoenix:localhost:2181:/hbase
     * 2.注意，上面driver的connectUrl的后面zkParentNode是hbase在zk上面的rootPath，如果不一致，phenix是连不上的
     * 3.hbase的hmaster和regionserver下面都要有phenix的server jar包，然后重启hmaster和regionserver
     *  phoenix-4.8.1-HBase-1.2-server.jar
     * 4. timeline server 通过phenix 将数据存储到hbase，在创建表的时候，指定了压缩方式，这种压缩方式基本上是hdfs和hbase都需要的
     *  HBase中可以对HFile进行gzip、lzo、snappy方式的压缩存储。
     *
     * 例如"CREATE TABLE IF NOT " +
     "EXISTS METRIC_RECORD (METRIC_NAME VARCHAR, " +
     "HOSTNAME VARCHAR, " +
     "SERVER_TIME UNSIGNED_LONG NOT NULL, " +
     "APP_ID VARCHAR, " +
     "INSTANCE_ID VARCHAR, " +
     "START_TIME UNSIGNED_LONG, " +
     "UNITS CHAR(20), " +
     "METRIC_SUM DOUBLE, " +
     "METRIC_COUNT UNSIGNED_INT, " +
     "METRIC_MAX DOUBLE, " +
     "METRIC_MIN DOUBLE, " +
     "METRICS VARCHAR CONSTRAINT pk " +
     "PRIMARY KEY (METRIC_NAME, HOSTNAME, SERVER_TIME, APP_ID, " +
     "INSTANCE_ID)) DATA_BLOCK_ENCODING='%s', IMMUTABLE_ROWS=true, " +
     "TTL=%s, COMPRESSION='%s'";

     *      COMPRESSION后面可以跟哪些压缩方式呢？
     *
     *      gz， snappy，lzo 大小写都可以。
     *
     *      其中 snappy和lzo都需要native lib库的支持。
     *
     * 如果你是在standalone模式下调试这个项目，那么你就先选择gz方式好了，这个不需要你额外配置
     *
     * <property>
            <name>timeline.metrics.hbase.compression.scheme</name>
            <value>GZ</value>
     </property>


     5.执行完initMetricSchema之后，系统会创建11张表
     CONTAINER_METRICS
     HOSTED_APPS_METADATA
     METRICS_METADATA
     METRIC_AGGREGATE
     METRIC_AGGREGATE_DAILY
     METRIC_AGGREGATE_HOURLY
     METRIC_AGGREGATE_MINUTE
     METRIC_RECORD
     METRIC_RECORD_DAILY
     METRIC_RECORD_HOURLY
     METRIC_RECORD_MINUTE
     从这些表里可以看出什么来呢，可以看出来这种metrics采集系统的一个基本设计原理

     首先metrics数据通常都是数据量特别庞大的，即存储的挑战
     其次是metrics数据需要同时满足精度和聚合的要求，什么意思呢，所谓精度，就是指数据尽量要精细，可能，每一秒钟的数据我都需要
     比如排查具体性能问题的时候，因此保存精度要细，细的结果就是数据量庞大。但同时，我们更多的时候要看整体趋势，要统计，因此就要聚合，
     具体图表展示的时候，也不能无限个点数据一起展示，肯定需要聚合，比如按照分钟，小时级别聚合，当有具体的小时间范围段的请求时再从非聚合数据中
     查找

     那么什么时候聚合呢，如果你查询的时候，临时聚合，那么性能一定不会好，给用户的体验就很差，数据半天绘制不出来
     因此，这里的设计，就是同时保有细节表，和聚合表，metric都是按照时间存储，其实很容易scan出一段时间内的数据进行聚合

     *
     * */


    @Test
    public void testPhenixHbaseAccessor() throws Exception {
        PhoenixHBaseAccessor accessor = new PhoenixHBaseAccessor(hbaseConf,metricsConf);

        accessor.initMetricSchema();

    }
}
