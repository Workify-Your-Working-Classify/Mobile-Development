package com.capbatu.workify.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferences
    @Inject
    constructor(private val dataStore: DataStore<Preferences>) {
        fun getAuthSession(): Flow<AuthModel> {
            return dataStore.data.map {
                AuthModel(
                    it[USERID_KEY] ?: "",
                    it[DISPLAY_NAME_KEY] ?: "",
                    it[EMAIL_KEY] ?: "",
                    it[TOKEN_KEY] ?: "",
                    it[IS_LOGIN_KEY] ?: false,
                    it[THEME_PREFERENCE_KEY] ?: false,
                )
            }
        }

        suspend fun saveAuthSession(user: AuthModel) {
            dataStore.edit {
                it[USERID_KEY] = user.userId
                it[DISPLAY_NAME_KEY] = user.displayName
                it[EMAIL_KEY] = user.email
                it[TOKEN_KEY] = user.token
                it[IS_LOGIN_KEY] = true
                it[THEME_PREFERENCE_KEY] = true
            }
        }

        suspend fun removeAuthSession() {
            dataStore.edit {
                it.clear()
            }
        }

        companion object {
            private val USERID_KEY = stringPreferencesKey("user_id")
            private val EMAIL_KEY = stringPreferencesKey("email")
            private val DISPLAY_NAME_KEY = stringPreferencesKey("display_name")
            private val TOKEN_KEY = stringPreferencesKey("token_data")
            private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
            private val THEME_PREFERENCE_KEY = booleanPreferencesKey("theme_preferences")
        }
    }
