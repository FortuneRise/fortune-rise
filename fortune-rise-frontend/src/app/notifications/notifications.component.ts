import {Component, OnInit} from '@angular/core';
import {Notification} from './models/notification';
import {UsersService} from '../users/services/users.service';
import {NotificationService} from './services/notification.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CommonModule, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-notifications',
  imports: [
    NgForOf,
    NgIf,
    CommonModule
  ],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements OnInit{
  notifications: Notification[] = [];
  userId: number = +(sessionStorage.getItem("username")?? 0);
  currentPage:number = 1;
  limit:number = 10;
  offset:number = 0;
  totalNotifications:number = 0;

  constructor(private notificationService: NotificationService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getNotifications();
  }

  getNotifications(): void{
    this.notificationService.getNotifications(this.userId, this.limit, this.offset).subscribe(result => {this.notifications = result.notifications; this.totalNotifications = result.totalCount;});
  }

  changePage(pageNumber: number){
    this.currentPage = pageNumber;
    this.offset = (pageNumber - 1) * this.limit
    //console.log(this.totalNotifications);
    this.getNotifications();
  }

  readNotification(notificationId:number){
    this.notificationService.readNotification(this.userId,notificationId);
  }

  protected readonly Math = Math;
}
