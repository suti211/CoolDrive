import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { File } from '../model/file.model';

import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {StorageInfo} from '../model/storage-info';
import {Token} from '../model/token.model';

@Injectable()
export class FileService {
  filesUrl = "http://demo3422681.mockable.io/";
  //filesUrl = "http://192.168.150.29:8080/CoolDrive/files/";

  constructor(private http: Http) {

  }

  getStorageInfo(token: Token): Observable<StorageInfo> {
  let bodyString = JSON.stringify(token);

  let headers = new Headers({'Content-Type' : 'application/json'});
  let options = new RequestOptions({headers : headers});

  return this.http.post(this.filesUrl + 'getStorageInfo', bodyString, options)
  .map((res: Response) => res.json())
  .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  getFiles(token: Token): Observable<File[]> {
    let bodyString = JSON.stringify(token);

  let headers = new Headers({'Content-Type' : 'application/json'});
  let options = new RequestOptions({headers : headers});
  return this.http.post(this.filesUrl + 'getFiles', bodyString, options)
  .map((res: Response) => res.json())
  .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }


}
