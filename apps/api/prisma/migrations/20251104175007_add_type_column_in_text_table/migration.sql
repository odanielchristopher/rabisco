-- CreateEnum
CREATE TYPE "text_type" AS ENUM ('DIARY', 'DAY_PROMPT', 'FREE');

-- AlterTable
ALTER TABLE "texts" ADD COLUMN     "type" "text_type" NOT NULL DEFAULT 'FREE';
