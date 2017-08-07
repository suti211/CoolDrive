import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";

@Injectable()
export class HttpClient {

  constructor(private http: Http) {}

  createAuthorizationHeader(headers: Headers) {
    headers.append("token", sessionStorage.getItem("transaction"));
  }

  get(url: string) {
    let headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.get(url, {headers: headers });
  }

  post(url: string, data: object) {
    let headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.post(url, data, {headers: headers});
  }
}
