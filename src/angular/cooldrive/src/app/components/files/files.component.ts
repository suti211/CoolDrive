import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {File} from '../../model/file.model';
import {FileService} from '../../service/files.service';
import {StorageInfo} from '../../model/storage-info';
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";


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
    let getStorageInfoOperation: Observable<StorageInfo>;
    getStorageInfoOperation = this.fileService.getStorageInfo();
    getStorageInfoOperation.subscribe((info: StorageInfo) => {
      this.usage = info.usage;
      this.quantity = info.quantity;
      this.percentageStyle = info.usage / info.quantity * 100 + "%";
    });

    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.fileService.getFiles();
    getFilesOperation.subscribe((newFiles: File[]) => {
      this.files = newFiles;
      console.log(this.files);
    });
  }

}
