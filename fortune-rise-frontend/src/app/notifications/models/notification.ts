export class Notification {
  id!: number;
  read?: boolean;
  content?: string;
  date?: Date;

  constructor(data?: Partial<Notification>) {
    if (data) {
      this.id = data.id!;
      this.read = data.read;
      this.content = data.content;
      this.date = new Date(data.date!);
    }
  }
}
