package dto;

import java.sql.Date;

/**
 * Created by David Szilagyi on 2017. 07. 06..
 */
public class UserFile {
    private int id;
    private String path;
    private double size;
    private Date uploadDate;
    private String fileName;
    private String extension;
    private double maxSize;
    private boolean isFolder;
    private int ownerId;
    private int parentId;

    public UserFile(int id, String path, double size, Date uploadDate, String fileName, String extension, double maxSize, boolean isFolder, int ownerId, int parentId) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.extension = extension;
        this.maxSize = maxSize;
        this.isFolder = isFolder;
        this.ownerId = ownerId;
        this.parentId = parentId;
    }

    public UserFile(String path, double size, String fileName, String extension, boolean isFolder, int ownerId, int parentId) {
        this.path = path;
        this.size = size;
        this.fileName = fileName;
        this.extension = extension;
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

    public String getExtension() {
        return extension;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMaxSize(double maxSize) {
        if (!isFolder) {
            this.maxSize = maxSize;
        }
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
