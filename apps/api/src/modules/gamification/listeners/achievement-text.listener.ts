import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { AchievementsService } from '../services/achievements.service';

@Injectable()
export class TextCreatedAchievementListener extends BaseListener {
  constructor(private readonly achievements: AchievementsService) {
    super(TextCreatedAchievementListener.name);
  }

  @OnEvent('text.created')
  async handle(event: TextCreatedEvent) {
    await this.achievements.evaluateTextCreation(event.userId);

    this.logger.log(`User ${event.userId} has achievements avaluated.`);
  }
}
