export class Register {

  userName: String;
  firstName: String;
  lastName: String;
  email: String;
  pass: String;

  constructor(username: String, firstname: String, lastname: String, email: String, password: String) {
    this.userName = username;
    this.firstName = firstname;
    this.lastName = lastname;
    this.email = email;
    this.pass = password;
  }
}
