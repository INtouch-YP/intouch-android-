package care.intouch.app.account.data.dto.impl

import android.content.SharedPreferences
import care.intouch.app.account.data.dto.AccountModel
import care.intouch.app.account.domain.api.AccountLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AccountLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineScope: CoroutineScope,
) : AccountLocalDataSource, SharedPreferences.OnSharedPreferenceChangeListener {

    private val _accountUpdateFlow = MutableStateFlow<AccountModel?>(null)

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        coroutineScope.launch {
            _accountUpdateFlow.value = getAccountInformation()
        }
    }

    override suspend fun saveAccountInformation(account: AccountModel) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().putString(KEY, Json.encodeToString(account))
        }
    }

    override suspend fun getAccountInformation(): AccountModel? {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getString(KEY, null)?.let {
                Json.decodeFromString<AccountModel>(it)
            }
        }
    }

    override fun getAccountFlow(): Flow<AccountModel?> {
        return _accountUpdateFlow
    }

    override suspend fun hasAccount(): Boolean {
        return withContext(Dispatchers.IO) {
            sharedPreferences.contains(KEY)
        }
    }

    override suspend fun clearAccountInformation() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().remove(KEY)
            _accountUpdateFlow.emit(null)
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1 == KEY) {
            coroutineScope.launch {
                _accountUpdateFlow.update {
                    getAccountInformation()
                }
            }
        }
    }

    companion object {
        const val KEY = "KEY"
    }

}