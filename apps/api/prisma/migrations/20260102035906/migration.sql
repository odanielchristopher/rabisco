/*
  Warnings:

  - A unique constraint covering the columns `[user_id,name]` on the table `tags` will be added. If there are existing duplicate values, this will fail.

*/
-- DropIndex
DROP INDEX "public"."tags_name_key";

-- AlterTable
ALTER TABLE "texts" ADD COLUMN     "daily_prompt_id" UUID;

-- CreateTable
CREATE TABLE "daily_prompts" (
    "id" UUID NOT NULL,
    "prompt" TEXT NOT NULL,
    "date" DATE NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMPTZ NOT NULL,

    CONSTRAINT "daily_prompts_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "daily_prompts_date_key" ON "daily_prompts"("date");

-- CreateIndex
CREATE UNIQUE INDEX "tags_user_id_name_key" ON "tags"("user_id", "name");

-- AddForeignKey
ALTER TABLE "texts" ADD CONSTRAINT "texts_daily_prompt_id_fkey" FOREIGN KEY ("daily_prompt_id") REFERENCES "daily_prompts"("id") ON DELETE SET NULL ON UPDATE CASCADE;
