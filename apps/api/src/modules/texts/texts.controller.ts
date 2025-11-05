import {
  Body,
  Controller,
  Delete,
  Get,
  HttpCode,
  HttpStatus,
  Inject,
  Param,
  Post,
  Put,
} from '@nestjs/common';

import { ActiveUserId } from '@shared/decorators/active-user-id.decorator';

import { ITextsService } from './contracts/text-service.contract';
import { CreateTextDto } from './dto/create-text.dto';
import { UpdateTextDto } from './dto/update-text.dto';

@Controller('texts')
export class TextsController {
  constructor(
    @Inject(ITextsService) private readonly textsService: ITextsService,
  ) {}

  @Get()
  findAll(@ActiveUserId() userId: string) {
    return this.textsService.findAll({ userId });
  }

  @Get(':textId')
  findOne(@ActiveUserId() userId: string, @Param('textId') textId: string) {
    return this.textsService.findOne({ userId, textId });
  }

  @Post()
  create(@ActiveUserId() userId: string, @Body() createTextDto: CreateTextDto) {
    return this.textsService.create({ userId, createTextDto });
  }

  @Put(':textId')
  update(
    @ActiveUserId() userId: string,
    @Param('textId') textId: string,
    @Body() updateTextDto: UpdateTextDto,
  ) {
    return this.textsService.update({ userId, textId, updateTextDto });
  }

  @HttpCode(HttpStatus.NO_CONTENT)
  @Delete(':textId')
  remove(@ActiveUserId() userId: string, @Param('textId') textId: string) {
    return this.textsService.delete({ userId, textId });
  }
}
