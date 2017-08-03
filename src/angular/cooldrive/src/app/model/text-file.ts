import {Token} from "./token.model";

export class TextFile {
  name: string;
  content: string;
  token: Token;


  constructor(name: string, content: string, token: Token) {
    this.name = name;
    this.content = content;
    this.token = token;
  }
}
