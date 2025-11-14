import { Injectable } from '@nestjs/common';
import { CreateTagDto } from './dto/create-tag.dto';

@Injectable()
export class TagsService {
  create(createTagDto: CreateTagDto) {
    return 'This action adds a new tag';
  }

  findAll() {
    return `This action returns all tags`;
  }

  findOne(id: number) {
    return `This action returns a #${id} tag`;
  }
}
