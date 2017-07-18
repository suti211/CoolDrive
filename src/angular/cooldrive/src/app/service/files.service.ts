import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { File } from '../model/file.model';

import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'

@Injectable()
export class FileService {
  filesUrl = "http://demo3422681.mockable.io/";

  constructor(private http: Http) {

  }

  getFiles(): Observable<File[]> {

    let headers = new Headers({'Content-Type' : 'application/json'});
    let options = new RequestOptions({headers : headers});
    console.log("asddd");
    return this.http.post(this.filesUrl, '', options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
}
