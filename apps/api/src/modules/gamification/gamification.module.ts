import { Module } from '@nestjs/common';

import { TextCreatedAchievementListener } from './listeners/achievement-text.listener';
import { TextCreatedDailyMissionListener } from './listeners/missions-text.listener';
import { TextCreatedScoreListener } from './listeners/score-text.listener';
import { TextCreatedStreakListener } from './listeners/streak-text.listener';
import { AchievementsService } from './services/achievements.service';
import { MissionsService } from './services/missions.service';
import { ScoreService } from './services/score.service';
import { StreakService } from './services/streak.service';

@Module({
  providers: [
    // Services
    AchievementsService,
    StreakService,
    MissionsService,
    ScoreService,

    // Listeners
    TextCreatedAchievementListener,
    TextCreatedStreakListener,
    TextCreatedDailyMissionListener,
    TextCreatedScoreListener,
  ],
})
export class GamificationModule {}
