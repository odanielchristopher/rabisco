/*
  Warnings:

  - You are about to drop the column `goalDate` on the `users_achievements` table. All the data in the column will be lost.
  - A unique constraint covering the columns `[code]` on the table `achievements` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `code` to the `achievements` table without a default value. This is not possible if the table is not empty.

*/
-- DropIndex
DROP INDEX "public"."achievements_title_key";

-- AlterTable
ALTER TABLE "achievements" ADD COLUMN     "code" TEXT NOT NULL;

-- AlterTable
ALTER TABLE "users_achievements" DROP COLUMN "goalDate",
ADD COLUMN     "achieved_at" TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "goal_date" TIMESTAMPTZ;

-- CreateIndex
CREATE UNIQUE INDEX "achievements_code_key" ON "achievements"("code");
