import { Injectable } from '@angular/core';
import { Router, ActivatedRoute, CanActivate } from '@angular/router';
import { TokenService } from '../service/token.service';
import { Observable } from 'rxjs/Rx';
import { Token } from '../model/token.model';
import { Status } from '../model/status.model';

@Injectable()
export class LoginGuard implements CanActivate {

    token: Token;

    constructor(private tokenService: TokenService, private router: Router) { }
    canActivate() {
        if (localStorage.length == 0) {
            this.router.navigate(['login']);
            //nincs semmi token
            return false;
        } else {
            //ha van token, megnézzük valid e
            let validateOperation: Observable<Status>;
            this.token = new Token(localStorage.getItem(localStorage.key(0)));


            validateOperation = this.tokenService.validateToken(this.token);
            validateOperation.subscribe((status: Status)=>{
                if(status.success){
                    console.log(status);
                    //valid a token
                    return true;
                } else {
                    console.log(status);
                    //invalid a token
                    this.router.navigate(['login']);
                    return false;
                }
            });
        }
    }
}