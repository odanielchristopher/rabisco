import { MISSIONS } from '@shared/constants/missions.constants';

import { prisma } from './lib/prisma-client.lib';

export async function seedMissions() {
  await prisma.dailyMission.createMany({
    data: MISSIONS.map((mission) => ({
      ...mission,
      availableDate: new Date(), // Setando a data de disponibilidade para hoje
    })),
    skipDuplicates: true,
  });

  // eslint-disable-next-line no-console
  console.log('✅ Daily missions seeded successfully.');
}
