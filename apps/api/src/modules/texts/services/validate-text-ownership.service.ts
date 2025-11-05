import { Inject, NotFoundException } from '@nestjs/common';

import { ITextsRepository } from '@shared/database/contracts/texts-repository.contract';

import { IValidateTextOwnershipService } from '../contracts/validate-text-ownership-service.contract';
import { Text } from '../entities/text.entity';

export class ValidateTextOwnershipService
  implements IValidateTextOwnershipService
{
  constructor(
    @Inject(ITextsRepository)
    private readonly textsRepository: ITextsRepository,
  ) {}

  async validate({
    textId,
    userId,
  }: IValidateTextOwnershipService.Input): Promise<Text> {
    const text = await this.textsRepository.findOneById({ textId, userId });

    if (!text) {
      throw new NotFoundException('Texto não encontrado.');
    }

    return text;
  }
}
