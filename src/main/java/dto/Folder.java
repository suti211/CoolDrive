package dto;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
public class Folder {
    private String fileName;
    private double maxSize;
    private String label;
    private String token;

    public Folder(String fileName, double maxSize, String label, String token) {
        this.fileName = fileName;
        this.maxSize = maxSize;
        this.label = label;
        this.token = token;
    }

    public Folder(String fileName, double maxSize, String token) {
        this.fileName = fileName;
        this.maxSize = maxSize;
        this.token = token;
    }

    public Folder() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
