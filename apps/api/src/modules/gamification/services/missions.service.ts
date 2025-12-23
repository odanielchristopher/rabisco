import { Injectable } from '@nestjs/common';

import { CustomEventEmitter } from '@modules/events/contract/custom-event-emitter.contract';
import { MissionCompletedPayload } from '@modules/events/payloads/mission-completed.payload';
import { TextCreatedEvent } from '@modules/texts/events/text-created.event';
import { MissionType } from '@shared/constants/missions.constants';
import {
  IMissionsRepository,
  UserMissionWithDailyMission,
} from '@shared/database/contracts/missions-repository.contract';

@Injectable()
export class MissionsService {
  constructor(
    private readonly missionsRepository: IMissionsRepository,
    private readonly eventEmitter: CustomEventEmitter,
  ) {}

  async evaluateTextCreated(event: TextCreatedEvent) {
    const missions = await this.missionsRepository.findUserMissions({
      userId: event.userId,
    });

    for (const userMission of missions) {
      switch (userMission.mission.type) {
        case MissionType.WRITE_TEXT:
          await this.progressMission(userMission, 1);
          break;

        case MissionType.WORD_QUANTITY:
          await this.progressMission(userMission, event.wordCount);
          break;

        default:
          break;
      }
    }
  }

  private async progressMission(
    userMission: UserMissionWithDailyMission,
    value: number,
  ) {
    if (userMission.completed) {
      return;
    }

    const updated = await this.missionsRepository.incrementProgress({
      userId: userMission.userId,
      missionId: userMission.missionId,
      value,
    });

    if (!updated || !updated.completed) {
      return;
    }

    this.eventEmitter.emit(
      'mission.completed',
      new MissionCompletedPayload(userMission.userId, userMission.mission),
    );
  }
}
