import {Component, OnInit} from '@angular/core';
import {Game} from '../games/models/game';
import {HistoryService} from './services/history.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CommonModule, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'game-history',
  imports: [
    NgForOf,
    NgIf,
    CommonModule
  ],
  templateUrl: 'gamehistory.component.html'
})
export class GameHistoryComponent implements OnInit{
  games: Game[] = [];
  userId: number = +(sessionStorage.getItem("username")?? 0);
  currentPage:number = 1;
  limit:number = 10;
  offset:number = 0;
  totalGames:number = 0;

  constructor(private historyService : HistoryService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getGamesHistory();
  }

  getGamesHistory() {
    this.historyService.getGames(this.userId,this.limit,this.offset)
      .subscribe(result => {this.games = result.games; this.totalGames = result.totalCount});
  }

  changePage(pageNumber: number){
    this.currentPage = pageNumber;
    this.offset = (pageNumber - 1) * this.limit
    //console.log(this.totalNotifications);
    this.getGamesHistory();
  }

  test(){
    console.log(this.userId);
  }

  goBack() {
    this.router.navigate(["history"]);
  }

  goToBets(gameId:number){
    this.router.navigate(["history/games/" + gameId]);
  }

  protected readonly Math = Math;
}
