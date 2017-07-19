import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {File} from '../../model/file.model';
import {FileService} from '../../service/files.service';
import {StorageInfo} from '../../model/storage-info';
import {Token} from "../../model/token.model";


@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  usage: number;
  quantity: number;
  percentageStyle: string;
  files: File[];

  constructor(private fileService: FileService) { }

  ngOnInit() {
  //  let tokenID = localStorage.getItem(localStorage.key(0));
    let tokenID = localStorage.getItem(localStorage.key(0));
    let newToken = new Token(tokenID);
    newToken.setID(-1);

    let getStorageInfoOperation: Observable<StorageInfo>;
    getStorageInfoOperation = this.fileService.getStorageInfo(newToken);
    getStorageInfoOperation.subscribe((info: StorageInfo) => {
      this.usage = info.usage;
      this.quantity = info.quantity;
      this.percentageStyle = info.usage / info.quantity * 100 + "%";
    });

    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.fileService.getFiles(newToken);
    getFilesOperation.subscribe((newFiles: File[]) => {
      this.files = newFiles;
      console.log(this.files);
    });
  }

}
