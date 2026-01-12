import { Module } from '@nestjs/common';
import { TagsService } from './tags.service';
import { TagsController } from './tags.controller';
import { ITagsService } from './contracts/tags-service.contract';

@Module({
  controllers: [TagsController],
  providers: [{ provide: ITagsService, useClass: TagsService }],
})
export class TagsModule {}
