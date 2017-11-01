package sample;

public class Site {
    protected String siteName;
    protected String ipRange;

    public Site(String siteName, String isIpRange) {
        this.siteName = siteName;
        this.ipRange = isIpRange;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }
}
