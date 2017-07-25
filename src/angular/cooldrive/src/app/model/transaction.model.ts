export class Transaction{
    userName: string;
    firstName: string;
    lastName: string;
    zipCode: number;
    city: string;
    address: string;
    storagePack: number;

    constructor(user: string, firstName: string, lastName: string, zipCode: number, city: string, address: string, storagePack: number){
        this.userName = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.address = address;
        this.storagePack = storagePack;
    }
}