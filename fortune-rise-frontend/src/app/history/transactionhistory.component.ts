import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {HistoryService} from './services/history.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Transaction} from '../wallets/models/transaction';

@Component({
  selector: 'transaction-history',
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: 'transactionhistory.component.html'
})
export class TransactionhistoryComponent implements OnInit{
  transactions: Transaction[] = [];
  userId: number = +(sessionStorage.getItem("username")?? 0);
  currentPage:number = 1;
  limit:number = 10;
  offset:number = 0;
  totalTransactions:number = 0;


  constructor(private historyService : HistoryService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getTransactionHistory();
  }

  getTransactionHistory() {
    this.historyService.getTransactions(this.userId,this.limit,this.offset)
      .subscribe(result => {this.transactions = result.transactions; this.totalTransactions = result.totalCount});
  }

  changePage(pageNumber: number){
    this.currentPage = pageNumber;
    this.offset = (pageNumber - 1) * this.limit
    //console.log(this.totalNotifications);
    this.getTransactionHistory();
  }

  goBack() {
    this.router.navigate(["history"]);
  }

  protected readonly Math = Math;
}
