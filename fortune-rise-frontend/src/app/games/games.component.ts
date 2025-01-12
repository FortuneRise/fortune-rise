import {Component, OnInit} from '@angular/core';
import {GamesService} from './services/games.service';
import {Game} from './models/game';
import {Bet} from './models/bet';
import {FormsModule} from '@angular/forms';
import {NgFor, NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {Promotion} from '../promotions/models/promotion';
import {PromotionsService} from '../promotions/services/promotions.service';

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

  gamePromotions: Promotion[] = [];
  currentPromotion!: Promotion;
  hasAppliedPromotion: boolean = false;
  listOfUsedPromotions: Promotion[] = [];

  betTypeToValue: Map<string, string[]> = new Map<string, string[]>();

  constructor(private gameService: GamesService,
              private promotionService : PromotionsService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.betTypeToValue.set("STRAIGHT", ["0","1","2","3","4","5","6","7","8","9","10", "11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"]);
    this.betTypeToValue.set("SPLIT", ["1,2","1,4","2,3","2,5","3,6","4,5","4,7","5,6","5,8","6,9","7,8","7,10","8,9","8,11","9,12","10,11","10,13","11,12","11,14","12,15","13,14","13,16","14,15","14,17","15,18","16,17","16,19","17,18","17,20","18,21","19,20","19,22","20,21","20,23","21,24","22,23","22,25","23,24","23,26","24,27","25,26","25,28","26,27","26,29","27,30","28,29","28,31","29,30","29,32","30,33","31,32","31,34","32,33","32,35","33,36","34,35","35,36"]);
    this.betTypeToValue.set("STREET", ["1,2,3", "4,5,6", "7,8,9","10,11,12","13,14,15","16,17,18","19,20,21","22,23,24","25,26,27","28,29,30","31,32,33","34,35,36"]);
    this.betTypeToValue.set("CORNER", ["1,2,4,5","2,3,5,6","4,5,7,8","5,6,8,9","7,8,10,11","8,9,11,12","10,11,13,14","11,12,14,15","13,14,16,17","14,15,17,18","16,17,19,20","17,18,20,21","19,20,22,23","20,21,23,24","22,23,25,26","23,24,26,27","25,26,28,29","26,27,29,30","28,29,31,32","29,30,32,33","31,32,34,35","32,33,35,36"]);
    this.betTypeToValue.set("SIX_LINE", ["1,2,3,4,5,6","7,8,9,10,11,12","13,14,15,16,17,18","19,20,21,22,23,24","25,26,27,28,29,30","31,32,33,34,35,36"]);
    this.betTypeToValue.set("COLOR", ["Red", "Black"]);
    this.betTypeToValue.set("PARITY", ["Odd", "Even"]);
    this.betTypeToValue.set("COLUMN", ["Column 1", "Column 2", "Column 3"]);
    this.betTypeToValue.set("DOZEN", ["1-12", "13-24", "25-36"]);
    this.betTypeToValue.set("HIGH_LOW", ["1-18", "19-36"]);

    this.getGamePromotions();
  }


  onTypeChange() {
    // Set the cities based on the selected country
    this.values = (this.betTypeToValue.get(this.selectedType) || [""]);
    // Reset the selected city whenever the country changes
    this.selectedValue = "";
  }

  submitBetForm(){
    this.currentBet.type = this.selectedType;
    this.stringToBetField();
    this.currentBets.push(this.currentBet);
    //console.log(this.currentBet);
    //console.log(this.currentBets);
    this.currentBet = new Bet();
    this.hasAppliedPromotion = false;
    this.listOfUsedPromotions.push(this.currentPromotion);
    this.currentPromotion = new Promotion();

  }

  stringToBetField(){
    if(this.selectedType == "STRAIGHT" || this.selectedType == "SPLIT" || this.selectedType == "STREET" || this.selectedType == "CORNER" || this.selectedType == "SIX_LINE"){
      this.currentBet.fields = this.selectedValue.split(',')
        .map(num => num.trim()) // Remove extra spaces
        .filter(num => !isNaN(Number(num))) // Filter out non-numeric values
        .map(num => Number(num));
    }else if(this.selectedType == "COLOR"){
      let color = [0];
      if (this.selectedValue == "Black") {color = [1];}
      this.currentBet.fields = color;
    }else if(this.selectedType == "PARITY"){
      let parity = [0];
      if (this.selectedValue == "Odd") {parity = [1];}
      this.currentBet.fields = parity;
    }else if(this.selectedType == "COLUMN"){
      let column = [3];
      if(this.selectedValue == "Column 1"){
        column = [0];
      }else if(this.selectedValue == "Column 2"){
        column = [1];
      }else if(this.selectedValue == "Column 3"){
        column = [2]
      }
      this.currentBet.fields = column;
    }else if(this.selectedType == "DOZEN"){
      let dozen = [3];
      if(this.selectedValue == "1-12"){
        dozen = [0];
      }else if(this.selectedValue == "13-24"){
        dozen = [1];
      }else if(this.selectedValue == "25-36"){
        dozen = [2]
      }
      this.currentBet.fields = dozen;
    }else if(this.selectedType == "HIGH_LOW"){
      let hl = [0];
      if (this.selectedValue == "19-36") {hl = [1];}
      this.currentBet.fields = hl;
    }
  }

  playBets(){
    for(const p of this.listOfUsedPromotions){
      if(p.id != undefined){
        this.promotionService.applyPromotion(this.userId, p.id).subscribe();
        console.log(p)
      }
    }
    this.gameService.createGame(this.userId,this.currentBets).subscribe(game => this.game = game);
    this.currentBets = [];
    this.getGamePromotions();
  }


  getGamePromotions(){
    this.promotionService.getPromotionsByTrigger(this.userId,"bet").subscribe(promotions => this.gamePromotions = promotions);
  }

  selectPromotion(promotionId:number){
    this.currentPromotion = (this.gamePromotions.find(promotion => promotion.id === promotionId) || new Promotion());
    this.hasAppliedPromotion = true;
    this.currentBet.betAmount = this.currentPromotion.parameters[0];
  }

  test(){
    console.log(this.betTypes);
    console.log(this.selectedType);
    console.log(this.values);
    console.log(this.selectedValue);
  }

}
