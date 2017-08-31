import { Component, OnInit } from "@angular/core";
import {Transaction} from "../../model/transaction.model";
import {AdminService} from "../../service/admin.service";
import {Observable} from "rxjs/Observable";
import {FilterService} from "../../service/filter.service";
import {FileService} from "../../service/files.service";
import {FilterListener} from "./filterlistener";


@Component({
  selector: "app-transactionlist",
  templateUrl: "./transactionlist.component.html",
  styleUrls: ["./transactionlist.component.css"],
  providers: [AdminService],
})

export class TransactionList implements OnInit, FilterListener{

  transactions: Transaction[] = [];
  getTransactions: Observable<Transaction[]>;
  filteredTransactions: Transaction[] = [];
  isResult = true;

  onFiltered(filter: string){
    this.filter(filter);
    if(this.filteredTransactions.length == 0){
      this.isResult = false;
    } else {
      this.isResult = true;
    }
  }

  ngOnInit() {

  }

  constructor(private adminService: AdminService, private filterService: FilterService) {
    this.filterService.listener = this;
    this.getTransactions = this.adminService.getTransactions();
    this.getTransactions.subscribe((trans: Transaction[]) => {
      this.transactions = trans;
      for(let transact of trans){
        this.filteredTransactions.push(transact);
      }
    });
  }

  filter(filter: string){
    if(filter.length == 0){
      this.filteredTransactions.length = 0;
      for(let trans of this.transactions){
        this.filteredTransactions.push(trans);
      }
    } else {
      this.filteredTransactions.length = 0;
      for( let trans of this.transactions){
        if((trans.firstName.toLowerCase() + " " + trans.lastName.toLowerCase()).indexOf(filter) != -1
          || trans.boughtDate.indexOf(filter) != -1
          || trans.phone.indexOf(filter)!= -1
          || trans.bought.toString().indexOf(filter) != -1
          || trans.address1.toLowerCase().indexOf(filter) != -1
          || trans.address1.indexOf(filter) != -1
          || trans.address2.toLowerCase().indexOf(filter) != -1
          || trans.address2.indexOf(filter) != -1
          || trans.zip.indexOf(filter) != -1){
            this.filteredTransactions.push(trans);
        }
      }
    }
  }

}
