import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Transaction } from '../model/transaction.model';
import { Token } from '../model/token.model';

@Injectable()
export class CheckoutService{
    checkoutServiceUrl = "http://localhost:8080/CoolDrive/checkout";

    constructor(private http:Http){

    }

    getNewTransactionID(token: Token): Observable<Transaction>{
        
        let headers = new Headers({'Content-Type' : 'application/json'});
        let options = new RequestOptions({headers : headers});

         return this.http.post(this.checkoutServiceUrl, JSON.stringify(token), options)
            .map((res: Response) => res.json());
    }
}