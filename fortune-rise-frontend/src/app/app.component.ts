import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import {UsersService} from './users/services/users.service';
import {FormsModule} from '@angular/forms';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'fortune-rise-frontend';
  userId! : number;
  username!: string;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  navigateUser(){
    this.router.navigate(["user"]);
  }

  navigateWallet(){
    this.router.navigate(["wallet"]);
  }

  navigateGame(){
    this.router.navigate(["game"]);
  }

  navigateHistory(){
    this.router.navigate(["history"]);
  }

  navigateNotifications(){
    this.router.navigate(["notifications"])
  }

  navigateLogin(){
    this.router.navigate(["login"]);
  }

  testUser(){
    console.log(sessionStorage.getItem("username"));
  }





}
