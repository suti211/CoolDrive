import { Injectable } from '@angular/core';
import { Router, ActivatedRoute, CanActivate } from '@angular/router';

@Injectable()
export class LoginGuard implements CanActivate{

constructor (private router : Router){

}

    canActivate(){
        if(localStorage.length == 0){
            this.router.navigate(['login']);
            return false;
        } else {
            return true;
        }
    }
}