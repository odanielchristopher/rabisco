import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { MissionsService } from '../services/missions.service';

export class TextCreatedDailyMissionListener extends BaseListener {
  constructor(private readonly missions: MissionsService) {
    super(TextCreatedDailyMissionListener.name);
  }

  @OnEvent('text.created')
  handle(event: TextCreatedEvent) {
    this.logger.log(event, '✅ evento chegou');
  }
}
