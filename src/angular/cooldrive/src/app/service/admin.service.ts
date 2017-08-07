import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "./http.client";
import {Observable} from "rxjs/Observable";
import {Transaction} from "../model/transaction.model";


@Injectable()
export class AdminService {
  adminUrl: string;
  serviceError = false;

  constructor(private httpClient: HttpClient) {
    this.adminUrl = environment.urlPrefix + "/adminPage/transactions";
  }

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient.get(this.adminUrl)
      .catch((error: any) => Observable.throw(this.serviceError = true));
  }
}
