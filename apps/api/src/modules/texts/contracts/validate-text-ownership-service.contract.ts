import { Text } from '../entities/text.entity';

export const IValidateTextOwnershipService = Symbol(
  'IValidateTextOwnershipService',
);

export interface IValidateTextOwnershipService {
  validate(input: IValidateTextOwnershipService.Input): Promise<Text>;
}

export namespace IValidateTextOwnershipService {
  export type Input = {
    textId: string;
    userId: string;
  };
}
