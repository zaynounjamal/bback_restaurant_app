package zaynoun.ul.bbackapplication.models;

public class CompletedOrderItemsModel {
    int deliveredImage;
    String name;
    String Payment;

    public CompletedOrderItemsModel(int deliveredImage, String name, String payment) {
        this.deliveredImage = deliveredImage;
        this.name = name;
        Payment = payment;
    }

    public int getDeliveredImage() {
        return deliveredImage;
    }

    public void setDeliveredImage(int deliveredImage) {
        this.deliveredImage = deliveredImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }
}
