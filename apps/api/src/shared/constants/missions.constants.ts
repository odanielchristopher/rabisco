import { pointsTo } from './points.constants';

export enum MissionType {
  WRITE_TEXT = 'WRITE_TEXT',
  WORD_QUANTITY = 'WORD_QUANTITY',
  CUSTOM = 'CUSTOM',
}

export const MISSIONS = [
  {
    type: MissionType.WRITE_TEXT,
    description: 'Escreva qualquer coisa hoje',
    goal: 1,
    points: pointsTo.WRITE_TEXT(),
  },
  {
    type: MissionType.WORD_QUANTITY,
    description: 'Escreva ao menos 200 palavras',
    goal: 200,
    points: pointsTo.WORD_QUANTITY(),
  },
];
