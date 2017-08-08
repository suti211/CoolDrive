import { Component, OnInit } from '@angular/core';
import {File} from '../../model/file.model';
import {FileService} from "../../service/files.service";
import {Token} from "../../model/token.model";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-shared-with-me',
  templateUrl: './shared-with-me.component.html',
  styleUrls: ['./shared-with-me.component.css']
})
export class SharedWithMeComponent implements OnInit {

  files: File[];
  filteredFiles: File[] = [];

  currentFolderId: number;
  currentFolderName: string = "";


  constructor(private fileService: FileService) {
    this.files = fileService.getFilesArray();
    this.filteredFiles = fileService.getFilteredFilesArray();
  }



  download(fileId: number) {
    let token = this.creatToken(-1);
    this.fileService.downloadFile(fileId, token);
  }

  listFiles(id: number){
    this.files.length = 0;
    this.filteredFiles.length = 0;

    let newToken = this.creatToken(id);

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

  filterFiles(filt: string) {
    let filter = filt.toLowerCase();
    this.filteredFiles.length = 0;

    if (filt.length === 0) {
      for (let file of this.files) {
        this.filteredFiles.push(file);
      }
    }else {
      for (let file of this.files) {
        if(file.label === null) {
          if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1) {
            this.filteredFiles.push(file);
          }
        }else {
          if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1 || file.label.toLowerCase().indexOf(filter) > -1) {
            this.filteredFiles.push(file);
          }
        }
      }
    }
  }

  creatToken(id: number): Token {
    let tokenID = sessionStorage.getItem(sessionStorage.key(0));
    let newToken = new Token(tokenID);
    newToken.setID(id);
    return newToken;
  }

  ngOnInit() {
    let newToken = this.creatToken(-1);

    this.currentFolderId = -1;
    this.currentFolderName = "Files shared with You";

    this.listFiles(this.currentFolderId);
  }

}
