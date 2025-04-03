package com.example.projectse104.di

import com.example.projectse104.data.repository.ConversationRepositoryImpl
import com.example.projectse104.data.repository.MessageRepositoryImpl
import com.example.projectse104.data.repository.RideOfferRepositoryImpl
import com.example.projectse104.data.repository.RideRepositoryImpl
import com.example.projectse104.data.repository.UserRepositoryImpl
import com.example.projectse104.domain.repository.ConversationRepository
import com.example.projectse104.domain.repository.MessageRepository
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.realtime

const val USER = "User"
const val RIDE = "Ride"
const val RIDE_OFFER = "RideOffer"
const val CONVERSATION = "Conversation"
const val MESSAGE = "Message"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideSupabaseClient() = createSupabaseClient(
        supabaseUrl = "https://ouanezsrnbseuupwngww.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im91YW5lenNybmJzZXV1cHduZ3d3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM0MzIwMTcsImV4cCI6MjA1OTAwODAxN30.ovsvvFYTUUM2f6HkrjKC2qhHS2IRUeH6TUiGTfsOEAg"
    ) {
        install(Postgrest)
        install(Realtime)
    }

    @Provides
    fun provideUserRepository(
        db: SupabaseClient
    ): UserRepository = UserRepositoryImpl(
        usersRef = db.from(USER)
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
        conversationsRef = db.from(CONVERSATION)
    )

    @Provides
    fun provideMessageRepository(
        db: SupabaseClient
    ): MessageRepository = MessageRepositoryImpl(
        messagesRef = db.from(MESSAGE)
    )
}