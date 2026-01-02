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

  async listUserAchievements(userId: string) {
    const [
      achievements,
      userAchievements,
      textCount,
      streak,
      completedMissions,
    ] = await Promise.all([
      this.achievementsRepository.findManyAchievements(),
      this.achievementsRepository.findManyUserAchievements({
        where: { userId },
      }),
      this.textsRepository.count({ userId }),
      this.streakRepository.findByUserId({ userId }),
      this.missionsRepository.countUserMissions({
        userId,
        completed: true,
      }),
    ]);

    const achievedMap = new Set(userAchievements.map((ua) => ua.achievementId));

    return achievements.map((achievement) => {
      let progress = 0;

      switch (AchievementType[achievement.type]) {
        case AchievementType.TEXT_QUANTITY:
          progress = textCount;
          break;

        case AchievementType.DAY_SEQUENCE:
          progress = streak?.daySequence ?? 0;
          break;

        case AchievementType.MISSION:
          progress = completedMissions;
          break;
      }

      return {
        id: achievement.id,
        code: achievement.code,
        title: achievement.title,
        description: achievement.description,
        goal: achievement.goal,
        progress,
        achieved: achievedMap.has(achievement.id),
      };
    });
  }

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
