import {Bet} from './bet';

export class Game {
  gameId?: number;
  payout?: number;
  roll?: number;
  date?: Date;
  userId?: number;
  bets?: Bet[]
}
