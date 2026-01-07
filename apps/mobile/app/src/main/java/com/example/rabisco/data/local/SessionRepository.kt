    package com.example.rabisco.data.local

    import android.content.Context
    import androidx.datastore.core.DataStore
    import androidx.datastore.preferences.core.Preferences
    import androidx.datastore.preferences.core.booleanPreferencesKey
    import androidx.datastore.preferences.core.edit
    import androidx.datastore.preferences.core.stringPreferencesKey
    import androidx.datastore.preferences.preferencesDataStore
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.flow.map

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
    class SessionRepository(private val context: Context) {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val TOKEN = stringPreferencesKey("token")


        suspend fun saveToken(token: String) {
            context.dataStore.edit { prefs ->
                prefs[TOKEN] = token
            }
        }

        suspend fun getToken(): String? {
            return context.dataStore.data.first()[TOKEN]
        }

        suspend fun clearSession() {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }

        fun observeDarkMode(): Flow<Boolean> {
            return context.dataStore.data.map { prefs ->
                prefs[DARK_MODE] ?: false
            }
        }

        suspend fun saveDarkMode(enabled: Boolean) {
            context.dataStore.edit { prefs ->
                prefs[DARK_MODE] = enabled
            }
        }

        suspend fun getDarkMode(): Boolean {
            return context.dataStore.data.first()[DARK_MODE] ?: false
        }
    }