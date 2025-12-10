import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { AchievementsService } from '../services/achievements.service';

export class TextCreatedAchievementListener extends BaseListener {
  constructor(private readonly achievements: AchievementsService) {
    super(TextCreatedAchievementListener.name);
  }

  @OnEvent('text.created')
  handle(event: TextCreatedEvent) {
    this.logger.log(event, '✅ evento chegou');
  }
}
