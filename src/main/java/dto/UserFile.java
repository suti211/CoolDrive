package dto;

/**
 * Created by David Szilagyi on 2017. 07. 06..
 */
public class UserFile {
    private int id;
    private String path;
    private double size;
    private String uploadDate;
    private String fileName;
    private String extension;
    private double maxSize;
    private boolean isFolder;
    private int ownerId;
    private int parentId;
    private String label;

    public UserFile(int id, String path, double size, String uploadDate, String fileName, String extension, double maxSize, boolean isFolder, int ownerId, int parentId, String label) {
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
        this.label = label;
    }

    public UserFile(String path, double size, String fileName, String extension, double maxSize, boolean isFolder, int ownerId, int parentId) {
        this.path = path;
        this.size = size;
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

    public UserFile() {
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

    public String getUploadDate() {
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

    public String getLabel() {
        return label;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
