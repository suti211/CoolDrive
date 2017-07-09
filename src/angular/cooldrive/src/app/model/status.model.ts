export class Status{

    operation: String;
    succes: boolean;

    constructor( operation: String, success: boolean){
        this.operation = operation;
        this.succes = success;
    }
}