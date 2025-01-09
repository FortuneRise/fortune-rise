import {Component, OnInit} from '@angular/core';
import {User} from './models/user';
import {UsersService} from './services/users.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AppComponent} from '../app.component';
import {NgIf} from '@angular/common';

@Component({
  selector: 'user-home',
  imports: [NgIf],
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit{
  user! : User;
  userId!: number;

  constructor(private userService: UsersService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {this.userId = +params['id']});
    this.getUser();
  }

  getUser(){
    this.userService.getUser(this.userId).subscribe(user => this.user = user);
  }
}
