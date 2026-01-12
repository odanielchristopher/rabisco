import { HttpService } from '@nestjs/axios';
import { Injectable, Logger } from '@nestjs/common';
import { firstValueFrom } from 'rxjs';

import { FALLBACK_PROMPTS } from '@shared/constants/prompts.constants';
import { PrismaService } from '@shared/database/prisma.service';

import { DailyPrompt } from './entities/daily-prompt.entity';

@Injectable()
export class PromptsService {
  private readonly logger = new Logger(PromptsService.name);

  constructor(
    private readonly prisma: PrismaService,
    private readonly http: HttpService,
  ) {}

  private getTodayDateOnly(): Date {
    const now = new Date();
    return new Date(now.getFullYear(), now.getMonth(), now.getDate());
  }

  async getTodayPrompt(): Promise<DailyPrompt> {
    const today = this.getTodayDateOnly();

    let prompt = await this.prisma.dailyPrompt.findUnique({
      where: { date: today },
    });

    if (!prompt) {
      let text: string;

      try {
        text = await this.fetchExternalPrompt();
      } catch (error: unknown) {
        const message =
          error instanceof Error
            ? error.message
            : 'Erro desconhecido ao buscar prompt diário';

        this.logger.warn(
          `Falha ao buscar prompt no serviço externo, usando fallback local. Detalhes: ${message}`,
        );

        text = this.getRandomFallbackPrompt();
      }

      prompt = await this.prisma.dailyPrompt.create({
        data: {
          prompt: text,
          date: today,
        },
      });
    }

    return prompt as DailyPrompt;
  }

  private async fetchExternalPrompt(): Promise<string> {
    const url = process.env.SUPABASE_URL;
    const token = process.env.SUPABASE_TOKEN;

    if (!token || !url) {
      throw new Error(
        'SUPABASE_TOKEN ou SUPABASE_URL não configurado nas variáveis de ambiente',
      );
    }

    const response = await firstValueFrom(
      this.http.post<{ prompt: string }>(
        url,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      ),
    );

    const text = response.data?.prompt as string | undefined;

    if (!text) {
      throw new Error(
        'Serviço externo de prompt não retornou um campo "prompt"',
      );
    }

    return text;
  }

  private getRandomFallbackPrompt(): string {
    if (!FALLBACK_PROMPTS.length) {
      throw new Error('Lista de prompts locais está vazia');
    }

    const randomIndex = Math.floor(Math.random() * FALLBACK_PROMPTS.length);
    return FALLBACK_PROMPTS[randomIndex];
  }

  async findById(id: string): Promise<DailyPrompt | null> {
    const prompt = await this.prisma.dailyPrompt.findUnique({ where: { id } });
    return prompt as DailyPrompt | null;
  }
}
