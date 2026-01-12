/*
  Warnings:

  - A unique constraint covering the columns `[score_id]` on the table `users` will be added. If there are existing duplicate values, this will fail.
  - A unique constraint covering the columns `[streak_id]` on the table `users` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `score_id` to the `users` table without a default value. This is not possible if the table is not empty.
  - Added the required column `streak_id` to the `users` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "users" ADD COLUMN     "score_id" UUID NOT NULL,
ADD COLUMN     "streak_id" UUID NOT NULL;

-- CreateIndex
CREATE UNIQUE INDEX "users_score_id_key" ON "users"("score_id");

-- CreateIndex
CREATE UNIQUE INDEX "users_streak_id_key" ON "users"("streak_id");

-- AddForeignKey
ALTER TABLE "users" ADD CONSTRAINT "users_score_id_fkey" FOREIGN KEY ("score_id") REFERENCES "users_scores"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users" ADD CONSTRAINT "users_streak_id_fkey" FOREIGN KEY ("streak_id") REFERENCES "streaks"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
