import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { environment } from "../../environments/environment"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {Status} from "../model/status.model";


@Injectable()
export class EmailValidationService {
  constructor(private http: Http) { }

  verifyUrl = environment.urlPrefix;

  sendValidation(token: string): Observable<Status>{
    let bodyString = JSON.stringify(token);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.verifyUrl + '/verify', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

}
