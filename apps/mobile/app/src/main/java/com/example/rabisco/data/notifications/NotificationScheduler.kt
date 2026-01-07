package com.example.rabisco.data.notifications

import android.R.attr.action
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar

// Objeto pra agendar notificacoes diarias
object NotificationScheduler {

    private const val REQUEST_CODE = 1001
    private const val TAG = "NotificationScheduler"
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Agendar notificacao
    fun scheduleNotification(context: Context, hour: Int, minute: Int) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, NotificationReceiver::class.java).apply {
                action = "com.example.rabisco.DAILY_NOTIFICATION"
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Configurar horario
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // Se ja passou hoje, agendar pra amanha
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            // Cancelar alarme anterior
            alarmManager.cancel(pendingIntent)

            // Agendar baseado na versao do Android
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    // API 31+ (Android 12+)
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    // API 23-30
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
                else -> {
                    // API < 23
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            }

            Log.d(TAG, "Notificação agendada para: ${calendar.time}")

        } catch (e: Exception) {
            Log.e(TAG, "Erro ao agendar notificação", e)
        }
    }

    // Cancelar notificacao
    fun cancelNotification(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, NotificationReceiver::class.java).apply {
                action = "com.example.rabisco.DAILY_NOTIFICATION"
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.cancel(pendingIntent)
            Log.d(TAG, "Notificação cancelada")

        } catch (e: Exception) {
            Log.e(TAG, "Erro ao cancelar notificação", e)
        }
    }

    // Reagendar apos boot
    fun rescheduleNotifications(context: Context) {
        scope.launch {
            try {
                val sessionRepo = com.example.rabisco.data.local.SessionRepository(context)
                val isEnabled = sessionRepo.getNotificationsEnabled()

                if (isEnabled) {
                    val time = sessionRepo.getNotificationTime()
                    val (hour, minute) = parseTime(time)
                    scheduleNotification(context, hour, minute)
                    Log.d(TAG, "Notificações reagendadas após boot")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao reagendar notificações", e)
            }
        }
    }

    private fun parseTime(time: String): Pair<Int, Int> {
        val parts = time.split(":")
        return Pair(parts[0].toIntOrNull() ?: 20, parts[1].toIntOrNull() ?: 0)
    }
}