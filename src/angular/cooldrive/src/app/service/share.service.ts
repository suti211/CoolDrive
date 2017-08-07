import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {File} from '../model/file.model';
import { environment } from "../../environments/environment.live"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {Token} from "../model/token.model";
import {Status} from "../model/status.model";
import {Share} from "../model/shared";

@Injectable()
export class ShareService {
  Url = environment.urlPrefix + '/share/';

  constructor(private http: Http) { }


  shareFile(shared: Share): Observable<Status>{
    let bodyString = JSON.stringify(shared);
    console.log(bodyString);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.Url + 'add', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

}
