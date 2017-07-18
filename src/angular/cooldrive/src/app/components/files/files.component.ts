import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {File} from '../../model/file.model';
import {FileService} from '../../service/files.service';


@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  files: File[];

  constructor(private fileService: FileService) { }

  ngOnInit() {
    let getFilesOperation: Observable<File[]>;

    getFilesOperation = this.fileService.getFiles();
    getFilesOperation.subscribe((newFiles: File[]) => {
      this.files = newFiles;
      console.log(this.files);
    });
  }

}
