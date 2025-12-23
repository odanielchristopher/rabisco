import { Streak } from '@prisma/client';

export abstract class IStreakRepository {
  abstract findByUserId(
    findStreakDto: IStreakRepository.FindByUserIdDto,
  ): Promise<Streak | null>;

  abstract create(createDto: IStreakRepository.CreateDto): Promise<Streak>;

  abstract update(updateDto: IStreakRepository.UpdateDto): Promise<Streak>;

  abstract increment(
    incrementDailyDto: IStreakRepository.IncrementDto,
  ): Promise<Streak>;
}

export namespace IStreakRepository {
  export type FindByUserIdDto = {
    userId: string;
  };

  export type CreateDto = {
    userId: string;
    daySequence: number;
    lastDate: Date;
  };

  export type UpdateDto = {
    userId: string;
    daySequence: number;
    lastDate: Date;
  };

  export type IncrementDto = {
    userId: string;
    lastDate: Date;
  };
}
