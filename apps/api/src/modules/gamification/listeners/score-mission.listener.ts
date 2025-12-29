import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { MissionCompletedPayload } from '@modules/events/payloads/mission-completed.payload';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { ScoreService } from '../services/score.service';

@Injectable()
export class MissionCompletedScoreListener extends BaseListener {
  constructor(private readonly scoreService: ScoreService) {
    super(MissionCompletedScoreListener.name);
  }

  @OnEvent('mission.completed')
  async handle(event: MissionCompletedPayload) {
    await this.scoreService.addPoints(event.userId, event.mission.points);

    this.logger.log(`User ${event.userId} has his points incremented.`);
  }
}
