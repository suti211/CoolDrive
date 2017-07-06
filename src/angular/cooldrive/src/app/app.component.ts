import { Component } from '@angular/core';
//import { Component } from './components/login/login.component';
import { RouterModule, Routes} from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  styles: ['@import "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css";']
})

//const appRoutes: Routes = [
//  {path: 'login', component: 'login'},
//  {path: '**', component: 'PageNotFoundComponent'}
//];

export class AppComponent {
  title = 'app';
}
