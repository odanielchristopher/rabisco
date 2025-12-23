import { Injectable } from '@nestjs/common';
import { Achievement } from '@prisma/client';

import { AchievementType } from '@shared/constants/achievements.contants';
import { IAchievementsRepository } from '@shared/database/contracts/achievements-repository.contract';
import { IMissionsRepository } from '@shared/database/contracts/missions-repository.contract';
import { IStreakRepository } from '@shared/database/contracts/streak-repository.contract';
import { ITextsRepository } from '@shared/database/contracts/texts-repository.contract';

@Injectable()
export class AchievementsService {
  constructor(
    private readonly achievementsRepository: IAchievementsRepository,
    private readonly streakRepository: IStreakRepository,
    private readonly textsRepository: ITextsRepository,
    private readonly missionsRepository: IMissionsRepository,
  ) {}

  async evaluateTextCreation(userId: string) {
    const totalTexts = await this.textsRepository.count({ userId });

    const achievements = await this.achievementsRepository.findManyAchievements(
      {
        where: { type: AchievementType.TEXT_QUANTITY },
      },
    );

    await this.grantAchievements(userId, achievements, totalTexts);
  }

  async evaluateStreak(userId: string, streak: number) {
    const achievements = await this.achievementsRepository.findManyAchievements(
      {
        where: { type: AchievementType.DAY_SEQUENCE },
      },
    );

    await this.grantAchievements(userId, achievements, streak);
  }

  async evaluateMissionCompletion(userId: string) {
    const totalCompleted = await this.missionsRepository.countUserMissions({
      userId,
      completed: true,
    });

    const achievements = await this.achievementsRepository.findManyAchievements(
      {
        where: { type: AchievementType.MISSION },
      },
    );

    await this.grantAchievements(userId, achievements, totalCompleted);
  }

  private async grantAchievements(
    userId: string,
    achievementsToCheck: Achievement[],
    currentValue: number,
  ) {
    const userAchievements =
      await this.achievementsRepository.findManyUserAchievements({
        where: { userId },
        select: { achievementId: true },
      });

    const alreadyHas = new Set(userAchievements.map((ua) => ua.achievementId));

    const toGrant = achievementsToCheck.filter(
      (achievement) =>
        currentValue >= achievement.goal && !alreadyHas.has(achievement.id),
    );

    if (toGrant.length === 0) {
      return;
    }

    await this.achievementsRepository.createManyUserAchievements({
      data: toGrant.map((achievement) => ({
        userId,
        achievementId: achievement.id,
      })),
      skipDuplicates: true,
    });
  }
}
