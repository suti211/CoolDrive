package dto;

/**
 * Created by David Szilagyi on 2017. 07. 19..
 */
public class StorageInfo {
    private double usage;
    private double quantity;

    public StorageInfo() {
    }

    public StorageInfo(double usage, double quantity) {
        this.usage = usage;
        this.quantity = quantity;
    }

    public double getUsage() {
        return usage;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
