import { Component, OnInit } from '@angular/core';
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { LogoutService } from '../../service/logout.service';
import { Observable } from 'rxjs/Rx';
import { Status } from '../../model/status.model';
import { Token } from '../../model/token.model';
import { Router, ActivatedRoute } from '@angular/router';
import {FilesComponent} from '../files/files.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: [FilesComponent]
})
export class DashboardComponent implements OnInit {

  userName : string = localStorage.key(0);
  userToken: Token = new Token(localStorage.getItem(this.userName));
  logoutOperation: Observable<Status>;

  filter: string;

  constructor(private logoutService: LogoutService, private router: Router, private filesComponent: FilesComponent) {}

  ngOnInit() {
  }

  sendSearchData(filt: string){
    this.filesComponent.filterFiles(filt);
  }


  logout(){
    this.logoutOperation = this.logoutService.sendLogoutRequest(this.userToken);
    this.logoutOperation.subscribe((status: Status) => {
      if(status.success){
        console.log(status);
        localStorage.clear();
        this.router.navigate(['login']);
      }
    });
  }

  storage(){
    this.router.navigate(['storage']);
  }

}
