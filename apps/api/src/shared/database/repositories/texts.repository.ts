import { Injectable } from '@nestjs/common';

import { Text } from '@modules/texts/entities/text.entity';

import {
  CreateTextDto,
  DeleteTextDto,
  FindAllTextsDto,
  FindOneTextDto,
  ITextsRepository,
  UpdateTextDto,
} from '../contracts/texts-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class TextsRepository implements ITextsRepository {
  constructor(private readonly prismaService: PrismaService) {}

  async findAllByUserId(findAllDto: FindAllTextsDto): Promise<Text[]> {
    const { userId, filters } = findAllDto;

    const findedTexts = await this.prismaService.text.findMany({
      where: {
        userId,
        categories: { some: { categoryId: filters?.category } },
      },
      select: {
        id: true,
        type: true,
        title: true,
        content: true,
        wordCount: true,
        createdAt: true,
        updatedAt: true,
      },
    });

    return findedTexts as Text[];
  }

  findOneById(findOneDto: FindOneTextDto): Promise<Text | null> {
    throw new Error('Method not implemented.');
  }

  create(createTextDto: CreateTextDto): Promise<Text> {
    throw new Error('Method not implemented.');
  }

  update(updateTextDto: UpdateTextDto): Promise<Text> {
    throw new Error('Method not implemented.');
  }

  delete(deleteTextDto: DeleteTextDto): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
