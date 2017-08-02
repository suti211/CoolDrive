import { Component, OnChanges, Input } from '@angular/core';
import { RouterModule, Routes, Router} from '@angular/router';
import { TransactionService } from '../../service/transaction.service';
import { Token } from '../../model/token.model';
import { Status } from '../../model/status.model';
import { Transaction } from '../../model/transaction.model';
import { Observable } from 'rxjs/Rx';


@Component({
  selector: 'app-storage',
  templateUrl: './extension.component.html',
  styleUrls: ['./extension.component.css'],
})


export class ExtensionComponent{
    token: Token = new Token(sessionStorage.getItem(sessionStorage.key(0)));
    storage: number;
    card1Color: String = "";
    card2Color: String = "";
    card3Color: String = "";
    showOrderDetails: boolean = false;
    showWarningPanel: boolean = false;
    showSuccessPanel: boolean = false;
    showStorageWarning: boolean = false;
    transaction: Transaction;
    transactionResult: Observable<Status>;

    firstName: string = "";
    lastName: string = "";
    zipCode: string = "";
    city: string = "";
    phone: string = "";
    address: string= "";
    billingAddress: string = "none";

    constructor(private router: Router, private transService: TransactionService){
    }

    change1mbHeader(){
        console.log(this.storage);
        this.card1Color = "lightgreen";
        this.card2Color = "red";
        this.card3Color = "red";
    }

    change5mbHeader(){
        this.card1Color = "red";
        this.card2Color = "lightgreen";
        this.card3Color = "red";
    }

    change10mbHeader(){
        this.card1Color = "red";
        this.card2Color = "red";
        this.card3Color = "lightgreen";
    }

    showForm(){
        if(this.showOrderDetails){
            this.showOrderDetails = false;
        } else {
            this.showOrderDetails = true;
        }
        
    }

    checkInput(): boolean{

        let isValid = true;

        let fNameField = document.getElementById("fNameField");
        let lNameField = document.getElementById("lNameField");
        let cityNameField = document.getElementById("cityNameField");
        let zipField = document.getElementById("zipField");
        let phoneField = document.getElementById("phoneField");
        let addressField = document.getElementById("addressField");

        if(this.firstName == ""){
            fNameField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            fNameField.style.backgroundColor = '#dff0d8';
        }

        if (this.lastName == ""){
            lNameField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
             lNameField.style.backgroundColor = '#dff0d8';
        }

        if (this.zipCode == ""){
            zipField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            zipField.style.backgroundColor = '#dff0d8';
        }

        if (this.city == ""){
            cityNameField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            cityNameField.style.backgroundColor = '#dff0d8';
        }

        if (this.phone == ""){
            phoneField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            phoneField.style.backgroundColor = '#dff0d8';
        }

        if (this.address == ""){
            addressField.style.backgroundColor = '#f2dede';
            this.showWarningPanel = true;
            isValid = false;
        } else {
            addressField.style.backgroundColor = '#dff0d8';
        }

        if(this.storage == null){
            this.showWarningPanel = true;
            this.showStorageWarning = true;
            this.colorRadioButtons('#f2dede');

            isValid = false;
        } 

        console.log("form valid: " + isValid);
        return isValid;
    }

    initOrder(isFormValid: boolean){
        if(isFormValid){
            this.colorRadioButtons('#dff0d8');
            this.showWarningPanel = false;
            this.showStorageWarning = false;
            this.showSuccessPanel = true;
            this.transaction = new Transaction(sessionStorage.getItem(sessionStorage.key(0)), this.firstName, this.lastName, this.zipCode, this.city,this.address, this.phone, this.storage);

            if(this.billingAddress != ""){
                this.transaction.setBillingAddress(this.billingAddress);
            }

            localStorage.removeItem("transaction");
            localStorage.setItem("transaction", JSON.stringify(this.transaction));
            /*this.transactionResult = this.transService.newTransaction(this.transaction);
            console.log(this.transaction);
            this.transactionResult.subscribe((status : Status) => {
                console.log(status);
                if(status.success){
                    console.log("siker");
                    setTimeout(() => this.router.navigate(['dashboard/checkout', status.message]), 2000 );
                }
            }); */

            setTimeout(() => this.router.navigate(['dashboard/checkout']), 2000 );
        }
    }

    colorRadioButtons(color: string){
        let radios = document.getElementsByClassName("input-group-addon warning");
            Array.from(radios).forEach((item: HTMLElement) => {
                item.style.backgroundColor = color;
            })
    }
    
}



