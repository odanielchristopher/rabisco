    package com.example.rabisco.data.local

    import android.content.Context
    import androidx.datastore.preferences.core.booleanPreferencesKey
    import androidx.datastore.preferences.core.edit
    import androidx.datastore.preferences.core.stringPreferencesKey
    import androidx.datastore.preferences.preferencesDataStore
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.flow.map

    class SessionRepository(private val context: Context) {

        private val DARK_MODE = booleanPreferencesKey("dark_mode")

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

        fun observeDarkMode(): Flow<Boolean> {
            return context.sessionDataStore.data.map { prefs ->
                prefs[DARK_MODE] ?: false
            }
        }

        suspend fun saveDarkMode(enabled: Boolean) {
            context.sessionDataStore.edit { prefs ->
                prefs[DARK_MODE] = enabled
            }
        }

        suspend fun getDarkMode(): Boolean {
            return context.sessionDataStore.data.first()[DARK_MODE] ?: false
        }
    }