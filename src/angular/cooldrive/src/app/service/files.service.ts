import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {File} from '../model/file.model';
import { environment } from "../../environments/environment"
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch'
import {StorageInfo} from '../model/storage-info';
import {Token} from '../model/token.model';
import {Status} from "../model/status.model";
import {Folder} from "../model/folder";
import {TextFile} from "../model/text-file";
import {HttpClient} from "./http.client";

@Injectable()
export class FileService {
  files: File[] = [];
  filteredFiles: File[] = [];
  filesUrl = environment.urlPrefix + '/files/';

  constructor(private http: HttpClient, private oHttp: Http) {
  }

  getFilesArray(): File[] {
    return this.files;
  }

  getFilteredFilesArray(): File[] {
    return this.filteredFiles;
  }

  getTxtFileData(token: Token): Observable<TextFile>{
    let bodyString = JSON.stringify(token);
    console.log(bodyString)
    return this.http.post(this.filesUrl + 'getTXT', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  uploadTextFile(txt: TextFile): Observable<Status>{
    let bodyString = JSON.stringify(txt);
    console.log(bodyString);

    return this.http.post(this.filesUrl + 'uploadTXT', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  createFolder(folder: Folder): Observable<Status>{
    console.log(folder);
    let bodyString = JSON.stringify(folder);

    return this.http.post(this.filesUrl + 'createFolder', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  downloadFile(fileId: number, token: Token) {
    this.http.get(this.filesUrl + 'download?id=' + fileId + "&token=" + token.token).toPromise()
      .then(function(response) {
        window.location.href = response.url;
      })
  }

  modifyFile(file: File): Observable<Status>{
    console.log(file);
    let bodyString = JSON.stringify(file);

    console.log(bodyString);

    return this.http.post(this.filesUrl + 'modify', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  deleteFile(token: Token): Observable<Status>{
    let bodyString = JSON.stringify(token);

    return this.http.post(this.filesUrl + 'deleteFile', <String>bodyString)
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
    return this.oHttp.post(this.filesUrl + 'upload', fd, options)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw('Server Error'));
  }

  getStorageInfo(token: Token): Observable<StorageInfo> {
    let bodyString = JSON.stringify(token);

    return this.http.post(this.filesUrl + 'getStorageInfo', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  getFiles(token: Token): Observable<File[]> {
    let bodyString = JSON.stringify(token);

    return this.http.post(this.filesUrl + 'getFiles', <String>bodyString)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }


}
