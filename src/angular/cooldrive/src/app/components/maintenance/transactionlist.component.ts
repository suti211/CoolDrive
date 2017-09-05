import { Component, OnInit } from "@angular/core";
import {Transaction} from "../../model/transaction.model";
import {AdminService} from "../../service/admin.service";
import {Observable} from "rxjs/Observable";


@Component({
  selector: "app-transactionlist",
  templateUrl: "./transactionlist.component.html",
  styleUrls: ["./transactionlist.component.css"],
  providers: [AdminService],
})

export class TransactionList implements OnInit {

  transactions: Transaction[];
  getTransactions: Observable<Transaction[]>;
  fasz: boolean;

  ngOnInit() {
    this.getTransactions = this.adminService.getTransactions();
    this.getTransactions.subscribe((trans: Transaction[]) => {
      this.transactions = trans;
      console.log(this.transactions);
    });
  }

  constructor(private adminService: AdminService) { }

}
