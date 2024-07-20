import { Component, OnInit } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-podcast',
  standalone: true,
  imports: [],
  templateUrl: './podcast.component.html',
  styleUrl: './podcast.component.css'
})
export class PodcastComponent implements OnInit{
  constructor(private spotService: SpotifyService, private router: Router){}

  shows: any[] = [];
  showsLength = 0;
  randIndex = 0;
  ngOnInit(): void {
    this.spotService.getPodcast().subscribe((show) => {
      show.forEach((a: any) => {
        this.shows.push(a);
      });
      this.showsLength = show.length;
      this.randIndex = Math.floor(Math.random() * show.length);
    });
  }

}
