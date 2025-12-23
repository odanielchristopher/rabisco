import { Injectable } from '@nestjs/common';

import { IScoreRepository } from '@shared/database/contracts/score-repository.contract';

@Injectable()
@Injectable()
export class ScoreService {
  constructor(private readonly scoreRepository: IScoreRepository) {}

  async addPoints(userId: string, points: number) {
    await this.scoreRepository.addPoints({
      userId,
      quantity: points,
    });
  }
}
