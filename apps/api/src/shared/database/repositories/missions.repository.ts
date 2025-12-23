/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { Injectable } from '@nestjs/common';

import { IMissionsRepository } from '../contracts/missions-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class MissionsRepository implements IMissionsRepository {
  constructor(private readonly prismaService: PrismaService) {}

  findUserMissions(
    findUserMissionsDto: IMissionsRepository.FindUserMissionsDto,
  ) {
    const { userId } = findUserMissionsDto;

    return this.prismaService.userMission.findMany({
      where: {
        userId,
      },
      select: {
        missionId: true,
        userId: true,
        progress: true,
        completed: true,
        completionDate: true,
        mission: true,
      },
    });
  }

  countUserMissions(
    countUserMissionsDto: IMissionsRepository.CountUserMissionsDto,
  ) {
    const { userId, completed = undefined } = countUserMissionsDto;

    return this.prismaService.userMission.count({
      where: { completed, userId },
    });
  }

  async incrementProgress(
    incrementProgressDto: IMissionsRepository.IncrementProgressDto,
  ) {
    const { missionId, userId, value } = incrementProgressDto;

    return this.prismaService.$transaction(async (manager) => {
      const userMission = await manager.userMission.findUnique({
        where: { userId_missionId: { userId, missionId } },
        include: { mission: true },
      });

      if (!userMission) {
        return null;
      }

      if (userMission.completed) {
        return userMission;
      }

      const newProgress = userMission.progress + value;

      const completed = newProgress >= userMission.mission.goal;

      return manager.userMission.update({
        where: { userId_missionId: { userId, missionId } },
        data: {
          progress: newProgress,
          completed,
          completionDate: completed ? new Date() : null,
        },
        include: { mission: true },
      });
    });
  }
}
