import { Injectable, NotFoundException } from '@nestjs/common';

import { CustomEventEmitter } from '@modules/events/contract/custom-event-emitter.contract';
import { ITextsRepository } from '@shared/database/contracts/texts-repository.contract';
import { countWords } from '@shared/utils/shared.utils';

import { ITextsService } from '../contracts/text-service.contract';
import { IValidateTextOwnershipService } from '../contracts/validate-text-ownership-service.contract';
import { Text } from '../entities/text.entity';
import { TextCreatedEvent } from '../events/text-created.event';

@Injectable()
export class TextsService implements ITextsService {
  constructor(
    private readonly textsRepository: ITextsRepository,
    private readonly validateTextOwnershipService: IValidateTextOwnershipService,
    private readonly eventEmitter: CustomEventEmitter,
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

  async create(input: ITextsService.CreateInput): Promise<Text> {
    const { userId, createTextDto } = input;
    const { title, content, type, dailyPromptId, tagIds } = createTextDto;

    const wordCount = countWords(content);

    const text = await this.textsRepository.create({
      userId,
      data: { title, content, type, wordCount, dailyPromptId, tagIds },
    });

    this.eventEmitter.emit(
      'text.created',
      new TextCreatedEvent(userId, text.id, wordCount),
    );

    return text;
  }

  async update({
    textId,
    updateTextDto,
    userId,
  }: ITextsService.UpdateInput): Promise<Text> {
    await this.validateTextOwnershipService.validate({ userId, textId });

    const { type, title, content, dailyPromptId, tagIds } = updateTextDto;

    const wordCount = countWords(content);

    return this.textsRepository.update({
      textId,
      data: { title, content, type, wordCount, dailyPromptId, tagIds },
    });
  }

  async delete({ textId, userId }: ITextsService.DelelteInput): Promise<void> {
    await this.validateTextOwnershipService.validate({ userId, textId });

    await this.textsRepository.delete({ textId });
  }
}
