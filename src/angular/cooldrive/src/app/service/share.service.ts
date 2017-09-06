import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {File} from '../model/file.model';
import { environment } from "../../environments/environment"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {Token} from "../model/token.model";
import {Status} from "../model/status.model";
import {Share} from "../model/shared";
import {HttpClient} from "./http.client";

@Injectable()
export class ShareService {
  Url = environment.urlPrefix + '/share/';
  files: File[] = [];
  filteredFiles: File[] = [];

  constructor(private http: HttpClient) { }

  getFilesArray(): File[] {
    return this.files;
  }

  getFilteredFilesArray(): File[] {
    return this.filteredFiles;
  }

  shareFile(shared: Share): Observable<Status>{
    let bodyString = JSON.stringify(shared);
    console.log(bodyString);

    return this.http.post(this.Url + 'add', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  getFiles(token: Token): Observable<File[]> {
    let bodyString = JSON.stringify(token);

    return this.http.post(this.Url + 'sharedWithMe', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  getShareInfo(token: Token): Observable<Share[]> {
    let bodyString = JSON.stringify(token);

    return this.http.post(this.Url + 'sharedWith', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  changeAccess(share: Share): Observable<Status> {
    let bodyString = JSON.stringify(share);

    return this.http.post(this.Url + 'changeAccess', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  removeAccess(share: Share): Observable<Status> {
    let bodyString = JSON.stringify(share);

    return this.http.post(this.Url + 'remove', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

}
