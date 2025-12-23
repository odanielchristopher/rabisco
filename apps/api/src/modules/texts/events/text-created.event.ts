export class TextCreatedEvent {
  constructor(
    public readonly userId: string,
    public readonly textId: string,
    public readonly wordCount: number,
  ) {}
}
