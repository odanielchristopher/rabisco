import { Injectable } from '@nestjs/common';

import { Text } from '@modules/texts/entities/text.entity';

import { ITextsRepository } from '../contracts/texts-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class TextsRepository implements ITextsRepository {
  constructor(private readonly prismaService: PrismaService) {}

  count(countDto: ITextsRepository.WhereInput): Promise<number> {
    return this.prismaService.text.count({ where: countDto });
  }

  async findAllByUserId(
    findAllDto: ITextsRepository.FindAllTextsDto,
  ): Promise<Text[]> {
    const { userId, filters = {} } = findAllDto;
    const { category, searchQuery } = filters;

    const findedTexts = await this.prismaService.text.findMany({
      where: {
        userId,
        categories: category ? { some: { categoryId: category } } : undefined,
        title: {
          contains: searchQuery,
          mode: 'insensitive',
        },
      },
      select: this.select(),
    });

    return findedTexts as Text[];
  }

  async findOneById(
    findOneDto: ITextsRepository.FindOneTextDto,
  ): Promise<Text | null> {
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

  async create(createDto: ITextsRepository.CreateTextDto): Promise<Text> {
    const { userId, data } = createDto;
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

  async update(updateDto: ITextsRepository.UpdateTextDto): Promise<Text> {
    const { textId, data } = updateDto;
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

  async delete(deleteDto: ITextsRepository.DeleteTextDto): Promise<void> {
    const { textId } = deleteDto;

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
