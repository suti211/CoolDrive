import { Component } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Observable } from 'rxjs/Rx';
import { Register } from '../../model/register.model';
import { Status } from '../../model/status.model'
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { Router, ActivatedRoute } from '@angular/router';

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

  username: String;
  password: String;
  lastname = "";
  firstname = "";
  email = "";
  user: Register;
  status: Status;

  login(){

    if(this.isThereInput()){

      console.log(this.username, this.password);
      console.log("Login Attempt!");
      this.user = new Register(this.username, this.firstname, this.lastname, this.email, this.password);
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

  register(){
    // redirect to users view
    this.router.navigate(['register']);
  }
}


