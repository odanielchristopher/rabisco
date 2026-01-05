import { Injectable, Logger } from '@nestjs/common';
import { Cron } from '@nestjs/schedule';

import { PromptsService } from '../prompts.service';

@Injectable()
export class DailyPromptCron {
  private readonly logger = new Logger(DailyPromptCron.name);

  constructor(private readonly promptsService: PromptsService) {}

  @Cron('0 0 * * *')
  async handle() {
    this.logger.log('Executando cron de geração de prompt diário');
    await this.promptsService.getTodayPrompt();
  }
}
