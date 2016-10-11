package mamba.timeline.cache;

import junit.framework.Assert;
import mamba.timeline.PostProcessingUtil;
import org.junit.Test;

import java.util.*;

/**
 * Created by sanbing on 10/10/16.
 */
public class PostProcessingUtilTest {

    @Test
    public void testInterpolateMissinData() throws Exception {

        Map<Long, Double> metricValues = new TreeMap<Long, Double>();
        long interval = 60 * 1000;

        long currentTime = System.currentTimeMillis();

        for (int i = 10; i >= 1; i--) {
            if (i % 4 != 0 && i != 5) { //Skip time points 4,5,8
                metricValues.put(currentTime - i * interval, (double) i);
            }
        }
        metricValues = PostProcessingUtil.interpolateMissingData(metricValues, interval);
        Assert.assertTrue(metricValues.size() == 10);

        Iterator it = metricValues.entrySet().iterator();
        double sum = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            sum += (double) entry.getValue();
        }
        Assert.assertEquals(sum, 55.0);
    }

    @Test
    public void testInterpolate() throws Exception {

        long t2 = System.currentTimeMillis();
        long t1 = t2 - 60000;
        double interpolatedValue;

        //Test Equal Values
        interpolatedValue = PostProcessingUtil.interpolate((t1 + 30000), t1, 10.0, t2, 10.0);
        Assert.assertEquals(interpolatedValue, 10.0);

        //Test Linear increase Values
        interpolatedValue = PostProcessingUtil.interpolate((t1 + 30000), t1, 10.0, t2, 20.0);
        Assert.assertEquals(interpolatedValue, 15.0);

        //Test Linear decrease Values
        interpolatedValue = PostProcessingUtil.interpolate((t1 + 30000), t1, 20.0, t2, 10.0);
        Assert.assertEquals(interpolatedValue, 15.0);

        //Test interpolation with non mid point time
        interpolatedValue = PostProcessingUtil.interpolate((t1 + 20000), t1, 15.0, t2, 30.0); // 1:2 ratio
        Assert.assertEquals(interpolatedValue, 20.0);

        //Test interpolation with past time
        interpolatedValue = PostProcessingUtil.interpolate((t1 - 60000), t1, 20.0, t2, 30.0);
        Assert.assertEquals(interpolatedValue, 10.0);

    }

    @Test
    public void testLinearInterpolate() throws Exception {

        long t2 = System.currentTimeMillis();

        Map<Long, Double> valuesMap = new TreeMap<>();

        valuesMap.put(t2 - 4 * 3000, 4.0);
        valuesMap.put(t2 - 2 * 3000, 2.0);
        valuesMap.put(t2 - 1 * 3000, 1.0);

        List<Long> requiredTs = new ArrayList<Long>();
        requiredTs.add(t2 - 5 * 3000);
        requiredTs.add(t2 - 3 * 3000);
        requiredTs.add(t2);

        Map result = PostProcessingUtil.interpolate(valuesMap, requiredTs);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.get(t2 - 5 * 3000), 5.0);
        Assert.assertEquals(result.get(t2 - 3 * 3000), 3.0);
        Assert.assertEquals(result.get(t2), 0.0);
        System.out.println(result.toString());

    }
}
