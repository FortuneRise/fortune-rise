import {Bet} from './bet';

export class Game {
  gameId!: number;
  payout?: number;
  roll?: number;
  date?: Date;
  userId?: number;
  bets?: Bet[]

  constructor(data?: Partial<Game>) {
    if (data) {
      this.gameId = data.gameId!;
      this.roll = data.roll;
      this.payout = data.payout;
      this.date = new Date(data.date!);
      this.userId = data.userId;
      this.bets = data.bets;
    }
  }
}
