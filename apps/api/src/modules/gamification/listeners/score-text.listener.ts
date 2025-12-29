import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { pointsTo } from '@shared/constants/points.constants';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { ScoreService } from '../services/score.service';

@Injectable()
export class TextCreatedScoreListener extends BaseListener {
  constructor(private readonly scoreService: ScoreService) {
    super(TextCreatedScoreListener.name);
  }

  @OnEvent('text.created')
  async handle(event: TextCreatedEvent) {
    await this.scoreService.addPoints(event.userId, pointsTo.CREATE_TEXT());

    this.logger.log(`User ${event.userId} has his points incremented.`);
  }
}
