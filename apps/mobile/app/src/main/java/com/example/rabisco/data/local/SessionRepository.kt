    package com.example.rabisco.data.local

    import android.content.Context
    import androidx.datastore.preferences.core.edit
    import androidx.datastore.preferences.core.stringPreferencesKey
    import androidx.datastore.preferences.preferencesDataStore
    import kotlinx.coroutines.flow.first

    class SessionRepository(private val context: Context) {

        private val Context.sessionDataStore by preferencesDataStore("session")

        private val TOKEN = stringPreferencesKey("token")

        suspend fun saveToken(token: String) {
            context.sessionDataStore.edit { prefs ->
                prefs[TOKEN] = token
            }
        }

        suspend fun getToken(): String? {
            return context.sessionDataStore.data.first()[TOKEN]
        }

        suspend fun clearSession() {
            context.sessionDataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }