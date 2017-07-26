export class Transaction{
    userToken: string;
    firstName: string;
    lastName: string;
    zip: string;
    city: string;
    address1: string;
    bought: number;
    phone: string;

    constructor(userToken: string, firstName: string, lastName: string, zipCode: string, city: string, address: string, phone: string, storagePack: number){
        this.userToken = userToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zipCode;
        this.city = city;
        this.address1 = address;
        this.phone = phone;
        this.bought = storagePack;
    }
}