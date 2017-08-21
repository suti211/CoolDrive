import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {File} from '../../model/file.model';
import {FileService} from '../../service/files.service';
import {StorageInfo} from '../../model/storage-info';
import {Token} from '../../model/token.model';
import {Status} from "../../model/status.model";
import {Folder} from "../../model/folder";
import {TextFile} from "../../model/text-file";
import {ShareService} from "../../service/share.service";
import {Share} from "../../model/shared";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  currentFolderId: number;
  currentFolderName: string = "";

  selectedFile: File = new File(-1, "", 0, "", "", "", 0, true, 0, 0, "", false);

  infoPanelDisplayed: boolean;
  infoPanelText: string;

  uploadInfoPanelDisplayed: boolean;
  uploadInfoPanelText: string;

  usage: number;
  quantity: number;
  percentage: number;

  files: File[];
  uploadedFilesList: any;
  filteredFiles: File[] = [];
  homeFolderSize: number;
  homeFolderMaxSize: number;

  newFolderName: string;
  newFolderLabel: string;
  newFolderMaxSize: number;

  newTxtTitle: string;
  newTxtContent: string;

  editTxtTitle: string;
  editTxtContent: string;

  shareFileId: number;
  shareFileName: string;
  shareReadOnly: boolean;
  shareWithEmail: string;
  sharedWith: Share[];

  publicLink: string;


  progressBarSytle: string = "progress-bar";

  constructor(private fileService: FileService, private shareService: ShareService) {
    this.files = fileService.getFilesArray();
    this.filteredFiles = fileService.getFilteredFilesArray();
    this.infoPanelDisplayed = false;
  }

  setSelectedFile(file: File) {
    this.selectedFile = file;
  }

  creatToken(id: number): Token {
    let tokenID = sessionStorage.getItem(sessionStorage.key(0));
    let newToken = new Token(tokenID);
    newToken.setID(id);
    return newToken;
  }

  setInfoPanelDisplay(text: string, show: boolean) {
    this.infoPanelDisplayed = show;
    this.infoPanelText = text;
    setTimeout(() => this.infoPanelDisplayed = false, 5000);
  }

  setUploadInfoPanelDisplay(text: string, show: boolean) {
    this.uploadInfoPanelDisplayed = show;
    this.uploadInfoPanelText = text;
    setTimeout(() => this.uploadInfoPanelDisplayed = false, 5000);
  }

  setProgressBarStyle() {
    if (this.percentage.valueOf() <= 60) {
      this.progressBarSytle = "progress-bar";
    } else {
      if (this.percentage.valueOf() <= 85) {
        this.progressBarSytle = "progress-bar bg-warning";
      } else {
        this.progressBarSytle = "progress-bar bg-danger";
      }
    }
  }

  // Files list methods

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


  openFolder(id: number, name: string) {
    this.currentFolderId = id;

    if (name === "...") {
      this.currentFolderName = "Your files";
    } else {
      this.currentFolderName = name;
    }

    this.files.length = 0;
    this.filteredFiles.length = 0;

    let newToken = this.creatToken(this.currentFolderId);

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
      let backButton = new File(-1, "", this.homeFolderSize, "", "...", "", this.homeFolderMaxSize, true, 0, 0, "", false);
      if (id > 0) {
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

  setShare(file: File) {
    this.shareFileId = file.id;
    this.shareFileName = file.fileName;
  }

  getShareInfo(file: File) {
    this.getPublicLink(file.id);
    this.shareFileId = file.id;
    this.shareFileName = file.fileName;
    let close = document.getElementById("shareClose");
    let token = this.creatToken(file.id);

    let sharedWith: Observable<Share[]>;
    sharedWith = this.shareService.getShareInfo(token);
    sharedWith.subscribe((share: Share[]) => {
      this.sharedWith = share;
      console.log(this.sharedWith);
    });
  }

  changeAccess(readOnly: boolean, email: string) {
    let token = this.creatToken(this.shareFileId);
    let share = new Share(email, readOnly, token)

    let sharedWith: Observable<Status>;
    sharedWith = this.shareService.changeAccess(share);
    sharedWith.subscribe((status: Status) => {
      console.log(status);
    });
  }

  removeAccess(email: string) {
    console.log("remove");
    let token = this.creatToken(this.shareFileId);
    let share = new Share(email, false, token)

    let removeShared: Observable<Status>;
    removeShared = this.shareService.removeAccess(share);
    removeShared.subscribe((status: Status) => {
      if (status.success) {
        let index = 0;
        for (var i = 0; i < this.sharedWith.length; i++) {
          if (this.sharedWith[i].email == email) {
            index = i;
          }
        }
        this.sharedWith.splice(index, 1);
        console.log(status);
      }
    });
  }

  share() {
    let close = document.getElementById("shareClose");
    let token = this.creatToken(this.shareFileId);
    let shared = new Share(this.shareWithEmail, this.shareReadOnly, token);

    let shareFileOperation: Observable<Status>;
    shareFileOperation = this.shareService.shareFile(shared);
    shareFileOperation.subscribe((status: Status) => {
      if (status.success) {
        this.setInfoPanelDisplay(status.message, true);
        close.click();
      }

    });
  }

  createTxtFile() {
    let close = document.getElementById("createTxtClose");
    let token = this.creatToken(this.currentFolderId);
    let txt = new TextFile(this.newTxtTitle, this.newTxtContent, token);

    let createTXTOperation: Observable<Status>;
    createTXTOperation = this.fileService.uploadTextFile(txt);
    createTXTOperation.subscribe((status: Status) => {
      if (status.success) {
        this.newTxtTitle = null;
        this.newTxtContent = null;
        this.setInfoPanelDisplay(status.message, true);
        this.listFiles(this.currentFolderId);
        close.click();
      }
    });
  }

  fetchEditTxtData(id: number) {
    let token = this.creatToken(id);

    let fetchTXTOperation: Observable<TextFile>;
    fetchTXTOperation = this.fileService.getTxtFileData(token);
    fetchTXTOperation.subscribe((txt: TextFile) => {
      console.log(txt);
      this.editTxtTitle = txt.name;
      this.editTxtContent = txt.content;
    });
  }

  editTxtFile() {
    let close = document.getElementById("editTxtClose");
    let token = this.creatToken(this.currentFolderId);
    let txt = new TextFile(this.editTxtTitle, this.editTxtContent, token);

    let createTXTOperation: Observable<Status>;
    createTXTOperation = this.fileService.uploadTextFile(txt);
    createTXTOperation.subscribe((status: Status) => {
      if (status.success) {
        this.listFiles(this.currentFolderId);
        this.setInfoPanelDisplay(status.message, true);
        close.click();
      }
    });
  }

  createFolder() {
    let close = document.getElementById("createFolderClose");
    let newToken = this.creatToken(-1);
    let folder = new Folder(newToken.token, this.newFolderName, this.newFolderMaxSize, this.newFolderLabel);

    let createFolderOperation: Observable<Status>;
    createFolderOperation = this.fileService.createFolder(folder);
    createFolderOperation.subscribe((status: Status) => {
      if (status.success) {
        this.newFolderName = null;
        this.newFolderMaxSize = null;
        this.newFolderLabel = null;
        this.listFiles(this.currentFolderId);
        this.setInfoPanelDisplay(status.message, true);
        close.click();
      }
    });
  }

  // File action methods

  download(fileId: number) {
    let token = this.creatToken(-1);
    this.fileService.downloadFile(fileId, token);
  }

  modifyFile() {
    const modifiedFile = this.selectedFile;

    let deleteFileOperation: Observable<Status>;
    deleteFileOperation = this.fileService.modifyFile(modifiedFile);
    deleteFileOperation.subscribe((status: Status) => {
      if (status.success) {
        this.listFiles(this.currentFolderId);
        this.setInfoPanelDisplay(status.message, true);
      }
    });
  }

  deleteFile(id: number) {
    let newToken = this.creatToken(id);

    let deleteFileOperation: Observable<Status>;
    deleteFileOperation = this.fileService.deleteFile(newToken);
    deleteFileOperation.subscribe((status: Status) => {
      if (status.success) {
        this.listFiles(this.currentFolderId);
        this.getStorageInfo();
        this.setInfoPanelDisplay(status.message, true);
      }
    });
  }

  getStorageInfo() {
    let newToken = this.creatToken(this.currentFolderId);

    let getStorageInfoOperation: Observable<StorageInfo>;
    getStorageInfoOperation = this.fileService.getStorageInfo(newToken);
    getStorageInfoOperation.subscribe((info: StorageInfo) => {
      this.usage = info.usage;
      this.quantity = info.quantity;
      this.percentage = info.usage / info.quantity * 100;

      this.setProgressBarStyle();
    });
  }

  uploadFile() {
    this.uploadedFilesList = document.getElementById("uploadedFiles")['files'];

    let newToken = this.creatToken(this.currentFolderId);

    let uploadFileOperation: Observable<Status>;
    uploadFileOperation = this.fileService.uploadFile(newToken, this.uploadedFilesList[0]);
    uploadFileOperation.subscribe((status: Status) => {
      if (status.success) {
        this.setInfoPanelDisplay(status.message, true);
        document.getElementById("uploadCloseButton").click();
        this.uploadedFilesList = null;
        this.setInfoPanelDisplay(status.message, true);
      } else {
        this.setUploadInfoPanelDisplay(status.message, true);
      }
      this.getStorageInfo()
      this.listFiles(this.currentFolderId);
    });

  }

  listUploadedFiles() {
    this.uploadedFilesList = document.getElementById("uploadedFiles")['files'];
    console.log(this.uploadedFilesList);
  }

  listFiles(id: number) {
    this.files.length = 0;
    this.filteredFiles.length = 0;
    let backButton = new File(-1, "", this.homeFolderSize, "", "...", "", this.homeFolderMaxSize, true, 0, 0, "", false);
    if (this.currentFolderId != -1) {
      console.log("pluszba");
      this.files.push(backButton);
      this.filteredFiles.push(backButton);
    }

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

  addPublicLink(id: number) {
    let token = this.creatToken(id)
    let addPublicLink: Observable<Status>;
    addPublicLink = this.fileService.setPublicLink(token);
    addPublicLink.subscribe((status: Status) => {
      if (status.success) {
        this.getPublicLink(id);
      }
    });
    console.log(id);
  }

  deletePublicLink(id: number) {
    let token = this.creatToken(id)
    let addPublicLink: Observable<Status>;
    addPublicLink = this.fileService.deletePublicLink(token);
    addPublicLink.subscribe((status: Status) => {
      if (status.success) {
        this.publicLink = null;
      }
    });
  }

  getPublicLink(id: number) {
    let token = this.creatToken(id)
    let addPublicLink: Observable<Status>;
    addPublicLink = this.fileService.getPublicLink(token);
    addPublicLink.subscribe((status: Status) => {
      if (status.message != null) {
        this.publicLink = environment.urlPrefix + '/files/public?link=' + status.message;
      } else {
        this.publicLink = null;
      }
    });
  }

  ngOnInit() {
    //  let tokenID = localStorage.getItem(localStorage.key(0));
    let newToken = this.creatToken(-1);

    this.currentFolderId = -1;
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

    this.listFiles(this.currentFolderId);
  }
}
