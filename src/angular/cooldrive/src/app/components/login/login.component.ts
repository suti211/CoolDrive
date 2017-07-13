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
        
  }

  username: string;
  password: string;
  lastname = "";
  firstname = "";
  email = "";
  user: Register;
  status: Status;
  token: Token;

  login(){

    if(this.isThereInput()){
      
      
      console.log(this.username, this.password);
      console.log("Login Attempt!");
      this.user = new Register(this.username, this.firstname, this.lastname, this.email, this.password);
      let loginOperation: Observable<Status>;

      loginOperation = this.loginService.sendLoginData(this.user);
      loginOperation.subscribe((status: Status) => {
        this.status = status;
        this.token = new Token(this.status.message.split(" ")[1]);
        console.log(this.status);
        console.log(this.token);
        localStorage.setItem(this.username, this.token.token);

        if(this.status.success){
          setTimeout(this.router.navigate(['dashboard']), 1000);
        }
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

  register(){
    // redirect to users view
    this.router.navigate(['register']);
  }
}


