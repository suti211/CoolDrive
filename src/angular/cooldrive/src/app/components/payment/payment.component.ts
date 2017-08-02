import { Component, OnInit } from '@angular/core';
import { Router, Route, ActivatedRoute } from '@angular/router';
import { Transaction } from '../../model/transaction.model';
import $ from 'jquery';
import './checkout.js';

declare var paypal: any;

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
})

export class PaymentComponent implements OnInit {

    constructor(private router: Router, private route: ActivatedRoute){}

    address: any;
    method: string;
    transaction: Transaction;
    cardOwner: string = "";
    cardNumber: string = "";
    expiration: string = "";
    cvt: string = "";
    showWarningPanel = false;
    paymentCost: number;

    ngOnInit(){
            this.transaction = JSON.parse(localStorage.getItem("transaction"));
            this.paymentCost = this.transaction.bought;

            paypal.Button.render({

            env: 'sandbox', // sandbox | production

            // PayPal Client IDs - replace with your own
            // Create a PayPal app: https://developer.paypal.com/developer/applications/create
            client: {
                sandbox:    'AfRk5B6UjMKxwH3n2td36LF25NKI94-TOqWijTBScWngS3Vr7PChGd1k6G49HmOf9lBX9jj-E8CzuwwS',
                production: 'AfRk5B6UjMKxwH3n2td36LF25NKI94-TOqWijTBScWngS3Vr7PChGd1k6G49HmOf9lBX9jj-E8CzuwwS'
            },

            // Show the buyer a 'Pay Now' button in the checkout flow
            commit: true,

            // payment() is called when the button is clicked
            payment: (data, actions) => {

                // Make a call to the REST api to create the payment
                return actions.payment.create({
                    payment: {
                        transactions: [
                            {
                                amount: { total: this.paymentCost, currency: 'USD' }
                            }
                        ],
                         redirect_urls: {
                            return_url : 'http://localhost:4200/dashboard/transaction',
                            cancel_url : 'http://localhost:4200/dashboard/storage'
                        }
                    }
                });
            },

            // onAuthorize() is called when the buyer approves the payment
            onAuthorize: function(data, actions) {

                // Make a call to the REST api to execute the payment
                return actions.payment.execute().then(() => {
                    window.alert('Payment Complete!');
                    actions.redirect();
                });
            },
        }, '#paypal-button-container');

        
    }

    formValid(): boolean {
        let isValid = true;

        let oField = document.getElementById("ownerField");
        let numField = document.getElementById("numberField");
        let dateField = document.getElementById("expDateField");
        let cvtField = document.getElementById("cvtField");

        if(this.cardOwner == ""){
            oField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            oField.style.backgroundColor = '#dff0d8';
        }

        if (this.cardNumber == ""){
            numField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
             numField.style.backgroundColor = '#dff0d8';
        }

        if (this.expiration == ""){
            dateField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            dateField.style.backgroundColor = '#dff0d8';
        }

        if (this.cvt == ""){
            cvtField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            cvtField.style.backgroundColor = '#dff0d8';
        }

        
        return isValid;
    }

    payWithCard(isValid: boolean){
        if(isValid){

        }
        
    }
}