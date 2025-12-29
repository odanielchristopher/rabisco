/*
  Warnings:

  - Added the required column `goal` to the `daily_missions` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "daily_missions" ADD COLUMN     "goal" INTEGER NOT NULL;
