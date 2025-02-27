import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:9000/api/users';

  constructor(private http: HttpClient) {}

 
  loadExperts(): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  
    return this.http.get<any>(`${this.apiUrl}/role/ROLE_EXPERTO`, { headers });
  }
  getUserById(id: string): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  
    return this.http.get<any>(`http://localhost:9000/api/users/${id}`, { headers });
  }
  
  updateUser(user: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  
    return this.http.put<any>(`http://localhost:9000/api/users/${user.idUser}`, user, { headers });
  }
  
  
  
}
