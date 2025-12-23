export class MissionCompletedPayload {
  constructor(
    public readonly userId: string,
    public readonly missionKey: string,
  ) {}
}
