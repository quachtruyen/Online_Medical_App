import { Component, OnInit } from '@angular/core';
import { AccountService } from '../core/auth/account.service';

@Component({
  selector: 'jhi-conversations',
  templateUrl: './conversations.component.html',
  styleUrls: ['./conversations.component.scss'],
})
export class ConversationsComponent implements OnInit {
  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    console.log('');
  }
}
