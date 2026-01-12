export enum AchievementType {
  TEXT_QUANTITY = 'TEXT_QUANTITY',
  DAY_SEQUENCE = 'DAY_SEQUENCE',
  MISSION = 'MISSION',
}

export const ACHIEVEMENTS = [
  // Text quantity achievements
  {
    code: 'TEXT_10',
    title: 'Escreveu 10 textos',
    description: 'Você escreveu 10 textos!',
    type: AchievementType.TEXT_QUANTITY,
    goal: 10,
  },
  {
    code: 'TEXT_25',
    title: 'Escreveu 25 textos',
    description: 'Você escreveu 25 textos!',
    type: AchievementType.TEXT_QUANTITY,
    goal: 25,
  },
  {
    code: 'TEXT_50',
    title: 'Escreveu 50 textos',
    description: 'Você escreveu 50 textos!',
    type: AchievementType.TEXT_QUANTITY,
    goal: 50,
  },
  {
    code: 'TEXT_100',
    title: 'Escreveu 100 textos',
    description: 'Você escreveu 100 textos!',
    type: AchievementType.TEXT_QUANTITY,
    goal: 100,
  },

  // Streak achievements
  {
    code: 'STREAK_3',
    title: 'Streak de 3 dias',
    description: 'Manteve escrita por 3 dias seguidos.',
    type: AchievementType.DAY_SEQUENCE,
    goal: 3,
  },
  {
    code: 'STREAK_7',
    title: 'Streak de 7 dias',
    description: 'Manteve escrita por 7 dias seguidos.',
    type: AchievementType.DAY_SEQUENCE,
    goal: 7,
  },
  {
    code: 'STREAK_14',
    title: 'Streak de 14 dias',
    description: 'Manteve escrita por 14 dias seguidos.',
    type: AchievementType.DAY_SEQUENCE,
    goal: 14,
  },
  {
    code: 'STREAK_30',
    title: 'Streak de 30 dias',
    description: 'Manteve escrita por 30 dias seguidos.',
    type: AchievementType.DAY_SEQUENCE,
    goal: 30,
  },

  // Mission achievements
  {
    code: 'MISSION_5',
    title: 'Completou 5 missões',
    description: 'Você completou 5 missões diárias.',
    type: AchievementType.MISSION,
    goal: 5,
  },
  {
    code: 'MISSION_10',
    title: 'Completou 10 missões',
    description: 'Você completou 10 missões diárias.',
    type: AchievementType.MISSION,
    goal: 10,
  },
  {
    code: 'MISSION_20',
    title: 'Completou 20 missões',
    description: 'Você completou 20 missões diárias.',
    type: AchievementType.MISSION,
    goal: 20,
  },
  {
    code: 'MISSION_50',
    title: 'Completou 50 missões',
    description: 'Você completou 50 missões diárias.',
    type: AchievementType.MISSION,
    goal: 50,
  },
];
