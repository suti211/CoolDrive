export class File {

  constructor(
    public id: number,
    public path: string,
    public size: number,
    public uploadDate: string,
    public fileName: string,
    public extension: string,
    public maxSize: number,
    public folder: boolean,
    public ownerId: number,
    public parentId: number,
    public label: string,
    public readOnly: boolean
    ) {
  }
}
