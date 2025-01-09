import {Component, OnInit} from '@angular/core';
import {GamesService} from './services/games.service';
import {Game} from './models/game';
import {Bet} from './models/bet';
import {FormsModule} from '@angular/forms';
import {NgFor, NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-games',
  imports: [
    FormsModule,
    NgIf,
    NgFor
  ],
  templateUrl: './games.component.html',
  styleUrl: './games.component.css'
})
export class GamesComponent implements OnInit{
  game: Game = new Game
  currentBets: Bet[] = []
  currentBet: Bet = new Bet()
  tempField!: string
  userId!:number

  constructor(private gameService: GamesService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {this.userId = +params['userId']});
  }

  submitBetForm(){
    this.currentBet.fields = this.tempField.split(',')
      .map(num => num.trim()) // Remove extra spaces
      .filter(num => !isNaN(Number(num))) // Filter out non-numeric values
      .map(num => Number(num));
    this.currentBets.push(this.currentBet);
    console.log(this.currentBet);
    console.log(this.currentBets);
    this.currentBet = new Bet();

  }

  playBets(){
    this.gameService.createGame(this.userId,this.currentBets).subscribe(game => this.game = game);
    this.currentBets = [];
  }
}
