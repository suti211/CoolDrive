export class File {
  id: number;
  fileName: String;
  extension: String;
  size: Number;
  maxSize: Number;
  uploadDate: string;
  label: String;
  folder: boolean;


  constructor(id: number, fileName: String, extension: String, size: Number, maxSize: Number, uploadDate: string, label: String, folder: boolean) {
    this.id = id;
    this.fileName = fileName;
    this.extension = extension;
    this.size = size;
    this.maxSize = maxSize;
    this.uploadDate = uploadDate;
    this.label = label;
    this.folder = folder;
  }
}
