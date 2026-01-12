import { DailyMission, UserMission } from '@prisma/client';

import { MISSIONS } from '@shared/constants/missions.constants';

export type UserMissionWithDailyMission = UserMission & {
  mission: DailyMission;
};

export abstract class IMissionsRepository {
  abstract countUserMissions(
    countUserMissionsDto: IMissionsRepository.CountUserMissionsDto,
  ): Promise<number>;

  abstract findDailyMissions(
    findDailyMisionsDto: IMissionsRepository.FindDailyMissionsDto,
  ): Promise<DailyMission[]>;

  abstract findUserMissions(
    findUserMissionsDto: IMissionsRepository.FindUserMissionsDto,
  ): Promise<UserMissionWithDailyMission[]>;

  abstract incrementProgress(
    incrementProgressDto: IMissionsRepository.IncrementProgressDto,
  ): Promise<UserMissionWithDailyMission | null>;

  abstract createManyDailyMissions(
    createMissionsDto: IMissionsRepository.CreateDailyMissions,
  ): Promise<void>;

  abstract upsertUserMission(
    upsertUserMissionDto: IMissionsRepository.UpsertUserMission,
  ): Promise<void>;
}

export namespace IMissionsRepository {
  export type CountUserMissionsDto = {
    userId: string;
    completed?: boolean;
  };

  export type FindUserMissionsDto = {
    userId: string;
  };

  export type FindDailyMissionsDto = {
    availableDate?: Date;
  };

  export type IncrementProgressDto = {
    userId: string;
    missionId: string;
    value: number;
  };

  export type CreateDailyMissions = {
    missions: DailyMission[];
  };

  export type UpsertUserMission = {
    userId: string;
    missionId: string;
  };

  type DailyMission = (typeof MISSIONS)[number] & { availableDate: Date };
}
