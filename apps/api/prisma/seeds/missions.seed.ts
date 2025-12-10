import { MISSIONS } from '@shared/constants/missions.constants';

import { prisma } from './lib/prisma-client.lib';

export async function seedMissions() {
  for (const mission of MISSIONS) {
    await prisma.dailyMission.create({
      data: mission,
    });
  }

  // eslint-disable-next-line no-console
  console.log('✅ Daily missions seeded successfully.');
}
