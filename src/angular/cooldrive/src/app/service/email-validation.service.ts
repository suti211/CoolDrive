import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {Status} from "../model/status.model";


@Injectable()
export class EmailValidationService {
  constructor(private http: Http) { }

  filesUrl = "http://localhost:8080/CoolDrive/";

  sendValidation(token: string): Observable<Status>{
    let bodyString = JSON.stringify(token);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.filesUrl + 'verify', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

}
