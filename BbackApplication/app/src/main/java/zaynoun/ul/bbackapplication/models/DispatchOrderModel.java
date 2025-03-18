package zaynoun.ul.bbackapplication.models;

public class DispatchOrderModel {
    int Image;
    String name;
    String Payment;

    public DispatchOrderModel(int image, String name, String payment) {
        Image = image;
        this.name = name;
        Payment = payment;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
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
