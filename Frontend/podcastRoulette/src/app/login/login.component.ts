import { Component } from '@angular/core';
import { SpotifyService } from '../spotify.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private spotService: SpotifyService) {}

  loginPage = () => {
    this.spotService.getSpotifyLogin().subscribe(
      (response) => {
        window.location.replace(response);
      }
    );
  };
}
