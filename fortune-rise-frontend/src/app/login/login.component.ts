import { Component } from '@angular/core';
import {UsersService} from '../users/services/users.service';
import {User} from '../users/models/user';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username!: string;
  user: User = new User;

  constructor(private userService: UsersService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  logInUser(){
    this.userService.getUserByUsername(this.username).subscribe(users => sessionStorage.setItem('username', users[0].id.toString()));
    this.goBack();
  }

  addNewUser(): void {
    this.userService.createUser(this.user).subscribe(user => sessionStorage.setItem('username', user.id.toString()));
    this.goBack();
  }

  goBack() {
    this.router.navigate([""]);
  }
}
