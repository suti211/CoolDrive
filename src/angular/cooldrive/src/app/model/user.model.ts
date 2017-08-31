export class User{

    userName: String;
    password: String;
    email: String;
    validated: boolean;
    admin: boolean;
    registerDate: String;

    constructor(userName:String, password:String){
        this.userName = userName;
        this.password = password;
    }
}
