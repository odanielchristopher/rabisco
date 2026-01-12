import { Injectable, Logger } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';

import { MISSIONS } from '@shared/constants/missions.constants';
import { IMissionsRepository } from '@shared/database/contracts/missions-repository.contract';

@Injectable()
export class DailyMissionsCron {
  private readonly logger = new Logger(DailyMissionsCron.name);

  constructor(private readonly missionsRepository: IMissionsRepository) {}

  @Cron(CronExpression.EVERY_DAY_AT_MIDNIGHT) // todo dia à meia-noite
  async handle() {
    this.logger.log('Executando cron de geração de missões diárias');
    const today = new Date();

    await this.missionsRepository.createManyDailyMissions({
      missions: MISSIONS.map((mission) => ({
        ...mission,
        availableDate: today,
      })),
    });
  }
}
