import { Component, OnInit } from "@angular/core";
import {User} from "../../model/user.model";
import {AdminService} from "../../service/admin.service";
import {Observable} from "rxjs/Observable";
import {FilterListener} from "../maintenance/filterlistener";
import {FilterService} from "../../service/filter.service";

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.css"],
  providers: [AdminService]
})
export class UsersComponent implements OnInit, FilterListener{

  users: User[] = [];
  filteredUsers: User[] = [];
  getUsersOperation: Observable<User[]>;
  isResult = true;

  constructor(private adminService: AdminService, private filterService: FilterService) {
    this.filterService.listener = this;
  }

  ngOnInit() {
    this.getUsersOperation = this.adminService.getAllUsers();
    this.getUsersOperation.subscribe((users: User[]) => {
      this.users = users;
      for(let user of users){
        this.filteredUsers.push(user);
        console.log(user);
      }
    });
  }

  onFiltered(filter: string){
    this.filter(filter);
    if(this.filteredUsers.length == 0){
      this.isResult = false;
    } else {
      this.isResult = true;
    }
  }

  filter(filter: string){
    if(filter.length == 0){
      this.filteredUsers.length = 0;
      for(let user of this.users){
        this.filteredUsers.push(user);
      }
    } else {
      this.filteredUsers.length = 0;
      for( let user of this.users){
        if(user.userName.toLowerCase().indexOf(filter) != -1 || user.userName.indexOf(filter) != -1
          || user.email.toLowerCase().indexOf(filter) != -1 || user.registerDate.indexOf(filter) != -1){
          this.filteredUsers.push(user);
        }
      }
    }
  }

}
