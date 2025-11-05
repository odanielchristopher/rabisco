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
      select: this.select(),
    });

    return findedTexts as Text[];
  }

  async findOneById(findOneDto: FindOneTextDto): Promise<Text | null> {
    const { userId, textId } = findOneDto;

    const text = await this.prismaService.text.findUnique({
      where: {
        userId,
        id: textId,
      },
      select: this.select(),
    });

    return text as Text;
  }

  async create(createTextDto: CreateTextDto): Promise<Text> {
    const { userId, data } = createTextDto;
    const { type, title, content, wordCount } = data;

    const created = await this.prismaService.text.create({
      data: {
        userId,
        type,
        title,
        content,
        wordCount,
      },
      select: this.select(),
    });

    return created as Text;
  }

  async update(updateTextDto: UpdateTextDto): Promise<Text> {
    const { textId, data } = updateTextDto;
    const { type, title, content, wordCount } = data;

    const updated = await this.prismaService.text.update({
      where: { id: textId },
      data: {
        type,
        title,
        content,
        wordCount,
      },
      select: this.select(),
    });

    return updated as Text;
  }

  async delete(deleteTextDto: DeleteTextDto): Promise<void> {
    const { textId } = deleteTextDto;

    await this.prismaService.text.delete({ where: { id: textId } });
  }

  private select() {
    return {
      id: true,
      type: true,
      title: true,
      content: true,
      wordCount: true,
      createdAt: true,
      updatedAt: true,
    };
  }
}
