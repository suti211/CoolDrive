import { Component } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Observable } from 'rxjs/Rx';
import { User } from '../../model/user.model';
import { Status } from '../../model/status.model'

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  constructor (private loginService: LoginService){

  }

  title = 'Login';
  username: String;
  password: String;
  user: User;

  login(){
    console.log("Login Attempt!");
    this.user = new User(this.username, this.password);
    let loginOperation: Observable<Status>;

    loginOperation = this.loginService.sendLoginData(this.user);
    loginOperation.subscribe();
  }

}


