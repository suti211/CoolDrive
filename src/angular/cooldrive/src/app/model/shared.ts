import {Token} from './token.model';

export class Share {
  email: string;
  readOnly: boolean;
  token: Token;


  constructor(email: string, readOnly: boolean, token: Token) {
    this.email = email;
    this.readOnly = readOnly;
    this.token = token;
  }
}
