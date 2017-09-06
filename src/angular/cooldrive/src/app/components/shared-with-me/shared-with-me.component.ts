import {Component, OnInit} from '@angular/core';
import {File} from '../../model/file.model';
import {Token} from "../../model/token.model";
import {Observable} from "rxjs/Observable";
import {ShareService} from "../../service/share.service";
import {FileService} from "../../service/files.service";
import {Status} from "../../model/status.model";
import {TextFile} from "../../model/text-file";
import {FilterListener} from "../maintenance/filterlistener";
import {FilterService} from "../../service/filter.service";

@Component({
  selector: 'app-shared-with-me',
  templateUrl: './shared-with-me.component.html',
  styleUrls: ['./shared-with-me.component.css']
})
export class SharedWithMeComponent implements OnInit, FilterListener {

  files: File[];
  filteredFiles: File[] = [];
  isEmptyFiles: boolean;

  currentFolderId: number;
  currentFolderName: string = "";

  editTxtFileID: number;
  editTxtTitle: string;
  editTxtContent: string;


  constructor(private fileService: FileService, private shareService: ShareService, private filterService: FilterService) {
    this.files = shareService.getFilesArray();
    this.filteredFiles = shareService.getFilteredFilesArray();
    this.filterService.listener = this;
  }

  onFiltered(filt: string): void {
    let filter = filt.toLowerCase();
    this.filteredFiles.length = 0;

    if (filt.length === 0) {
      for (let file of this.files) {
        this.filteredFiles.push(file);
      }
    } else {
      for (let file of this.files) {
        if (file.label === null) {
          if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1) {
            this.filteredFiles.push(file);
          }
        } else {
          if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1 || file.label.toLowerCase().indexOf(filter) > -1) {
            this.filteredFiles.push(file);
          }
        }
      }
    }
  }

  download(fileId: number) {
    let token = this.creatToken(-1);
    this.fileService.downloadFile(fileId);
  }

  listFiles(id: number) {
    this.files.length = 0;
    this.filteredFiles.length = 0;

    let newToken = this.creatToken(id);

    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.shareService.getFiles(newToken);
    getFilesOperation.subscribe((newFiles: File[]) => {
      if (newFiles.length != 0) {
        this.isEmptyFiles = false;
        for (let file of newFiles) {
          this.files.push(file);
          this.filteredFiles.push(file);
        }
        console.log(this.files);
      } else {
        this.isEmptyFiles = true;
      }
    });
  }

  openFolder(id: number, name: string) {
    this.currentFolderId = id;

    if (name === "...") {
      this.currentFolderName = "Files shared with You";
    } else {
      this.currentFolderName = name;
    }

    this.files.length = 0;
    this.filteredFiles.length = 0;
    this.isEmptyFiles = null;

    let newToken = this.creatToken(this.currentFolderId);
    let getFilesOperation: Observable<File[]>;
    getFilesOperation = this.shareService.getFiles(newToken);
    getFilesOperation.subscribe((newFiles: File[]) => {
      for (let file of newFiles) {
        this.files.push(file);
        this.filteredFiles.push(file);
      }
      console.log(this.files);
    });
  }

  fetchEditTxtData(id: number, parentId: number) {
    let token = this.creatToken(id);
    this.editTxtFileID = parentId;

    let fetchTXTOperation: Observable<TextFile>;
    fetchTXTOperation = this.fileService.getTxtFileData(token);
    fetchTXTOperation.subscribe((txt: TextFile) => {
      console.log(txt);
      this.editTxtTitle = txt.name;
      this.editTxtContent = txt.content;
    });
  }

  editTxtFile() {
    let token = this.creatToken(this.editTxtFileID);
    let txt = new TextFile(this.editTxtTitle, this.editTxtContent, true, token);

    let createTXTOperation: Observable<Status>;
    createTXTOperation = this.fileService.uploadTextFile(txt);
    createTXTOperation.subscribe((status: Status) => {
      console.log(status.message);
      if (status.success) {
        document.getElementById('closeButton').click();
      } else {
        console.log(status);
        window.alert("Text modify failed!");
      }
    });
  }

  filterFiles(filt: string) {
    let filter = filt.toLowerCase();
    this.filteredFiles.length = 0;

    if (filt.length === 0) {
      for (let file of this.files) {
        this.filteredFiles.push(file);
      }
    } else {
      for (let file of this.files) {
        if (file.label === null) {
          if (file.fileName.toLowerCase().indexOf(filter) > -1 || file.extension.toLowerCase().indexOf(filter) > -1) {
            this.filteredFiles.push(file);
          }
        } else {
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
