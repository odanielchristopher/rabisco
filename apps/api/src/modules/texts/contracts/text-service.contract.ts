import { ITextsRepository } from '@shared/database/contracts/texts-repository.contract';

import { CreateTextDto } from '../dto/create-text.dto';
import { UpdateTextDto } from '../dto/update-text.dto';
import { Text } from '../entities/text.entity';

export const ITextsService = Symbol('ITextsService');

export interface ITextsService {
  findAll(input: ITextsService.FindAllInput): Promise<Text[]>;

  findOne(input: ITextsService.FindOneInput): Promise<Text>;

  create(input: ITextsService.CreateInput): Promise<Text>;

  update(input: ITextsService.UpdateInput): Promise<Text>;

  delete(input: ITextsService.DelelteInput): Promise<void>;
}

export namespace ITextsService {
  export type FindAllInput = {
    userId: string;
    filters?: ITextsRepository.FindAllTextsDto['filters'];
  };

  export type FindOneInput = {
    userId: string;
    textId: string;
  };

  export type CreateInput = {
    userId: string;
    createTextDto: CreateTextDto;
  };

  export type UpdateInput = {
    userId: string;
    textId: string;
    updateTextDto: UpdateTextDto;
  };

  export type DelelteInput = {
    userId: string;
    textId: string;
  };
}
