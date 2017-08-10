export class Transaction {
    userToken: string;
    id: number;
    firstName: string;
    lastName: string;
    zip: string;
    city: string;
    address1: string;
    address2: string;
    bought: number;
    phone: string;
    boughtDate: string;

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

    setBillingAddress(billingAddress: string): void{
        this.address2 = billingAddress;
    }

    getBillingAddress(): string{
        return this.address2;
    }


    getDate(): string {
      return this.boughtDate;
    }

    setDate(value: string) {
      this.boughtDate = value;
    }

    setId(id: number): void{
        this.id = id;
    }

    getId(): number{
        return this.id;
    }
}
