import { Controller, Get, Param } from '@nestjs/common';

import { PromptsService } from './prompts.service';

@Controller('prompts')
export class PromptsController {
  constructor(private readonly promptsService: PromptsService) {}

  @Get('daily')
  getToday() {
    return this.promptsService.getTodayPrompt();
  }

  @Get(':id')
  getById(@Param('id') id: string) {
    return this.promptsService.findById(id);
  }
}
