import { Injectable } from '@nestjs/common';

import { IStreakRepository } from '../contracts/streak-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class StreakRepository implements IStreakRepository {
  constructor(private readonly prismaService: PrismaService) {}

  findByUserId({ userId }: IStreakRepository.FindByUserIdDto) {
    return this.prismaService.streak.findUnique({
      where: { userId },
    });
  }

  create({ userId, daySequence, lastDate }: IStreakRepository.CreateDto) {
    return this.prismaService.streak.create({
      data: {
        userId,
        daySequence,
        lastDate,
      },
    });
  }

  update({ userId, daySequence, lastDate }: IStreakRepository.UpdateDto) {
    return this.prismaService.streak.update({
      where: { userId },
      data: {
        daySequence,
        lastDate,
      },
    });
  }

  increment({ userId, lastDate }: IStreakRepository.IncrementDto) {
    return this.prismaService.streak.update({
      where: { userId },
      data: {
        daySequence: { increment: 1 },
        lastDate,
      },
    });
  }
}
