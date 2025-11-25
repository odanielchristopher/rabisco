import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Inject,
} from '@nestjs/common';

import { ActiveUserId } from '@shared/decorators/active-user-id.decorator';

import { ITagsService } from './contracts/tags-service.contract';
import { CreateTagDto } from './dto/create-tag.dto';

@Controller('tags')
export class TagsController {
  constructor(
    @Inject(ITagsService) private readonly tagsService: ITagsService,
  ) {}

  @Post()
  create(@Body() createTagDto: CreateTagDto, @ActiveUserId() userId: string) {
    return this.tagsService.create(createTagDto, userId);
  }

  @Get()
  findAll(@ActiveUserId() userId: string) {
    return this.tagsService.findAll(userId);
  }

  @Get(':id')
  findOne(@Param('id') id: string, @ActiveUserId() userId: string) {
    return this.tagsService.getTagById(userId, id);
  }
}
