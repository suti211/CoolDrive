import { Component, OnInit } from "@angular/core";
import {Transaction} from "../../model/transaction.model";
import {AdminService} from "../../service/admin.service";
import {Observable} from "rxjs/Observable";


@Component({
  selector: "app-maintenance",
  templateUrl: "./maintenance.component.html",
  styleUrls: ["./maintenance.component.css"],
  providers: [AdminService],
})

export class MaintenanceComponent implements OnInit {

  transactions: Transaction[];
  getTransactions: Observable<Transaction[]>;

  ngOnInit() {
    this.getTransactions = this.adminService.getTransactions();
    this.getTransactions.subscribe((trans: Transaction[]) => {
      this.transactions = trans;
      console.log(trans);
    });
  }

  constructor(private adminService: AdminService) { }

}
