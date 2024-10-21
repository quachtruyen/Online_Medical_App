import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../core/auth/account.service';

@Component({
  selector: 'jhi-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.scss'],
})
export class ConversationComponent implements OnInit {
  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    console.log('alo');
  }
}
