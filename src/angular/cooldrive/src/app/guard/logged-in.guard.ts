import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {Token} from "../model/token.model";
import {Status} from "../model/status.model";
import {TokenService} from "../service/token.service";

@Injectable()
export class LoggedInGuard implements CanActivate {


  constructor(private tokenService: TokenService, private router: Router) {
  }

  canActivate(){
    let token = this.creatToken();
    let checkUserTokenOperation: Observable<Status>;
    checkUserTokenOperation = this.tokenService.validateToken(token, "");

    if(checkUserTokenOperation.map(status => status.success)){
      if (sessionStorage.length == 0) {
        return true;
      }
      this.router.navigate(['dashboard/files']);
    }
    return true;
  }

  creatToken(): Token {
    let tokenID = sessionStorage.getItem(sessionStorage.key(0));
    let newToken = new Token(tokenID);
    return newToken;
  }
}
