import { Inject, Injectable, NotFoundException } from '@nestjs/common';

import { ITextsRepository } from '@shared/database/contracts/texts-repository.contract';
import { countWords } from '@shared/utils/shared.utils';

import { ITextsService } from '../contracts/text-service.contract';
import { IValidateTextOwnershipService } from '../contracts/validate-text-ownership-service.contract';
import { Text } from '../entities/text.entity';

@Injectable()
export class TextsService implements ITextsService {
  constructor(
    @Inject(ITextsRepository)
    private readonly textsRepository: ITextsRepository,
    @Inject(IValidateTextOwnershipService)
    private readonly validateTextOwnershipService: IValidateTextOwnershipService,
  ) {}

  findAll({
    userId,
    filters = {},
  }: ITextsService.FindAllInput): Promise<Text[]> {
    const { category, searchQuery } = filters;
    return this.textsRepository.findAllByUserId({
      userId,
      filters: { category, searchQuery },
    });
  }

  async findOne({ textId, userId }: ITextsService.FindOneInput): Promise<Text> {
    const text = await this.textsRepository.findOneById({ userId, textId });

    if (!text) {
      throw new NotFoundException('Texto não encontrado');
    }

    return text;
  }

  create(input: ITextsService.CreateInput): Promise<Text> {
    const { userId, createTextDto } = input;
    const { title, content, type } = createTextDto;

    const wordCount = countWords(content);

    return this.textsRepository.create({
      userId,
      data: { title, content, type, wordCount },
    });
  }

  async update({
    textId,
    updateTextDto,
    userId,
  }: ITextsService.UpdateInput): Promise<Text> {
    await this.validateTextOwnershipService.validate({ userId, textId });

    const { type, title, content } = updateTextDto;

    const wordCount = countWords(content);

    return this.textsRepository.update({
      textId,
      data: { title, content, type, wordCount },
    });
  }

  async delete({ textId, userId }: ITextsService.DelelteInput): Promise<void> {
    await this.validateTextOwnershipService.validate({ userId, textId });

    await this.textsRepository.delete({ textId });
  }
}
