import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { StreakService } from '../services/streak.service';

@Injectable()
export class TextCreatedStreakListener extends BaseListener {
  constructor(private readonly streaksService: StreakService) {
    super(TextCreatedStreakListener.name);
  }

  @OnEvent('text.created')
  async handle(event: TextCreatedEvent) {
    await this.streaksService.incrementDailyStreak(event.userId);

    this.logger.log(
      `User ${event.userId} has his streak increment in one Day.`,
    );
  }
}
