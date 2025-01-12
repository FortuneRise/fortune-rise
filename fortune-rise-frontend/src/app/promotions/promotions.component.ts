import { Component } from '@angular/core';
import {Promotion} from './models/promotion';
import {ActivatedRoute, Router} from '@angular/router';
import {PromotionsService} from './services/promotions.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-promotions',
  imports: [
    FormsModule
  ],
  templateUrl: './promotions.component.html',
  styleUrl: './promotions.component.css'
})
export class PromotionsComponent {
  userId: number = +(sessionStorage.getItem("username")?? 0);
  newGamePromotion : Promotion = new Promotion();
  promotionAmount!:number;
  currentUserIds!: string;

  constructor(private promotionService: PromotionsService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  addGamePromotionToUser(){
    this.newGamePromotion.type = "FREE_BET"
    this.newGamePromotion.triggerScenario = "BET"
    this.newGamePromotion.parameters = [this.promotionAmount];

    const userIds = this.currentUserIds.split(',')
      .map(num => num.trim()) // Remove extra spaces
      .filter(num => !isNaN(Number(num))) // Filter out non-numeric values
      .map(num => Number(num));

    console.log(userIds);


    this.promotionService.addPromotion(this.newGamePromotion).subscribe(promotion => {
      userIds.forEach(id => {
        this.promotionService.linkUserToPromotion(id, promotion.id).subscribe({
          next: () => console.log(`Linked user ${id} to promotion ${promotion.id}`),
          error: err => console.error(`Failed to link user ${id}:`, err),
        });
      });
    });

    this.newGamePromotion = new Promotion();
  }

}
