import { Controller, Get } from '@nestjs/common';

import { ActiveUserId } from '@shared/decorators/active-user-id.decorator';

import { MissionsService } from '../services/missions.service';

@Controller('missions')
export class MissionsController {
  constructor(private readonly missionsService: MissionsService) {}

  @Get('daily')
  async listDaily(@ActiveUserId() userId: string) {
    await this.missionsService.ensureUserDailyMissions(userId);
    return this.missionsService.listUserDailyMissions(userId);
  }
}
