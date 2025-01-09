import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'fortune-rise-frontend';
  userId : number = 1;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  navigateUser(){
    this.router.navigate(["users/" + this.userId]);
  }

  navigateWallet(){
    this.router.navigate(["wallet/" + this.userId]);
  }

  navigateGame(){
    this.router.navigate(["game/" + this.userId]);
  }




}
