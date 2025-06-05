package com.example.projectse104.di

import android.content.Context
import com.example.projectse104.data.repository.ConversationRepositoryImpl
import com.example.projectse104.data.repository.LocationRepositoryImpl
import com.example.projectse104.data.repository.MessageRepositoryImpl
import com.example.projectse104.data.repository.RideOfferRepositoryImpl
import com.example.projectse104.data.repository.RideRepositoryImpl
import com.example.projectse104.data.repository.UserFavouriteRiderRepositoryImpl
import com.example.projectse104.data.repository.UserLocationRepositoryImpl
import com.example.projectse104.data.repository.UserLoginImpl
import com.example.projectse104.data.repository.UserRepositoryImpl
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.UserFavouriteRiderRepository
import com.example.projectse104.domain.repository.UserLocationRepository
import com.example.projectse104.domain.repository.UserLogin
import com.example.projectse104.domain.repository.UserRepository
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import com.example.projectse104.domain.use_case.validation.ValidateLocation
import com.example.projectse104.domain.use_case.validation.ValidatePhoneNumber
import com.example.projectse104.domain.use_case.validation.ValidateConfirmPassword
import com.example.projectse104.domain.use_case.validation.ValidatePassword
import com.example.projectse104.utils.DataStoreSessionManager
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.postgrest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

const val USER = "User"
const val RIDE = "Ride"
const val RIDE_OFFER = "RideOffer"
const val CONVERSATION = "Conversation"
const val MESSAGE = "Message"
const val USER_FAVOURITE_RIDER = "UserFavouriteRider"
const val USER_LOCATION = "UserLocation"
const val LOCATION = "Location"

val json = Json {
    encodeDefaults = true
    useAlternativeNames = false // Disable snake_case conversion
    // Alternatively: namingStrategy = JsonNamingStrategy.BuiltIn.CamelCase
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://ouanezsrnbseuupwngww.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im91YW5lenNybmJzZXV1cHduZ3d3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM0MzIwMTcsImV4cCI6MjA1OTAwODAxN30.ovsvvFYTUUM2f6HkrjKC2qhHS2IRUeH6TUiGTfsOEAg"
        ) {
            install(Postgrest) {
                serializer = KotlinXSerializer(json)
            }
            install(Auth)
            install(Realtime)
        }
    }

    @Provides
    fun provideUserRepository(
        db: SupabaseClient
    ): UserRepository = UserRepositoryImpl(
        usersRef = db.from(USER),
    )

    @Provides
    fun provideRideRepository(
        db: SupabaseClient
    ): RideRepository = RideRepositoryImpl(
        ridesRef = db.from(RIDE),
        rideChannel = db.realtime.channel("ride")
    )

    @Provides
    fun provideRideOfferRepository(
        db: SupabaseClient
    ): RideOfferRepository = RideOfferRepositoryImpl(
        rideOffersRef = db.from(RIDE_OFFER)
    )

    @Provides
    fun provideConversationRepository(
        db: SupabaseClient
    ): ConversationRepository = ConversationRepositoryImpl(
        conversationsRef = db.from(CONVERSATION),
        realtimeChannel = db.channel("schema-db-changes")
    )

    @Provides
    fun provideMessageRepository(
        db: SupabaseClient
    ): MessageRepository = MessageRepositoryImpl(
        messagesRef = db.from(MESSAGE)
    )

    @Provides
    fun provideUserFavouriteRiderRepository(
        db: SupabaseClient
    ): UserFavouriteRiderRepository = UserFavouriteRiderRepositoryImpl(
        userFavouriteRiderRef = db.from(USER_FAVOURITE_RIDER)
    )

    @Provides
    fun provideUserLocationRepository(
        db: SupabaseClient
    ): UserLocationRepository = UserLocationRepositoryImpl(
        userLocationRef = db.from(USER_LOCATION)
    )

    @Provides
    fun provideLocationRepository(
        db: SupabaseClient
    ): LocationRepository = LocationRepositoryImpl(
        locationRef = db.from(LOCATION)
    )

    @Provides
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    fun provideValidateFullName(): ValidateFullName {
        return ValidateFullName()
    }

    @Provides
    fun provideValidateLocation(): ValidateLocation {
        return ValidateLocation()
    }

    @Provides
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    fun provideValidateConfirmPassword(): ValidateConfirmPassword {
        return ValidateConfirmPassword()
    }

    @Provides
    fun provideValidatePhoneNumber(): ValidatePhoneNumber {
        return ValidatePhoneNumber()
    }

    @Provides
    fun provideUserLogin(impl: UserLoginImpl): UserLogin {
        return impl
    }

    @Provides
    fun providePostgrestQueryBuilder(client: SupabaseClient): PostgrestQueryBuilder {
        return client.postgrest[USER]
    }

    @Provides
    fun provideDataStoreSessionManager(@ApplicationContext context: Context): DataStoreSessionManager {
        return DataStoreSessionManager(context)
    }

}