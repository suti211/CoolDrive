export class Status {
    operation: String;
    success: boolean;
    message: String;

    constructor( operation: String, success: boolean, message: String){
        this.operation = operation;
        this.success = success;
        this.message = message;
    }
}
