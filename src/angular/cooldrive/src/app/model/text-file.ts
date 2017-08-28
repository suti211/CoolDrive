import {Token} from "./token.model";

export class TextFile {
  name: string;
  content: string;
  modify: boolean
  token: Token;



  constructor(name: string, content: string, modify: boolean, token: Token) {
    this.name = name;
    this.content = content;
    this.modify = modify;
    this.token = token;
  }
}
