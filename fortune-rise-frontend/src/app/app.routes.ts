import { Routes } from '@angular/router';
import {UsersComponent} from './users/users.component';
import {HomeComponent} from './home/home.component';
import {UserComponent} from './users/user.component';
import {WalletsComponent} from './wallets/wallets.component';
import {GamesComponent} from './games/games.component';
import {HistoryComponent} from './history/history.component';
import {GameHistoryComponent} from './history/gamehistory.component';
import {TransactionhistoryComponent} from './history/transactionhistory.component';
import {NotificationsComponent} from './notifications/notifications.component';
import {LoginComponent} from './login/login.component';
import {PromotionsComponent} from './promotions/promotions.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: "users", component: UsersComponent},
  {path: "user", component: UserComponent},
  {path: "wallet", component: WalletsComponent},
  {path: "game", component: GamesComponent},
  {path: "history", component: HistoryComponent},
  {path: "history/games", component: GameHistoryComponent},
  {path: "history/games/:gameId", component: GameHistoryComponent},
  {path: "history/transactions", component: TransactionhistoryComponent},
  {path: "notifications", component: NotificationsComponent},
  {path: "promotions", component: PromotionsComponent}
];

