import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})

export class CheckoutComponent implements OnInit, OnDestroy{

    transaction: any;

    constructor(private route: ActivatedRoute){

    }

    ngOnInit(){
        this.transaction = this.route.params.subscribe(params => {
            this.transaction = params['id'];
            console.log(this.transaction);
        })
    }

    ngOnDestroy(){
        this.transaction.unsubscribe();
    }

}