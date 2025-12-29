import { Injectable } from '@nestjs/common';
import { OnEvent } from '@nestjs/event-emitter';

import { StreakGoalPaylod } from '@modules/events/payloads/streak-goal.payload';
import { BaseListener } from '@shared/contracts/base-listener.contract';

import { AchievementsService } from '../services/achievements.service';

@Injectable()
export class StreakGoalAchievementListener extends BaseListener {
  constructor(private readonly achievements: AchievementsService) {
    super(StreakGoalAchievementListener.name);
  }

  @OnEvent('streak.add')
  async handle(event: StreakGoalPaylod) {
    await this.achievements.evaluateStreak(event.userId, event.streak);

    this.logger.log(`User ${event.userId} has achievements avaluated.`);
  }
}
