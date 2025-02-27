import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {User} from './models/user';
import {UsersService} from './services/users.service';
import {FormsModule} from '@angular/forms';


@Component({
  selector: 'users-home',
  imports: [
    NgForOf
  ],
  templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit{
  users?: User[];

  ngOnInit(): void {
    this.getUsers();
  }

  constructor(private userService: UsersService) {
  }


  getUsers(): void {
    this.userService.getUsers().subscribe(users => this.users = users);
  }




}
