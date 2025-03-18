package zaynoun.ul.bbackapplication;

public class DataClass {
    private String dataName;
    private String dataDesc;
    private String dataPrice;
    private String dataRate;
    private String dataImage;
    private String dataTime;

    public DataClass(String dataName, String dataDesc, String dataPrice, String dataRate, String dataImage, String dataTime) {
        this.dataName = dataName;
        this.dataDesc = dataDesc;
        this.dataPrice = dataPrice;
        this.dataRate = dataRate;
        this.dataImage = dataImage;
        this.dataTime = dataTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(String dataPrice) {
        this.dataPrice = dataPrice;
    }

    public String getDataRate() {
        return dataRate;
    }

    public void setDataRate(String dataRate) {
        this.dataRate = dataRate;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
