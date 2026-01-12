import { HttpModule } from '@nestjs/axios';
import { Module } from '@nestjs/common';

import { DatabaseModule } from '@shared/database/database.module';

import { DailyPromptCron } from './cronjobs/daily-prompt.cron';
import { PromptsController } from './prompts.controller';
import { PromptsService } from './prompts.service';

@Module({
  imports: [DatabaseModule, HttpModule],
  controllers: [PromptsController],
  providers: [PromptsService, DailyPromptCron],
})
export class PromptsModule {}
