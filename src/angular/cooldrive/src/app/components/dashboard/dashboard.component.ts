import { Component, OnInit } from '@angular/core';
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { LogoutService } from '../../service/logout.service';
import { Observable } from 'rxjs/Rx';
import { Status } from '../../model/status.model';
import { Token } from '../../model/token.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  userName : string = localStorage.key(0);
  userToken: Token = new Token(localStorage.getItem(this.userName));
  logoutOperation: Observable<Status>;

  constructor(private logoutService: LogoutService, private router: Router) {}

  ngOnInit() {
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
