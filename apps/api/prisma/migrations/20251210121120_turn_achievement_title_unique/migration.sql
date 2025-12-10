/*
  Warnings:

  - A unique constraint covering the columns `[title]` on the table `achievements` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "achievements_title_key" ON "achievements"("title");
