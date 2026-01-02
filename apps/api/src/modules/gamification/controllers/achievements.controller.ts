import { Controller, Get } from '@nestjs/common';

import { ActiveUserId } from '@shared/decorators/active-user-id.decorator';

import { AchievementsService } from '../services/achievements.service';

@Controller('achievements')
export class AchievementsController {
  constructor(private readonly achievementsService: AchievementsService) {}

  @Get()
  async list(@ActiveUserId() userId: string) {
    return this.achievementsService.listUserAchievements(userId);
  }
}
