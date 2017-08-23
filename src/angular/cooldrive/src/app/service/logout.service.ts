import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {Status} from '../model/status.model';
import { environment } from "../../environments/environment"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {HttpClient} from "./http.client";

@Injectable()
export class LogoutService {
  logoutUrl = environment.urlPrefix + "/" + "logout";

  constructor(private http: HttpClient) {

  }

  sendLogoutRequest(body: Object): Observable<Status> {

    let bodyString = JSON.stringify(body);

    return this.http.post(this.logoutUrl, <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
}
