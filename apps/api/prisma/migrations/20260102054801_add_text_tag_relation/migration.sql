-- CreateTable
CREATE TABLE "texts_tags" (
    "tag_id" UUID NOT NULL,
    "text_id" UUID NOT NULL
);

-- CreateIndex
CREATE UNIQUE INDEX "texts_tags_tag_id_text_id_key" ON "texts_tags"("tag_id", "text_id");

-- AddForeignKey
ALTER TABLE "texts_tags" ADD CONSTRAINT "texts_tags_text_id_fkey" FOREIGN KEY ("text_id") REFERENCES "texts"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "texts_tags" ADD CONSTRAINT "texts_tags_tag_id_fkey" FOREIGN KEY ("tag_id") REFERENCES "tags"("id") ON DELETE CASCADE ON UPDATE CASCADE;
