package com.example.openvision.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.openvision.MainActivity
import com.example.openvision.R

class AccessibilityWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_accessibility)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent)

        setupQuickActions(context, views)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setupQuickActions(context: Context, views: RemoteViews) {
        val daltonismIntent = Intent(context, WidgetActions::class.java).apply {
            action = "TOGGLE_DALTONISM"
        }
        val daltonismPending = PendingIntent.getBroadcast(
            context, 0, daltonismIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btn_daltonism, daltonismPending)

        val talkbackIntent = Intent(context, WidgetActions::class.java).apply {
            action = "TOGGLE_TALKBACK"
        }
        val talkbackPending = PendingIntent.getBroadcast(
            context, 1, talkbackIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btn_talkback, talkbackPending)

        val translateIntent = Intent(context, WidgetActions::class.java).apply {
            action = "OPEN_TRANSLATION"
        }
        val translatePending = PendingIntent.getBroadcast(
            context, 2, translateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btn_translate, translatePending)
    }
}

class WidgetActions : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "TOGGLE_DALTONISM" -> toggleDaltonism(context)
            "TOGGLE_TALKBACK" -> toggleTalkback(context)
            "OPEN_TRANSLATION" -> openTranslation(context)
        }
        super.onReceive(context, intent)
    }

    private fun toggleDaltonism(context: Context) {
        // Implementar toggle do filtro de daltonismo
    }

    private fun toggleTalkback(context: Context) {
        // Implementar toggle do talkback
    }

    private fun openTranslation(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("OPEN_TRANSLATION", true)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}