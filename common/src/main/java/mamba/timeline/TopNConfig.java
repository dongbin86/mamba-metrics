package mamba.timeline;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sanbing on 10/10/16.
 */
@XmlRootElement(name = "topnconfig")
@XmlAccessorType(XmlAccessType.NONE)
public class TopNConfig {


    Integer topN;
    String topNFunction;
    Boolean isBottomN;

    public TopNConfig(Integer topN, String topNFunction, Boolean isBottomN) {
        this.setTopN(topN);
        this.setTopNFunction(topNFunction);
        this.setIsBottomN(isBottomN);
    }

    @XmlElement(name = "topn")
    public Integer getTopN() {
        return topN;
    }

    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    @XmlElement(name = "topnfunction")
    public String getTopNFunction() {
        return topNFunction;
    }

    public void setTopNFunction(String topNFunction) {
        this.topNFunction = topNFunction;
    }

    @XmlElement(name = "isbottomn")
    public Boolean getIsBottomN() {
        return isBottomN;
    }

    public void setIsBottomN(Boolean isBottomN) {
        this.isBottomN = isBottomN;
    }
}
