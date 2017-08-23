import { Injectable } from '@angular/core';
import { Router, ActivatedRoute, CanActivate } from '@angular/router';
import { TokenService } from '../service/token.service';
import { Observable } from 'rxjs/Rx';
import { Token } from '../model/token.model';
import { Status } from '../model/status.model';

@Injectable()
export class LoginGuard implements CanActivate {

    token: Token;
    status: Status;
    passed: boolean;
    validateOperation: Observable<Status>;
    
    constructor(private tokenService: TokenService, private router: Router) {
     }

    canActivate() {

        if (sessionStorage.length == 0) {
            //nincs semmi token
            this.router.navigate(['login']);
            return false;
        }

        this.token = new Token(sessionStorage.getItem(sessionStorage.key(0)));
        this.validateOperation = this.tokenService.validateToken(this.token, "token");

        return this.validateOperation.map(status => status.success);
    }
}