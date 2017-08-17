import { Component, OnInit } from '@angular/core';
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { LogoutService } from '../../service/logout.service';
import { Observable } from 'rxjs/Rx';
import { Status } from '../../model/status.model';
import { Token } from '../../model/token.model';
import { Router, ActivatedRoute } from '@angular/router';
import {FilesComponent} from '../files/files.component';
import {TokenService} from "../../service/token.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: [FilesComponent]
})
export class DashboardComponent implements OnInit {

  userName : string = sessionStorage.key(0);
  userToken: Token = new Token(sessionStorage.getItem(this.userName));
  logoutOperation: Observable<Status>;
  tokenOperation: Observable<Status>;
  isUserAdmin: boolean;

  filter: string;

  constructor(private logoutService: LogoutService, private router: Router, private filesComponent: FilesComponent, private tokenService: TokenService) {}

  ngOnInit() {
    this.tokenOperation = this.tokenService.isTokenAdmin(this.userToken);
    this.tokenOperation.subscribe((status: Status) => {
      if(status.success){
        this.isUserAdmin = true;
      } else {
        this.isUserAdmin = false;
      }
      console.log(this.isUserAdmin);
    });
  }

  sendSearchData(filt: string){
    this.filesComponent.filterFiles(filt);
  }


  logout(){
    this.logoutOperation = this.logoutService.sendLogoutRequest(this.userToken);
    this.logoutOperation.subscribe((status: Status) => {
      console.log(status);
      if(status.success){
        localStorage.removeItem("transaction");
        setTimeout(this.router.navigate(['login']), 2000);
      }
    });
    sessionStorage.clear();
  }

  storage(){
    this.router.navigate(['storage']);
  }

}
