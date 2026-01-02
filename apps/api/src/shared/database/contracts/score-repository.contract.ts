export abstract class IScoreRepository {
  abstract addPoints(
    addPointsDto: IScoreRepository.AddPointsDto,
  ): Promise<void>;
}

export namespace IScoreRepository {
  export type AddPointsDto = {
    userId: string;
    quantity: number;
  };
}
