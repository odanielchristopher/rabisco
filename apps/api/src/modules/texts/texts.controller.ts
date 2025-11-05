import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Put,
} from '@nestjs/common';

import { CreateTextDto } from './dto/create-text.dto';
import { UpdateTextDto } from './dto/update-text.dto';
import { TextsService } from './texts.service';

@Controller('texts')
export class TextsController {
  constructor(private readonly textsService: TextsService) {}

  @Get()
  findAll() {
    return this.textsService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.textsService.findOne(+id);
  }

  @Post()
  create(@Body() createTextDto: CreateTextDto) {
    return this.textsService.create(createTextDto);
  }

  @Put(':id')
  update(@Param('id') id: string, @Body() updateTextDto: UpdateTextDto) {
    return this.textsService.update(+id, updateTextDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.textsService.remove(+id);
  }
}
