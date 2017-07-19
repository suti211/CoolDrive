import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Token } from '../model/token.model';
import { Status } from '../model/status.model';

@Injectable()
export class TokenService{
    tokenUrl = "http://localhost:8080/CoolDrive/token";
    //loginUrl = "http://demo1158757.mockable.io/loginTest";

    constructor(private http:Http){

    }

    getToken(body: Object): Observable<Token>{

        let bodyString = JSON.stringify(body);
        console.log("body JSON: " + bodyString);

        let headers = new Headers({'Content-Type' : 'application/json'});
        let options = new RequestOptions({headers : headers});

        return this.http.post(this.tokenUrl, bodyString, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }

    validateToken(token: Token): Observable<Status>{
        let bodyString = JSON.stringify(token);
        //console.log("body JSON: " + bodyString);

        let headers = new Headers({'Content-Type' : 'application/json'});
        let options = new RequestOptions({headers : headers});

         return this.http.post(this.tokenUrl, bodyString, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }
}