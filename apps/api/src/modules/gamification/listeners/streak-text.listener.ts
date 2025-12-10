import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { StreakService } from '../services/streak.service';

export class TextCreatedStreakListener extends BaseListener {
  constructor(private readonly streaks: StreakService) {
    super(TextCreatedStreakListener.name);
  }

  @OnEvent('text.created')
  handle(event: TextCreatedEvent) {
    this.logger.log(event, '✅ evento chegou');
  }
}
