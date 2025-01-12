import {Component, OnInit} from '@angular/core';
import {HistoryService} from './services/history.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Bet} from '../games/models/bet';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'bet-history',
  imports: [
    NgForOf
  ],
  templateUrl: 'betHistory.component.html'
})
export class BethistoryComponent implements OnInit{
  bets: Bet[] = [];
  gameId!:number;

  constructor(private historyService : HistoryService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.getBets(+params['gameId']);
      this.gameId = +params['gameId'];
    });
  }

  getBets(gameId:number){
    this.historyService.getBets(gameId).subscribe(betlist => this.bets = betlist);
  }

  goBack() {
    this.router.navigate(["history/games"]);
  }
}
