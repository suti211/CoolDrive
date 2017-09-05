import {Injectable, NgModule} from "@angular/core";
import {Http, Headers, RequestOptions} from "@angular/http";

@Injectable()
export class HttpClient {

  constructor(private http: Http) {}

  createAuthorizationHeader(headers: Headers) {
    headers.append("Authorization" , "Bearer " + sessionStorage.getItem(sessionStorage.key(0)));
  }

  get(url: string) {
    let headers = new Headers();
    let requestOptions = new RequestOptions({headers: headers})
    this.createAuthorizationHeader(headers);
    return this.http.get(url, requestOptions);
  }

  post(url: string, data: object) {
    let headers = new Headers({'Content-Type': 'application/json'});
    this.createAuthorizationHeader(headers);
    return this.http.post(url, data, {headers: headers});
  }

  postFile(url: string, data: object) {
    let headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.post(url, data, {headers: headers})
  }
}
