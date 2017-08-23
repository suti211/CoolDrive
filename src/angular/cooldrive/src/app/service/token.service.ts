import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Token } from '../model/token.model';
import { Status } from '../model/status.model';
import { environment } from "../../environments/environment"
import {HttpClient} from "./http.client";

@Injectable()
export class TokenService{
    tokenUrl = environment.urlPrefix + "/";

    constructor(private http: HttpClient) {

    }

    getToken(body: Object, requestUrl: string): Observable<Token>{

        let bodyString = JSON.stringify(body);
        console.log("body JSON: " + bodyString);

        return this.http.post(this.tokenUrl + "token/tokentest", <String> bodyString)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }

    validateToken(token: Token, requestUrl: string): Observable<Status>{
        let bodyString = JSON.stringify(token);

        return this.http.post(this.tokenUrl + "token/tokentest", <String>bodyString)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }

    isTokenAdmin(token: Token): Observable<Status>{
        let bodyString = JSON.stringify(token);

        return this.http.post(this.tokenUrl + "token/admintest", <String>bodyString)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }
}
