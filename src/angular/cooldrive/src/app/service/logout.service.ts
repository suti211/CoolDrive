import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {Status} from '../model/status.model';
import { environment } from "../../environments/environment"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'

@Injectable()
export class LogoutService {
  logoutUrl = environment.urlPrefix + "/" + "logout";

  constructor(private http: Http) {

  }

  sendLogoutRequest(body: Object): Observable<Status> {

    let bodyString = JSON.stringify(body);
    console.log("body JSON: " + bodyString);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.logoutUrl, bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
}
