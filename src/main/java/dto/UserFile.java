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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFile userFile = (UserFile) o;

        if (id != userFile.id) return false;
        if (Double.compare(userFile.size, size) != 0) return false;
        if (Double.compare(userFile.maxSize, maxSize) != 0) return false;
        if (isFolder != userFile.isFolder) return false;
        if (ownerId != userFile.ownerId) return false;
        if (parentId != userFile.parentId) return false;
        if (path != null ? !path.equals(userFile.path) : userFile.path != null) return false;
        if (uploadDate != null ? !uploadDate.equals(userFile.uploadDate) : userFile.uploadDate != null) return false;
        if (fileName != null ? !fileName.equals(userFile.fileName) : userFile.fileName != null) return false;
        return extension != null ? extension.equals(userFile.extension) : userFile.extension == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (uploadDate != null ? uploadDate.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        temp = Double.doubleToLongBits(maxSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isFolder ? 1 : 0);
        result = 31 * result + ownerId;
        result = 31 * result + parentId;
        return result;
    }
}
