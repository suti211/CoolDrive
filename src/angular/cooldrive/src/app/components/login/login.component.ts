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

export class LoginComponent{

  constructor (private loginService: LoginService){

  }

  username: String;
  password: String;
  user: User;
  status: Status;

  login(){

    if(this.isThereInput()){

      console.log(this.username, this.password);
      console.log("Login Attempt!");
      this.user = new User(this.username, this.password);
      let loginOperation: Observable<Status>;

      loginOperation = this.loginService.sendLoginData(this.user);
      loginOperation.subscribe((status: Status) => {
        this.status = status;
        console.log(this.status);
      });

    } else {
      console.log("Üres az a sok szar te fasz!");
    }
  }

  isThereInput(): boolean{
    if(this.password !=null && this.username != null){
      return true;
    } else {
      return false;
    }
  }
}


