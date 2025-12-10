export class TextCreatedEvent {
  constructor(
    public readonly userId: string,
    public readonly textId: string,
  ) {}
}
