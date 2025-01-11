import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Game} from '../models/game';
import {catchError, Observable} from 'rxjs';
import {Bet} from '../models/bet';

@Injectable({
  providedIn: 'root',
})
export class GamesService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://game.default.svc.cluster.local/api/games';

  constructor(private http: HttpClient) {
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }

  createGame(userId: number, bets:Bet[]):Observable<Game>{
    const newurl = `${this.url}/${userId}`;
    return this.http.post<Game>(newurl,bets, {headers: this.headers}).pipe(catchError(this.handleError))
  }
}
