export class File {

  constructor(
    public id: number,
    public fileName: String,
    public extension: String,
    public size: Number,
    public maxSize: Number,
    public uploadDate: string,
    public label: String,
    public folder: boolean) {
  }
}
