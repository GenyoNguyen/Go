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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

const val USERS = "users"
const val RIDES = "rides"
const val RIDE_OFFERS = "ride_offers"
const val CONVERSATIONS = "conversations"
const val MESSAGES = "messages"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideUserRepository(
        db: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(
        usersRef = db.collection(USERS)
    )

    @Provides
    fun provideRideRepository(
        db: FirebaseFirestore
    ): RideRepository = RideRepositoryImpl(
        ridesRef = db.collection(RIDES)
    )

    @Provides
    fun provideRideOfferRepository(
        db: FirebaseFirestore
    ): RideOfferRepository = RideOfferRepositoryImpl(
        rideOffersRef = db.collection(RIDE_OFFERS)
    )

    @Provides
    fun provideConversationRepository(
        db: FirebaseFirestore
    ): ConversationRepository = ConversationRepositoryImpl(
        conversationsRef = db.collection(CONVERSATIONS)
    )

    @Provides
    fun provideMessageRepository(
        db: FirebaseFirestore
    ): MessageRepository = MessageRepositoryImpl(
        messagesRef = db.collection(MESSAGES)
    )
}