import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Transaction } from '../model/transaction.model';
import { Status } from '../model/status.model';
import { environment } from "../../environments/environment"
import {Router} from "@angular/router";
import {HttpClient} from "./http.client";

@Injectable()
export class TransactionService{
    transactionServiceUrl = environment.urlPrefix + "/" + "transaction";

    constructor(private http: HttpClient, private router: Router){

    }
    newTransaction(transaction: Transaction): Observable<Status>{
        let bodyString = JSON.stringify(transaction);

         return this.http.post(this.transactionServiceUrl, <String>bodyString)
            .map((res: Response) => res.json())
            .catch((error: any) => {
              if(error.status === 403){
                this.router.navigate(["unauthorized"])
                return Observable.throw(new Error(error.status));
              }
            });
    }
}
