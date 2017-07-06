import { Component } from '@angular/core';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  title = 'Login';
  username: String;
  password: String;

  xxx(){
    console.log(this.username, this.password);
  }

}


