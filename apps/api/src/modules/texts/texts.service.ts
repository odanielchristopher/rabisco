import { Injectable } from '@nestjs/common';

import { CreateTextDto } from './dto/create-text.dto';
import { UpdateTextDto } from './dto/update-text.dto';

@Injectable()
export class TextsService {
  findAll() {
    return 'This action returns all texts';
  }

  findOne(id: number) {
    return `This action returns a #${id} text`;
  }

  create(createTextDto: CreateTextDto) {
    return 'This action adds a new text';
  }

  update(id: number, updateTextDto: UpdateTextDto) {
    return `This action updates a #${id} text`;
  }

  remove(id: number) {
    return `This action removes a #${id} text`;
  }
}
