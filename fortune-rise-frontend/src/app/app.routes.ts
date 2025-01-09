import { Routes } from '@angular/router';
import {UsersComponent} from './users/users.component';
import {HomeComponent} from './home/home.component';
import {AdduserComponent} from './users/adduser.component';
import {UserComponent} from './users/user.component';
import {WalletsComponent} from './wallets/wallets.component';
import {GamesComponent} from './games/games.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: "users", component: UsersComponent},
  {path: "users/add", component: AdduserComponent},
  {path: "users/:id", component: UserComponent},
  {path: "wallet/:userId", component: WalletsComponent},
  {path: "game/:userId", component: GamesComponent}
];

