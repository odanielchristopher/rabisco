-- DropForeignKey
ALTER TABLE "public"."users" DROP CONSTRAINT "users_score_id_fkey";

-- DropForeignKey
ALTER TABLE "public"."users" DROP CONSTRAINT "users_streak_id_fkey";

-- CreateTable
CREATE TABLE "daily_missions" (
    "id" UUID NOT NULL,
    "description" TEXT NOT NULL,
    "points" INTEGER NOT NULL,
    "available_date" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "daily_missions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "users_missions" (
    "user_id" UUID NOT NULL,
    "missions_id" UUID NOT NULL,
    "completed" BOOLEAN NOT NULL DEFAULT false,
    "completion_date" TIMESTAMP
);

-- CreateIndex
CREATE UNIQUE INDEX "users_missions_user_id_missions_id_key" ON "users_missions"("user_id", "missions_id");

-- AddForeignKey
ALTER TABLE "users_missions" ADD CONSTRAINT "users_missions_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users_missions" ADD CONSTRAINT "users_missions_missions_id_fkey" FOREIGN KEY ("missions_id") REFERENCES "daily_missions"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users" ADD CONSTRAINT "users_score_id_fkey" FOREIGN KEY ("score_id") REFERENCES "users_scores"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "users" ADD CONSTRAINT "users_streak_id_fkey" FOREIGN KEY ("streak_id") REFERENCES "streaks"("id") ON DELETE CASCADE ON UPDATE CASCADE;
