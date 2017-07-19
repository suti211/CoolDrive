export class Token {
    token: string;
    id: number;

  constructor(token: string) {
    this.token = token;
  };

  setID(id: number) {
    this.id = id;
  }
}
