import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CheckoutService } from '../../service/checkout.service';
import { Transaction } from '../../model/transaction.model';
import { Token } from '../../model/token.model';
import { Observable } from 'rxjs/Rx';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})

export class CheckoutComponent implements OnInit{

    transaction: any;
    id: number;
    orderFetch: Observable<Transaction>;
    order: Transaction;
    token: Token;
    loaded: boolean = false;

    constructor(private route: ActivatedRoute, private checkoutService: CheckoutService){

    }

    ngOnInit(){
            this.transaction = this.route.params.subscribe(params => {
                this.id = params['id'];
            });

            this.token = new Token(localStorage.getItem(localStorage.key(0)));
            this.token.setID(this.id);
            this.orderFetch = this.checkoutService.getNewTransactionID(this.token);
            this.orderFetch.subscribe((transaction : Transaction) => {
                this.order = new Transaction(localStorage.getItem(localStorage.key(0)), transaction.firstName, transaction.lastName, transaction.zip, transaction.city, transaction.address1, transaction.phone, transaction.bought);
                console.log(this.order);
                this.loaded = true;
        });
    }

}