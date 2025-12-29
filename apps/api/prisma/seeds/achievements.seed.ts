import { ACHIEVEMENTS } from '@shared/constants/achievements.contants';

import { prisma } from './lib/prisma-client.lib';

export async function seedAchievements() {
  await prisma.achievement.createMany({
    data: ACHIEVEMENTS,
    skipDuplicates: true,
  });

  // eslint-disable-next-line no-console
  console.log('✅ Achievements seeded successfully.');
}
