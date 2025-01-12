import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError} from 'rxjs';
import {Promotion} from '../models/promotion';
import {Transaction} from '../../wallets/models/transaction';

@Injectable({
  providedIn: 'root'
})
export class PromotionsService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://72.144.96.255/promotion-proxy/api/notifications';

  constructor(private http: HttpClient) {
  }

  getPromotionsByTrigger(userId:number, trigger:string){
    const newurl = `${this.url}/${userId}/${trigger}`;
    return this.http.get<Promotion[]>(newurl).pipe(catchError(this.handleError));
  }

  verifyWalletPromotion(userId:number,promotionId:number,transaction:Transaction){
    const newurl = `${this.url}/${userId}/${promotionId}`;
    return this.http.post<boolean>(newurl, JSON.stringify(transaction), {headers: this.headers}).pipe(catchError(this.handleError));
  }

  applyPromotion(userId:number, promotionId:number){
    const newurl = `${this.url}/${userId}/${promotionId}`;
    return this.http.put(newurl,null,{headers: this.headers}).pipe(catchError(this.handleError));
  }

  addPromotion(promotion:Promotion){
    return this.http.post<Promotion>(this.url,promotion,{headers: this.headers}).pipe(catchError(this.handleError));
  }

  linkUserToPromotion(userId:number,promotionId:number){
    const newurl = `${this.url}/${userId}/${promotionId}`;
    return this.http.post(newurl,null,{headers: this.headers}).pipe(catchError(this.handleError));

  }


  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }
}
