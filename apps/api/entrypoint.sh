#!/bin/sh
set -e

echo "Generating Prisma Client..."
npx prisma generate

echo "Running database migrations..."
npx prisma db push

echo "Seeding database..."
npx prisma db seed

echo "Starting NestJS application..."
exec npm run start:dev
