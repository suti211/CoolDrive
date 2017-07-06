package object;

import java.sql.Date;

/**
 * Created by David Szilagyi on 2017. 07. 06..
 */
public class File {
    private final int id;
    private String path;
    private final double size;
    private final Date uploadDate;
    private String fileName;
    private double maxSize;
    private final boolean isFolder;
    private final int ownerId;
    private int parentId;

    public File(int id, String path, double size, Date uploadDate, String fileName, double maxSize, boolean isFolder, int ownerId, int parentId) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.maxSize = maxSize;
        this.isFolder = isFolder;
        this.ownerId = ownerId;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public double getSize() {
        return size;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public double getMaxSize() {
        return maxSize;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
