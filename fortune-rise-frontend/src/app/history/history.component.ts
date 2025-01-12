import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-history',
  imports: [],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {


  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  navigateGameHistory():void {
    this.router.navigate([this.router.url + "/games"])
  }

  navigateTransactionHistory():void {
    this.router.navigate([this.router.url + "/transactions"])
  }
}
