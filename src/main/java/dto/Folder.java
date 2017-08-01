package dto;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
public class Folder {
    private String fileName;
    private double maxSize;
    private String label;

    public Folder(String fileName, double maxSize, String label) {
        this.fileName = fileName;
        this.maxSize = maxSize;
        this.label = label;
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
}
