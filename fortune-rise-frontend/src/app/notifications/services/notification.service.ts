import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, map, Observable} from 'rxjs';
import {Notification} from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8083/api/notifications';

  constructor(private http: HttpClient) {
  }

  getNotifications(userId:number,limit:number,offset:number):Observable<{totalCount:number; notifications: Notification[]}>{
    const newurl = `${this.url}/${userId}`;
    const params = new HttpParams().set('limit', limit.toString()).set('offset', offset.toString());
    return this.http.get<Notification[]>(newurl, { params, observe: 'response' })
      .pipe(
        map(response => ({
          totalCount: +(response.headers.get('X-Total-Count') ?? '0'),
          notifications: response.body || [],
        }))
      );
  }

  readNotification(userId:number, notificationId:number):Observable<Notification>{
    const newurl = `${this.url}/${userId}/${notificationId}`;
    return this.http.put<Notification>(newurl,null,{headers: this.headers}).pipe(catchError(this.handleError));
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }
}
