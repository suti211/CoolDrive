import { Component } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Observable } from 'rxjs/Rx';
import { Register } from '../../model/register.model';
import { Status } from '../../model/status.model'
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { Router, ActivatedRoute } from '@angular/router';
import { Token } from '../../model/token.model';


@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],

  // make fade in animation available to this component
  animations: [slideInOutAnimation],

  // attach the fade in animation to the host (root) element of this component
  host: { '[@slideInOutAnimation]': '' }
})

export class LoginComponent{

  constructor (private loginService: LoginService, private route: ActivatedRoute, private router: Router){
        this.emailStatus = JSON.parse(localStorage.getItem("emailStatus"))
        if(this.emailStatus != null) {
          this.checkValidatedEmail();
        }
  }

  username: string = "";
  password: string = "";
  lastname: string = "";
  firstname: string = "";
  email: string = "";
  user: Register;
  status: Status;
  token: Token;
  showInputWarning: boolean = false;
  showIncorrectCredentials: boolean = false;
  showSuccesfulLogin: boolean = false;
  showValidatedEmail: boolean = false;
  showWrongValidatedEmail: boolean = false;
  emailStatus: Status;
  statusMessage: String = "";

  login(){

    if(this.isThereInput()){    
      sessionStorage.clear();
      //console.log(this.username, this.password);
      //console.log("Login Attempt!");
      this.user = new Register(this.username, this.firstname, this.lastname, this.email, this.password);
      let loginOperation: Observable<Status>;

      loginOperation = this.loginService.sendLoginData(this.user);
      loginOperation.subscribe((status: Status) => {
        this.status = status;
        this.token = new Token(this.status.message.split(" ")[1]);
        //console.log("Login Component: (status) " + this.status.operation, this.status.success, this.status.message);
        //console.log("Login Component: (token) " + this.token.token);
        sessionStorage.setItem(this.username, this.token.token);

        if(this.status.success){
          //console.log("Login Component: Successful login!");
          this.showIncorrectCredentials = false;
          this.showInputWarning = false;
          this.showSuccesfulLogin = true;
          this.statusMessage = status.message;
          setTimeout(() => this.router.navigate(['dashboard/files']), 2000);
        } else {
          this.showInputWarning = false;
          this.showIncorrectCredentials = true;
          this.statusMessage = status.message;
        }
      });

    } else {
      this.showInputWarning = true;
      this.showIncorrectCredentials = false;
    }
  }

  isThereInput(): boolean{
    if(this.password != "" && this.username != ""){
      return true;
    } else {
      return false;
    }
  }

  register(){
    // redirect to users view
    this.router.navigate(['register']);
  }

  checkValidatedEmail() {
    if(this.emailStatus.success) {
      this.showValidatedEmail = true;
      this.showWrongValidatedEmail = false;
    } else {
      this.showValidatedEmail = false;
      this.showWrongValidatedEmail = true;
    }
  }
}


