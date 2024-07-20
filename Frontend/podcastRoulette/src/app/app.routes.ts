import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PodcastComponent } from './podcast/podcast.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    redirectTo:  'login',
    pathMatch: 'full'
  },
  {
    path: 'podcast',
    component: PodcastComponent
  }
];
export default routes;
