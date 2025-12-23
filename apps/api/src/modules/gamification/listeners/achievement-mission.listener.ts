import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { MissionCompletedPayload } from '@modules/events/payloads/mission-completed.payload';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { AchievementsService } from '../services/achievements.service';

@Injectable()
export class MissionCompletedAchievementListener extends BaseListener {
  constructor(private readonly achievements: AchievementsService) {
    super(MissionCompletedAchievementListener.name);
  }

  @OnEvent('mission.completed')
  async handle(event: MissionCompletedPayload) {
    await this.achievements.evaluateMissionCompletion(event.userId);

    this.logger.log(`User ${event.userId} has achievements avaluated.`);
  }
}
