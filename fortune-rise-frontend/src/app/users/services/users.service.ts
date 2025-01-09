import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders}  from '@angular/common/http';
import {catchError, Observable} from 'rxjs';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UsersService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.url).pipe(catchError(this.handleError))
  }

  createUser(user: User):Observable<User>{
    //console.log("function was called");
    return this.http.post<User>(this.url,JSON.stringify(user), {headers: this.headers}).pipe(catchError(this.handleError));
  }

  getUser(id: number):Observable<User>{
    const newurl = `${this.url}/${id}`;
    return  this.http.get<User>(newurl).pipe(catchError(this.handleError));
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }




}
