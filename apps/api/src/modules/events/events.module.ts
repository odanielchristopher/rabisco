import { Global, Module } from '@nestjs/common';
import { EventEmitterModule } from '@nestjs/event-emitter';

import { CustomEventEmitter } from './contract/custom-event-emitter.contract';
import { NestEventEmitter } from './nest-event.emitter';

@Global()
@Module({
  imports: [EventEmitterModule.forRoot()],
  providers: [{ provide: CustomEventEmitter, useClass: NestEventEmitter }],
  exports: [{ provide: CustomEventEmitter, useClass: NestEventEmitter }],
})
export class EventsModule {}
