import { plainToInstance, Type } from 'class-transformer';
import {
  IsEmail,
  IsNotEmpty,
  IsString,
  Min,
  NotEquals,
  ValidateNested,
  validateSync,
} from 'class-validator';

class User {
  @IsString()
  @IsNotEmpty()
  name: string;

  @IsEmail()
  @IsString()
  @IsNotEmpty()
  email: string;

  @IsString()
  @IsNotEmpty()
  @Min(6)
  password: string;
}

class Env {
  @IsNotEmpty()
  @IsString()
  @NotEquals('unsecure_jwt_secret')
  jwtSecret: string;

  @IsNotEmpty()
  @IsString()
  dbUrl: string;

  @ValidateNested()
  @Type(() => User)
  user: User;
}

export const env: Env = plainToInstance(Env, {
  jwtSecret: process.env.JWT_SECRET!,
  dbUrl: process.env.DATABASE_URL!,
  user: {
    name: process.env.USER_NAME!,
    email: process.env.USER_EMAIL!,
    password: process.env.USER_PASSWORD!,
  },
} satisfies Env);

const errors = validateSync(env);

if (errors.length > 0) {
  throw new Error(JSON.stringify(errors, null, 2));
}
