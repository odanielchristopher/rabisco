import { DailyMission } from '@prisma/client';

export class MissionCompletedPayload {
  constructor(
    public readonly userId: string,
    public readonly mission: DailyMission,
  ) {}
}
