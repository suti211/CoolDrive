package dto;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
public class Folder {
    private String token;
    private String name;
    private double maxSize;
    private String label;

    public Folder(String token, String name, double maxSize, String label) {
        this.token = token;
        this.name = name;
        this.maxSize = maxSize;
        this.label = label;
    }

    public Folder(String token, String name, double maxSize) {
        this.token = token;
        this.name = name;
        this.maxSize = maxSize;
    }

    public Folder() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
