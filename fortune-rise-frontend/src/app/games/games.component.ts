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
  userId:number  = +(sessionStorage.getItem("username")?? 0);

  betTypes: string[] = ['STRAIGHT','SPLIT','STREET','CORNER','SIX_LINE','COLOR','PARITY','COLUMN','DOZEN','HIGH_LOW'];
  values: string[] = [];
  selectedType: string = '';
  selectedValue: string = '';

  betTypeToValue = {
    STRAIGHT: ['0','1','2','3','4','5','6','7','8','9','10', '11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36'],
    SPLIT: ['Delhi', 'Mumbai', 'Bangalore'],
    STREET: ['1,2,3', '4,5,6', '7,8,9','10,11,12','13,14,15','16,17,18','19,20,21','22,23,24','25,26,27','28,29,30','31,32,33','34,35,36'],
    CORNER: ['Toronto', 'Vancouver', 'Montreal'],
    SIX_LINE: ['Toronto', 'Vancouver', 'Montreal'],
    COLOR: ['Red', 'Black'],
    PARITY: ['Odd', 'Even'],
    COLUMN: ['Column 1', 'Column 2', 'Column 3'],
    DOZEN: ['1-12', '13-24', '25-36'],
    HIGH_LOW: ['1-18', '19-36']
  };

  constructor(private gameService: GamesService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
  }

  isValidKey(key: string): key is keyof typeof this.betTypeToValue {
    return key in this.betTypeToValue;
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
