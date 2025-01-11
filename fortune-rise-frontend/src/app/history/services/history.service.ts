import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, map, Observable} from 'rxjs';
import {Game} from '../../games/models/game';
import {Transaction} from '../../wallets/models/transaction';
import {Notification} from '../../notifications/models/notification';

@Injectable({
  providedIn: "root"
})
export class HistoryService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8085/api/history';

  constructor(private http: HttpClient) {
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }

  getGames(userId: number,limit:number,offset:number): Observable<{totalCount:number, games: Game[]}>{
    const newurl = `${this.url}/games/${userId}`;
    const params = new HttpParams().set('limit', limit.toString()).set('offset', offset.toString());
    return this.http.get<Game[]>(newurl, { params, observe: 'response' })
      .pipe(
        map(response => ({
          totalCount: +(response.headers.get('X-Total-Count') ?? '0'),
          games: response.body || [],
        }))
      );
  }

  getTransactions(userId: number,limit:number,offset:number): Observable<{totalCount:number, transactions: Transaction[]}>{
    const newurl = `${this.url}/transactions/${userId}`;
    const params = new HttpParams().set('limit', limit.toString()).set('offset', offset.toString());
    return this.http.get<Game[]>(newurl, { params, observe: 'response' })
      .pipe(
        map(response => ({
          totalCount: +(response.headers.get('X-Total-Count') ?? '0'),
          transactions: response.body || [],
        }))
      );
  }
}
