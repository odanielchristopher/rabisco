import { hash } from 'bcryptjs';

import { prisma } from './lib/prisma-client.lib';

export async function seedUser() {
  const hashedPassword = await hash(process.env.USER_PASSWORD!, 10);

  await prisma.user.upsert({
    where: { email: process.env.USER_EMAIL! },
    update: {},
    create: {
      name: process.env.USER_NAME!,
      email: process.env.USER_EMAIL!,
      password: hashedPassword,
    },
  });

  // eslint-disable-next-line no-console
  console.log('✅ User seeded successfully.');
}
