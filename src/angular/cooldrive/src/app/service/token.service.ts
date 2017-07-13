import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Token } from '../model/token.model';

@Injectable()
export class TokenService{
    loginUrl = "http://localhost:8080/CoolDrive/login";
    //loginUrl = "http://demo1158757.mockable.io/loginTest";

    constructor(private http:Http){

    }

    getToken(body: Object): Observable<Token>{

        let bodyString = JSON.stringify(body);
        console.log("body JSON: " + bodyString);

        let headers = new Headers({'Content-Type' : 'application/json'});
        let options = new RequestOptions({headers : headers});

        return this.http.post(this.loginUrl, bodyString, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }
}