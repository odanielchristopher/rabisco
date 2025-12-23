import { Injectable } from '@nestjs/common';

import { IScoreRepository } from '../contracts/score-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class ScoreRepository implements IScoreRepository {
  constructor(private readonly prismaService: PrismaService) {}

  async addPoints({
    userId,
    quantity,
  }: IScoreRepository.AddPointsDto): Promise<void> {
    await this.prismaService.userScore.upsert({
      where: { userId },
      update: {
        points: {
          increment: quantity,
        },
      },
      create: {
        userId,
        points: quantity,
      },
    });
  }
}
