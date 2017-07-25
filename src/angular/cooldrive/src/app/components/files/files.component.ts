import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {File} from '../../model/file.model';
import {FileService} from '../../service/files.service';
import {StorageInfo} from '../../model/storage-info';
import {Token} from '../../model/token.model';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  currentFolderName: string = "";
  usage: number;
  quantity: number;
  percentage: number;
  files: File[];
  uploadedFilesList: any;
  filteredFiles: File[] = [];
  homeFolderSize: number;
  homeFolderMaxSize: number;

  progressBarSytle: string = "progress-bar";

  constructor(private fileService: FileService) {
    this.files = fileService.getFilesArray();
    this.filteredFiles = fileService.getFilteredFilesArray();
  }

  filterFiles(filt: string) {
    let filter = filt.toLowerCase();
    this.filteredFiles.length = 0;
    for (let file of this.files) {
      if (file.folder) {
        if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.label.toLowerCase().indexOf(filter) > -1) {
          this.filteredFiles.push(file);
        }
      } else {
        if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1 || file.label.toLowerCase().indexOf(filter) > -1) {
          this.filteredFiles.push(file);
        }
      }
    }
  }



  openFolder(id: number, name: string){
    if(name === "..."){
      this.currentFolderName = "Your files";
    }else{
      this.currentFolderName = name;
    }

    this.files.length = 0;
    this.filteredFiles.length = 0;
    let tokenID = localStorage.getItem(localStorage.key(0));
    let newToken = new Token(tokenID);
    newToken.setID(id);

    let getStorageInfoOperation: Observable<StorageInfo>;
    getStorageInfoOperation = this.fileService.getStorageInfo(newToken);
    getStorageInfoOperation.subscribe((info: StorageInfo) => {
      this.usage = info.usage;
      this.quantity = info.quantity;
      this.percentage = info.usage / info.quantity * 100;

      this.setProgressBarStyle();
    });

    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.fileService.getFiles(newToken);
    getFilesOperation.subscribe((newFiles: File[]) => {
      let backButton = new File(-1,"...", "",this.homeFolderSize,this.homeFolderMaxSize,"","",true);
      if(id > 0){
        this.files.push(backButton);
        this.filteredFiles.push(backButton);
      }

      for (let file of newFiles) {
        this.files.push(file);
        this.filteredFiles.push(file);
      }
      console.log(this.files);
    });
  }

  listUploadedFiles(){
    this.uploadedFilesList = document.getElementById("uploadedFiles")['files'];
    console.log(this.uploadedFilesList);
  }

  setProgressBarStyle(){
    if(this.percentage.valueOf() <= 60){
      this.progressBarSytle = "progress-bar";
    }else{
      if(this.percentage.valueOf() <= 85){
        this.progressBarSytle = "progress-bar bg-warning";
      }else{
        this.progressBarSytle = "progress-bar bg-danger";
      }
    }
  }

  ngOnInit() {
    //  let tokenID = localStorage.getItem(localStorage.key(0));
    let tokenID = localStorage.getItem(localStorage.key(0));
    let newToken = new Token(tokenID);
    newToken.setID(-1);

    this.currentFolderName = "Your files";

    let getStorageInfoOperation: Observable<StorageInfo>;
    getStorageInfoOperation = this.fileService.getStorageInfo(newToken);
    getStorageInfoOperation.subscribe((info: StorageInfo) => {
      this.usage = info.usage;
      this.quantity = info.quantity;
      this.percentage = info.usage / info.quantity * 100
      this.homeFolderSize = info.usage;
      this.homeFolderMaxSize = info.quantity;

      this.setProgressBarStyle();
    });

    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.fileService.getFiles(newToken);
    getFilesOperation.subscribe((newFiles: File[]) => {
      for (let file of newFiles) {
        this.files.push(file);
        this.filteredFiles.push(file);
      }
      console.log(this.files);
    });
  }

}
