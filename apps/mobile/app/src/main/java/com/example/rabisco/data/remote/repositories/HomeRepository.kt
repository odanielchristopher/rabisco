package com.example.rabisco.data.remote.repositories

import android.content.Context
import com.example.rabisco.data.remote.api.PromptsApi
import com.example.rabisco.data.remote.api.TextsApi
import com.example.rabisco.data.remote.api.UsersApi
import com.example.rabisco.data.remote.dto.response.HomeStatsData
import com.example.rabisco.data.remote.providers.ApiProvider
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.Int

class HomeRepository(private val context: Context) {
    private val usersApi: UsersApi = ApiProvider
        .provideAuthenticated(context)
        .create(UsersApi::class.java)

    private val textsApi: TextsApi = ApiProvider
        .provideAuthenticated(context)
        .create(TextsApi::class.java)

    private val promptsApi: PromptsApi = ApiProvider
        .provideAuthenticated(context)
        .create(PromptsApi::class.java)

    suspend fun getHomeData(): Result<HomeStatsData> {
        return try {
            val texts = textsApi.getAllTexts(category = "", search = "")
            val dailyPrompt = promptsApi.getDailyPrompt()

            val streak = calculateStreak(texts.map { it.createdAt })
            val textsWritten = texts.size
            val textsThisWeek = calculateTextsThisWeek(texts.map { it.createdAt })

            // TODO: Substituir por GET /achievements quando API estiver disponível
            val totalXp = calculateMockXp(textsWritten, streak)

            val homeData = HomeStatsData(
                streak = streak,
                totalXP = totalXp,
                totalTexts = textsWritten,
                textsWeek = textsThisWeek,
                promptOfDay = dailyPrompt.prompt
            )

            Result.success(homeData)
        } catch (e: Exception) {
            println("HomeRepository.getHomeData() Error: ${e.message}")
            e.printStackTrace()
            Result.failure(e)

        }
    }

    suspend fun getDailyPrompt(): Result<String> {
        return try {
            val prompt = promptsApi.getDailyPrompt()
            Result.success(prompt.prompt)
        } catch (e: Exception) {
            println("HomeRepository.getDailyPrompt() Error: ${e.message}")
            Result.failure(e)
        }
    }

    private fun calculateMockXp(textsWritten: Int, streak: Int): Int { //verificar se o cálculo é esse
        return (textsWritten * 50) + (streak * 100)
    }

    private fun calculateStreak(createdAtDates: List<String>): Int { //esse código é bizarro
        if (createdAtDates.isEmpty()) return 0

        return try {
            val dates = createdAtDates
                .mapNotNull {
                    try {
                        ZonedDateTime.parse(it).toLocalDate()
                    } catch (e: Exception) {
                        println("Failed to parse date: $it")
                        null
                    }
                }
                .distinct()
                .sortedDescending()

            if (dates.isEmpty()) return 0

            val today = LocalDateTime.now().toLocalDate()
            val yesterday = today.minusDays(1)

            if (dates.first() != today && dates.first() != yesterday) {
                return 0
            }

            var streak = 0
            var expectedDate = dates.first()

            for (date in dates) {
                if (date == expectedDate) {
                    streak++
                    expectedDate = expectedDate.minusDays(1)
                } else {
                    break
                }
            }

            streak

        } catch (e: Exception) {
            println("calculateStreak() Error: ${e.message}")
            0
        }
    }

    private fun calculateTextsThisWeek(createdAtDates: List<String>): Int { //esse tmb tem data
        return try {
            val now = LocalDateTime.now()
            val sevenDaysAgo = now.minusDays(7)

            createdAtDates.count { dateString ->
                try {
                    val date = ZonedDateTime.parse(dateString).toLocalDateTime()
                    date.isAfter(sevenDaysAgo)
                } catch (e: Exception) {
                    false
                }
            }
        } catch (e: Exception) {
            println("calculateTextsThisWeek() Error: ${e.message}")
            0
        }
    }
}