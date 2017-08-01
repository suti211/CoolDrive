export class Folder {
  token: string;
  name: string;
  maxSize: number;
  label: string;

  constructor(token: string, name: string, maxSize: number, label: string) {
    this.token = token;
    this.name = name;
    this.maxSize = maxSize;
    this.label = label;
  }
}
