import {Injectable, NgModule} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "./http.client";
import {Observable} from "rxjs/Observable";
import {Transaction} from "../model/transaction.model";
import {Response} from "@angular/http";
import {Router} from "@angular/router";
import {User} from "../model/user.model";

@Injectable()
export class AdminService {
  adminUrl: string;
  serviceError = false;

  constructor(private httpClient: HttpClient, private router: Router) {
    this.adminUrl = environment.urlPrefix + "/adminpage";
  }

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient.get(this.adminUrl + "/transactions")
      .map((res: Response) => res.json())
      .catch((error: any) => {
        if(error.status === 403){
          this.router.navigate(["dashboard/unauthorized"]);
          return Observable.throw(new Error(error.status))
        }
      });
  }

  getAllUsers(): Observable<User[]>{
    return this.httpClient.get(this.adminUrl + "/users")
      .map((res: Response) => res.json())
      .catch((error: any) => {
        if(error.status === 403){
          this.router.navigate(["dashboard/unauthorized"]);
          return Observable.throw(new Error(error.status))
        }
      });
  }
}
