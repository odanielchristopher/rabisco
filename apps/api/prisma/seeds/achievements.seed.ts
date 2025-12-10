import { ACHIEVEMENTS } from '@shared/constants/achievements.contants';

import { prisma } from './lib/prisma-client.lib';

export async function seedAchievements() {
  for (const achievement of ACHIEVEMENTS) {
    await prisma.achievement.upsert({
      where: {
        title: achievement.title,
      },
      update: {},
      create: achievement,
    });
  }

  // eslint-disable-next-line no-console
  console.log('✅ Achievements seeded successfully.');
}
