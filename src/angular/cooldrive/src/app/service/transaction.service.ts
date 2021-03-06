import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Transaction } from '../model/transaction.model';
import { Status } from '../model/status.model';

@Injectable()
export class TransactionService{
    transactionServiceUrl = "http://localhost:8080/CoolDrive/transaction";
    //loginUrl = "http://demo1158757.mockable.io/loginTest";

    constructor(private http:Http){

    }
    newTransaction(transaction: Transaction): Observable<Status>{
        let bodyString = JSON.stringify(transaction);
        //console.log("body JSON: " + bodyString);

        let headers = new Headers({'Content-Type' : 'application/json'});
        let options = new RequestOptions({headers : headers});

         return this.http.post(this.transactionServiceUrl, bodyString, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
    }
}