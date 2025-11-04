import type { Request as RequestExpress } from 'express';

export type Request = RequestExpress & {
  userId?: string;
};

export type JwtPaylod = { sub: string };
