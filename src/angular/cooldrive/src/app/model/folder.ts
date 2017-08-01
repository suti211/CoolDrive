export class Folder {
  name: string;
  maxsize: number;
  label: string;

  constructor(name: string, maxsize: number, label: string) {
    this.name = name;
    this.maxsize = maxsize;
    this.label = label;
  }
}
