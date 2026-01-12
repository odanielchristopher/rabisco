package com.example.rabisco.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.rabisco.MainActivity
import com.example.rabisco.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

// Receiver pra mostrar a notificacao
class NotificationReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "com.example.rabisco.DAILY_NOTIFICATION" -> {
                showNotification(context)
                // Reagendar pro proximo dia
                reagendarParaProximoDia(context)
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                // Reagendar apos reiniciar
                NotificationScheduler.rescheduleNotifications(context)
            }
        }
    }

    private fun showNotification(context: Context) {
        // Verificar permissao antes
        if (!NotificationHelper.hasNotificationPermission(context)) {
            return
        }

        createNotificationChannel(context)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)  // ⚠️ ERRO: R não foi importado!
            .setContentTitle("Hora de escrever! ✏️")
            .setContentText("Que tal registrar como foi seu dia?")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Reserve alguns minutos para escrever sobre o seu dia. Seus pensamentos e sentimentos são importantes!")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, notification)
            }
        } catch (e: SecurityException) {
            // Permissao negada
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Lembretes Diários"
            val descriptionText = "Notificações para lembrar você de escrever"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun reagendarParaProximoDia(context: Context) {
        scope.launch {
            try {
                val sessionRepo = com.example.rabisco.data.local.SessionRepository(context)
                val time = sessionRepo.getNotificationTime()
                val (hour, minute) = parseTime(time)
                NotificationScheduler.scheduleNotification(context, hour, minute)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseTime(time: String): Pair<Int, Int> {
        val parts = time.split(":")
        return Pair(parts[0].toInt(), parts[1].toInt())
    }

    companion object {
        private const val CHANNEL_ID = "daily_reminder_channel"
        private const val NOTIFICATION_ID = 1
    }
}