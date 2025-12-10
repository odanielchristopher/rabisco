import { prisma } from './lib/prisma-client.lib';
import { seedUser } from './user.seed';

async function main() {
  await seedUser();
}

main()
  .catch((e) => {
    // eslint-disable-next-line no-console
    console.error('❌ Seed error:', e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
