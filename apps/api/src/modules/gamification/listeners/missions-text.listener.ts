import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { MissionsService } from '../services/missions.service';

@Injectable()
export class TextCreatedDailyMissionListener extends BaseListener {
  constructor(private readonly missionsService: MissionsService) {
    super(TextCreatedDailyMissionListener.name);
  }

  @OnEvent('text.created')
  async handle(event: TextCreatedEvent) {
    await this.missionsService.evaluateTextCreated(event);

    this.logger.log(`User ${event.userId} has mission updated.`);
  }
}
