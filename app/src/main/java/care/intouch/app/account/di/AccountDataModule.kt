package care.intouch.app.account.di

import care.intouch.app.account.data.dto.impl.AccountLocalDataSourceImpl
import care.intouch.app.account.data.dto.impl.AccountStateRepositoryImpl
import care.intouch.app.account.domain.api.AccountLocalDataSource
import care.intouch.app.account.domain.api.AccountStateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AccountDataModule {
    @Singleton
    @Binds
    fun bindAccountLocalDataSource(
        impl: AccountLocalDataSourceImpl,
    ): AccountLocalDataSource

    @Singleton
    @Binds
    fun bindAccountStateRepository(
        impl: AccountStateRepositoryImpl,
    ): AccountStateRepository
}