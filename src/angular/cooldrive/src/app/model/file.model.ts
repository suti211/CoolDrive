export class File {
  id: number;
  filename: String;
  extension: String;
  size: Number;
  lastmodify: string;
  label: String;
  isFolder: boolean;

  constructor(id: number, filename: String, extension: String, size: Number, lastmodify: string, label: String, isFolder: boolean) {
    this.id = id;
    this.filename = filename;
    this.extension = extension;
    this.size = size;
    this.lastmodify = lastmodify;
    this.label = label;
    this.isFolder = isFolder;
  }
}
