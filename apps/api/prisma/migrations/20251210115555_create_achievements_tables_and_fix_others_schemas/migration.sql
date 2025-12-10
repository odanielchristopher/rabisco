/*
  Warnings:

  - You are about to drop the column `score_id` on the `users` table. All the data in the column will be lost.
  - You are about to drop the column `streak_id` on the `users` table. All the data in the column will be lost.
  - You are about to drop the column `missions_id` on the `users_missions` table. All the data in the column will be lost.
  - A unique constraint covering the columns `[user_id,mission_id]` on the table `users_missions` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `mission_id` to the `users_missions` table without a default value. This is not possible if the table is not empty.

*/
-- CreateEnum
CREATE TYPE "achievement_type" AS ENUM ('TEXT_QUANTITY', 'DAY_SEQUENCE', 'MISSION');

-- DropForeignKey
ALTER TABLE "public"."users" DROP CONSTRAINT "users_score_id_fkey";

-- DropForeignKey
ALTER TABLE "public"."users" DROP CONSTRAINT "users_streak_id_fkey";

-- DropForeignKey
ALTER TABLE "public"."users_missions" DROP CONSTRAINT "users_missions_missions_id_fkey";

-- DropIndex
DROP INDEX "public"."users_score_id_key";

-- DropIndex
DROP INDEX "public"."users_streak_id_key";

-- DropIndex
DROP INDEX "public"."users_missions_user_id_missions_id_key";

-- AlterTable
ALTER TABLE "daily_missions" ALTER COLUMN "available_date" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "forgot_password_codes" ALTER COLUMN "expires_at" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "refresh_tokens" ALTER COLUMN "issues_at" SET DATA TYPE TIMESTAMPTZ,
ALTER COLUMN "expires_at" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "streaks" ALTER COLUMN "last_date" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "texts" ALTER COLUMN "created_at" SET DATA TYPE TIMESTAMPTZ,
ALTER COLUMN "updated_at" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "users" DROP COLUMN "score_id",
DROP COLUMN "streak_id",
ALTER COLUMN "created_at" SET DATA TYPE TIMESTAMPTZ,
ALTER COLUMN "updated_at" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "users_missions" DROP COLUMN "missions_id",
ADD COLUMN     "mission_id" UUID NOT NULL,
ALTER COLUMN "completion_date" SET DATA TYPE TIMESTAMPTZ;

-- AlterTable
ALTER TABLE "users_scores" ALTER COLUMN "updated_at" SET DATA TYPE TIMESTAMPTZ;

-- CreateTable
CREATE TABLE "achievements" (
    "id" UUID NOT NULL,
    "title" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    "type" "achievement_type" NOT NULL,
    "goal" INTEGER NOT NULL,

    CONSTRAINT "achievements_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "users_achievements" (
    "user_id" UUID NOT NULL,
    "achievement_id" UUID NOT NULL,
    "goalDate" TIMESTAMPTZ
);

-- CreateIndex
CREATE UNIQUE INDEX "users_achievements_user_id_achievement_id_key" ON "users_achievements"("user_id", "achievement_id");

-- CreateIndex
CREATE UNIQUE INDEX "users_missions_user_id_mission_id_key" ON "users_missions"("user_id", "mission_id");

-- AddForeignKey
ALTER TABLE "users_achievements" ADD CONSTRAINT "users_achievements_achievement_id_fkey" FOREIGN KEY ("achievement_id") REFERENCES "achievements"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users_achievements" ADD CONSTRAINT "users_achievements_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users_missions" ADD CONSTRAINT "users_missions_mission_id_fkey" FOREIGN KEY ("mission_id") REFERENCES "daily_missions"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users_scores" ADD CONSTRAINT "users_scores_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "streaks" ADD CONSTRAINT "streaks_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;
