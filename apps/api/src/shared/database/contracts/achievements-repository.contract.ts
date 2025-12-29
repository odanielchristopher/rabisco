import { Achievement, UserAchievement } from '@prisma/client';

import { AchievementType } from '@shared/constants/achievements.contants';

export abstract class IAchievementsRepository {
  abstract count(input: IAchievementsRepository.CountInput): Promise<number>;

  abstract findManyAchievements(
    input?: IAchievementsRepository.FindManyAchievementsInput,
  ): Promise<Achievement[]>;

  abstract findManyUserAchievements(
    input: IAchievementsRepository.FindManyUserAchievementsInput,
  ): Promise<UserAchievement[]>;

  abstract createManyUserAchievements(
    input: IAchievementsRepository.CreateManyInput,
  ): Promise<void>;
}

export namespace IAchievementsRepository {
  type WhereInput = { type?: AchievementType; userId?: string };

  type SelectInput = { achievementId?: boolean };

  export type CountInput = { where: WhereInput };

  export type FindManyAchievementsInput = {
    where?: WhereInput;
  };

  export type FindManyUserAchievementsInput = {
    where: WhereInput;
    select?: SelectInput;
  };

  export type CreateManyInput = {
    data: {
      userId: string;
      achievementId: string;
    }[];
    skipDuplicates: boolean;
  };
}
