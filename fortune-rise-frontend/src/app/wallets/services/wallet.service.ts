import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Transaction} from '../models/transaction';
import {catchError, Observable, tap} from 'rxjs';
import {Wallet} from '../models/wallet';

@Injectable({
  providedIn: 'root',
})
export class WalletService{
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://wallet.default.svc.cluster.local:80/api/wallets';

  constructor(private http: HttpClient) {
  }

  getWallet(userId:number):Observable<Wallet>{
    const newurl = `${this.url}/${userId}`;
    return this.http.get<Wallet>(newurl).pipe(catchError(this.handleError));
  }

  updateWallet(userId:number, transaction:Transaction){
    const newurl = `${this.url}/${userId}`;
    const transactionPayload = { ...transaction };
    //console.log(transactionPayload);
    //console.log(newurl);
    return this.http.put<Wallet>(newurl,transactionPayload,{headers: this.headers}).pipe(
      tap(() => console.log('PUT request made')),
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }
}
