import { Injectable } from '@nestjs/common';

import { IAchievementsRepository } from '../contracts/achievements-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class AchievementsRepository implements IAchievementsRepository {
  constructor(private readonly prismaService: PrismaService) {}

  count(input: IAchievementsRepository.CountInput) {
    return this.prismaService.achievement.count(input);
  }

  findManyAchievements(
    input: IAchievementsRepository.FindManyAchievementsInput = {},
  ) {
    return this.prismaService.achievement.findMany({
      where: input.where,
    });
  }

  findManyUserAchievements(
    input: IAchievementsRepository.FindManyUserAchievementsInput,
  ) {
    return this.prismaService.userAchievement.findMany(input);
  }

  async createManyUserAchievements({
    data,
    skipDuplicates,
  }: IAchievementsRepository.CreateManyInput) {
    await this.prismaService.userAchievement.createMany({
      data,
      skipDuplicates,
    });
  }
}
