export class Status {
    operation: string;
    success: boolean;
    message: string;

    constructor( operation: string, success: boolean, message: string){
        this.operation = operation;
        this.success = success;
        this.message = message;
    }
}
