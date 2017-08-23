import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../service/transaction.service';
import { Transaction } from '../../model/transaction.model';
import { Observable } from 'rxjs/Rx';
import { Status } from '../../model/status.model';

@Component({
  selector: 'app-trans-status',
  templateUrl: './transaction.status.component.html',
  styleUrls: ['./transaction.status.component.css'],
})

export class TransactionStatus implements OnInit{

    constructor(private transService: TransactionService){

    }

    transaction: Transaction;
    transactionResult: Observable<Status>;
    successful: boolean;
    loaded: boolean = false;

    ngOnInit(){
        this.transaction = JSON.parse(localStorage.getItem("transaction"));
        this.transactionResult = this.transService.newTransaction(this.transaction);
        this.transactionResult.subscribe((status: Status) => {
            console.log(status);
            this.successful = status.success;
            console.log(this.successful);
            this.loaded = true;
        });
    }
}