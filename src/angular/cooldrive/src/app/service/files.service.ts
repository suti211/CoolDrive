import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {File} from '../model/file.model';
import { environment } from "../../environments/environment.live"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {StorageInfo} from '../model/storage-info';
import {Token} from '../model/token.model';
import {Status} from "../model/status.model";
import {Folder} from "../model/folder";
import {TextFile} from "../model/text-file";

@Injectable()
export class FileService {
  files: File[] = [];
  filteredFiles: File[] = [];
  filesUrl = environment.urlPrefix + '/files/';

  constructor(private http: Http) {
  }

  getFilesArray(): File[] {
    return this.files;
  }

  getFilteredFilesArray(): File[] {
    return this.filteredFiles;
  }

  getTxtFileData(token: Token): Observable<TextFile>{
    let bodyString = JSON.stringify(token);
    console.log(bodyString);
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'getTXT', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  uploadTextFile(txt: TextFile): Observable<Status>{
    let bodyString = JSON.stringify(txt);
    console.log(bodyString);
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'uploadTXT', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  createFolder(folder: Folder): Observable<Status>{
    console.log(folder);
    let bodyString = JSON.stringify(folder);
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'createFolder', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  downloadFile(fileId: number) {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    this.http.get(this.filesUrl + 'download?id=' + fileId, options).toPromise()
      .then(function(response) {
        window.location.href = response.url;
      })
  }

  modifyFile(file: File): Observable<Status>{
    console.log(file);
    let bodyString = JSON.stringify(file);

    console.log(bodyString);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'modify', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  deleteFile(token: Token): Observable<Status>{
    let bodyString = JSON.stringify(token);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'deleteFile', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  uploadFile(token: Token, file): Observable<Status>{
    let fd = new FormData();
    fd.append('token', token.token);
    fd.append('id', token.id.toString());
    fd.append('input', file);


    let headers = new Headers([{'Content-Type': 'multipart/form-data'}]);
    let options = new RequestOptions({headers: headers});

    console.log(fd);
    return this.http.post(this.filesUrl + 'upload', fd, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  getStorageInfo(token: Token): Observable<StorageInfo> {
    let bodyString = JSON.stringify(token);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.filesUrl + 'getStorageInfo', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  getFiles(token: Token): Observable<File[]> {
    let bodyString = JSON.stringify(token);

    let headers = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.filesUrl + 'getFiles', bodyString, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }


}
