/*
  Warnings:

  - Added the required column `type` to the `daily_missions` table without a default value. This is not possible if the table is not empty.

*/
-- CreateEnum
CREATE TYPE "daily_mission_type" AS ENUM ('WRITE_TEXT', 'WORD_QUANTITY', 'CUSTOM');

-- AlterTable
ALTER TABLE "daily_missions" ADD COLUMN     "type" "daily_mission_type" NOT NULL;
