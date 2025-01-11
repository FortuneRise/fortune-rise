import {Component, OnInit} from '@angular/core';
import {Wallet} from './models/wallet';
import {Transaction} from './models/transaction';
import {UsersService} from '../users/services/users.service';
import {ActivatedRoute, Router} from '@angular/router';
import {WalletService} from './services/wallet.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-wallets',
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './wallets.component.html',
  styleUrl: './wallets.component.css'
})
export class WalletsComponent implements OnInit{
  wallet!: Wallet;
  transaction: Transaction = new Transaction()
  userId: number  = +(sessionStorage.getItem("username")?? 0);

  constructor(private walletService: WalletService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getWallet()
  }

  getWallet() {
    this.walletService.getWallet(this.userId).subscribe(wallet => this.wallet = wallet);
  }

  submitTransaction(){
    this.transaction.walletId = 0;
    this.transaction.id = 0;
    this.transaction.userId = 0;
    this.walletService.updateWallet(this.userId, this.transaction).subscribe(wallet => this.wallet = wallet);
    //this.getWallet();
  }


}
