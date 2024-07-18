import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpotifyService {
  private url = 'http://localhost:8080/podcastRoulette';

  constructor(private httpClient: HttpClient) {}

  getSpotifyLogin(): Observable<any> {
    const headers = new HttpHeaders().set(
      'Content-Type',
      'text/plain; charset=utf-8'
    );
    const loginUrl = `${this.url}/login`;
    return this.httpClient.get(loginUrl, {headers, responseType: 'text'});
  }

}
