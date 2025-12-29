import { Injectable } from '@nestjs/common';
import { Cron } from '@nestjs/schedule';

import { MISSIONS } from '@shared/constants/missions.constants';
import { IMissionsRepository } from '@shared/database/contracts/missions-repository.contract';

@Injectable()
export class DailyMissionsCron {
  constructor(private readonly missionsRepository: IMissionsRepository) {}

  @Cron('0 0 * * *') // todo dia à meia-noite
  async handle() {
    await this.missionsRepository.createManyDailyMissions({
      missions: MISSIONS.map((mission) => ({
        ...mission,
      })),
    });
  }
}
