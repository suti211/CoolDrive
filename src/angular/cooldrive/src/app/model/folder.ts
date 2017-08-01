export class Folder {
  token: string;
  name: string;
  maxsize: number;
  label: string;

  constructor(token: string, name: string, maxsize: number, label: string) {
    this.token = token;
    this.name = name;
    this.maxsize = maxsize;
    this.label = label;
  }
}
