import { Component, OnInit } from "@angular/core";
import {User} from "../../model/user.model";
import {AdminService} from "../../service/admin.service";
import {Observable} from "rxjs/Observable";

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.css"],
  providers: [AdminService]
})
export class UsersComponent implements OnInit {

  users: User[];
  getUsersOperation: Observable<User[]>;

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.getUsersOperation = this.adminService.getAllUsers();
    this.getUsersOperation.subscribe((users: User[]) => {
      this.users = users;
      console.log(this.users);
    });
  }

}
