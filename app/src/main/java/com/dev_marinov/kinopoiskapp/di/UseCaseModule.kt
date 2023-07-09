package com.dev_marinov.kinopoiskapp.di

import com.dev_marinov.kinopoiskapp.domain.repository.*
import com.dev_marinov.kinopoiskapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetVotesUseCase(votesRepository: VotesRepository): GetVotesUseCase {
        return GetVotesUseCase(votesRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteMovieUseCase(favoriteRepository: FavoriteRepository): GetFavoriteUseCase {
        return GetFavoriteUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideGetRatingUseCase(ratingRepository: RatingRepository): GetRatingUseCase {
        return GetRatingUseCase(ratingRepository)
    }

    @Provides
    @Singleton
    fun provideGetVideosUseCase(videosRepository: VideosRepository): GetVideosUseCase {
        return GetVideosUseCase(videosRepository)
    }

    @Provides
    @Singleton
    fun provideGetGenresUseCase(genresRepository: GenresRepository): GetGenresUseCase {
        return GetGenresUseCase(genresRepository)
    }

    @Provides
    @Singleton
    fun provideGetReleaseYearUseCase(releaseYearRepository: ReleaseYearRepository): GetReleaseYearUseCase {
        return GetReleaseYearUseCase(releaseYearRepository)
    }

    @Provides
    @Singleton
    fun provideGetPersonsUseCase(personsRepository: PersonsRepository): GetPersonsUseCase {
        return GetPersonsUseCase(personsRepository)
    }

    @Provides
    @Singleton
    fun provideGetPostersUseCase(posterRepository: PosterRepository): GetPostersUseCase {
        return GetPostersUseCase(posterRepository)
    }

    @Provides
    @Singleton
    fun provideGetLottieAnimationUseCase(lottieAnimationRepository: LottieAnimationRepository): GetLottieAnimationUseCase {
        return GetLottieAnimationUseCase(lottieAnimationRepository)
    }

}