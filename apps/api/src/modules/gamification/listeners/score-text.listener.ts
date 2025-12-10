import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { ScoreService } from '../services/score.service';

export class TextCreatedScoreListener extends BaseListener {
  constructor(private readonly score: ScoreService) {
    super(TextCreatedScoreListener.name);
  }

  @OnEvent('text.created')
  handle(event: TextCreatedEvent) {
    this.logger.log(event, '✅ evento chegou');
  }
}
