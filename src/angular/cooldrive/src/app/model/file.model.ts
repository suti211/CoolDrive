export class File {

  constructor(
    public id: number,
    public path: string,
    public size: Number,
    public uploadDate: string,
    public fileName: String,
    public extension: String,
    public maxSize: Number,
    public folder: boolean,
    public ownerId: number,
    public parentId: number,
    public label: String
    ) {
  }
}
