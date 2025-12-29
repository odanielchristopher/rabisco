import { Injectable } from '@nestjs/common';

import { CustomEventEmitter } from '@modules/events/contract/custom-event-emitter.contract';
import { StreakGoalPaylod } from '@modules/events/payloads/streak-goal.payload';
import { IStreakRepository } from '@shared/database/contracts/streak-repository.contract';
import { differenceInDays } from '@shared/utils/shared.utils';

@Injectable()
export class StreakService {
  constructor(
    private readonly eventEmitter: CustomEventEmitter,
    private readonly streakRepository: IStreakRepository,
  ) {}

  async incrementDailyStreak(userId: string) {
    const today = new Date();

    const streak = await this.streakRepository.findByUserId({ userId });

    // Primeiro streak
    if (!streak) {
      console.log('Executou aqui 1', streak);
      const created = await this.streakRepository.create({
        userId,
        daySequence: 1,
        lastDate: today,
      });

      this.emitStreakEvent(userId, created.daySequence);
      return;
    }

    const diffDays = differenceInDays(today, streak.lastDate);

    // Já contou hoje
    if (diffDays === 0) {
      console.log('Executou aqui 2');
      return;
    }

    // Quebrou sequência
    if (diffDays > 1) {
      const reseted = await this.streakRepository.update({
        userId,
        daySequence: 1,
        lastDate: today,
      });

      console.log('Executou aqui 3');

      this.emitStreakEvent(userId, reseted.daySequence);
      return;
    }

    // Sequência continua
    const updated = await this.streakRepository.increment({
      userId,
      lastDate: today,
    });
    console.log('Executou aqui 4');

    this.emitStreakEvent(userId, updated.daySequence);
  }

  private emitStreakEvent(userId: string, daySequence: number) {
    console.log('Emitiu o evento: "streak.add"');
    this.eventEmitter.emit(
      'streak.add',
      new StreakGoalPaylod(userId, daySequence),
    );
  }
}
