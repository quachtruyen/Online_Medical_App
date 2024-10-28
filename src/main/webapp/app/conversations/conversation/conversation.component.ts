import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../core/auth/account.service';

@Component({
  selector: 'jhi-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.scss'],
})
export class ConversationComponent implements OnInit {
  scrollHeightOlds: Map<HTMLTextAreaElement, number> = new Map();

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    console.log('alo');
  }

  autoResize(event: Event): void {
    const messageInput = event.target as HTMLTextAreaElement;

    const oldHeight = this.scrollHeightOlds.get(messageInput) ?? 0;
    console.log('scroll height old: ' + oldHeight.toString());
    console.log('giá trị scrollHeight hiện tại: ' + messageInput.scrollHeight.toString());

    if (messageInput.scrollHeight > oldHeight) {
      messageInput.style.height = 'auto';
      messageInput.style.height = messageInput.scrollHeight.toString() + 'px';
    }

    // Lưu lại chiều cao hiện tại cho textarea này
    this.scrollHeightOlds.set(messageInput, messageInput.scrollHeight);
  }
}
