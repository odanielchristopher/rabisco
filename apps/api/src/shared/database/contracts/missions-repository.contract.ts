import { DailyMission, UserMission } from '@prisma/client';

type UserMissionWithDailyMission = UserMission & { mission: DailyMission };

export abstract class IMissionsRepository {
  abstract countUserMissions(
    countUserMissionsDto: IMissionsRepository.CountUserMissionsDto,
  ): Promise<number>;

  abstract findUserMissions(
    findUserMissionsDto: IMissionsRepository.FindUserMissionsDto,
  ): Promise<UserMissionWithDailyMission[]>;

  abstract incrementProgress(
    incrementProgressDto: IMissionsRepository.IncrementProgressDto,
  ): Promise<UserMissionWithDailyMission | null>;
}

export namespace IMissionsRepository {
  export type CountUserMissionsDto = {
    userId: string;
    completed?: boolean;
  };

  export type FindUserMissionsDto = {
    userId: string;
  };

  export type IncrementProgressDto = {
    userId: string;
    missionId: string;
    value: number;
  };
}
