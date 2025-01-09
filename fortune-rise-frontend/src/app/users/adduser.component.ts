import {Component} from '@angular/core';
import {User} from './models/user';
import {UsersService} from './services/users.service';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'add-user',
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: 'adduser.component.html'
})
export class AdduserComponent {
  user: User = new User

  constructor(private userService : UsersService) {
  }

  submitUserForm(): void {
    this.userService.createUser(this.user).subscribe();
  }
}
