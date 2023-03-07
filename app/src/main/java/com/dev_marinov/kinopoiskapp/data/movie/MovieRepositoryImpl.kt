package com.dev_marinov.kinopoiskapp.data.movie

import com.dev_marinov.kinopoiskapp.data.movie.local.MovieDao
import com.dev_marinov.kinopoiskapp.data.movie.local.MovieEntity
import com.dev_marinov.kinopoiskapp.data.movie.remote.ApiService
import com.dev_marinov.kinopoiskapp.data.movie.remote.MovieDTO
import com.dev_marinov.kinopoiskapp.data.poster.remote.PosterDTO
import com.dev_marinov.kinopoiskapp.data.rating.remote.RatingDTO
import com.dev_marinov.kinopoiskapp.data.releaseYear.remote.ReleaseYearDTO
import com.dev_marinov.kinopoiskapp.data.votes.remote.VotesDTO
import com.dev_marinov.kinopoiskapp.domain.model.Movie
import com.dev_marinov.kinopoiskapp.domain.repository.MovieRepository
import com.dev_marinov.kinopoiskapp.domain.repository.RepositoryCoordinator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
    private val localDataSource: MovieDao,
    private val mediator: RepositoryCoordinator
) : MovieRepository {

    override val movies: Flow<List<Movie>> = localDataSource.getAllFlow().map {
        it.map { entity -> entity.mapToDomain() }
    }

    //    TODO: uncomment search params
    override suspend fun updateMovies(
        searchRating: String,
        searchDate: String,
        searchTypeNumber: String,
        sortTypeDate: String,
        sortTypRating: String,
        page: String,
        limit: String
    ) {

        // если данных по сети нет
        val response = remoteDataSource.getData().body() ?: return // TODO: uncomment this line



        val movieDtos = response.movies // TODO: uncomment this line
       // val movieDtos = getData() // TODO: delete this line
        movieDtos.forEach { dto ->
            val movie = dto.mapToDomain(response.page) // TODO: uncomment this line
           // val movie = dto.mapToDomain(1) // TODO: delete this line
            val releaseYears = dto.releaseYears?.map {
                it.mapToDomain(movie.id)
            } ?: listOf()

            val votes = dto.votes?.mapToDomain(movie.id)
            val rating = dto.rating?.mapToDomain(movie.id)
            val poster = dto.poster?.mapToDomain(movie.id)
            mediator.saveData(movie, releaseYears, votes, rating, poster)
        }
    }

    override suspend fun getMovie(movieId: String?): Movie {
        return localDataSource.getMovieForDetail(id = movieId).mapToDomain()
    }

    override suspend fun deleteMovie(movie: Movie) {
        localDataSource.delete(MovieEntity.mapFromDomain(movie))
    }

    /**
     *  Mock function. I can not load data from server. So use ApiService.getData() instead this.
     */
    private fun getData(): List<MovieDTO> {
        return listOf(
            MovieDTO(
                id = 1046206,
                alternativeName = "La casa de papel",
                description = "История о преступниках, решивших ограбить Королевский монетный двор Испании и украсть 2,4 млрд евро.",
                length = 50,
                name = "Бумажный дом",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359ae5b9f6a011dbf62c610", start = 2017, end = 2021)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6dcc168d824d6cae8cdbf"
                ),
                rating = RatingDTO(
                    kp = 8.141,
                    imdb = 8.2,
                    filmCritics = 0,
                    russianFilmCritics = 0.0,
                    await = 0.0,
                    id = "63e6dcc168d824d6cae8cdbe"
                ),
                poster = PosterDTO(
                    id = "63397818c22d011bb569fd77",
                    url = "https://st.kp.yandex.net/images/film_big/1046206.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_1046206.jpg"
                )
            ),
            MovieDTO(
                id = 1032606,
                alternativeName = "La casa de papel",
                description = "История о преступниках, решивших ограбить Королевский монетный двор Испании и украсть 2,4 млрд евро.",
                length = 60,
                name = "Тьма",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359af009f6a011dbf63878c", start = 2017, end = 2020)
                ),
                votes = VotesDTO(
                    kp = 137993,
                    imdb = 398184,
                    filmCritics = 0,
                    russianFilmCritics = 10,
                    await = 216,
                    id = "63e6e72468d824d6ca53626d"
                ),
                rating = RatingDTO(
                    kp = 8.11,
                    imdb = 8.7,
                    filmCritics = 0,
                    russianFilmCritics = 90.0,
                    await = 100.0,
                    id = "63e6e72468d824d6ca53626c"
                ),
                poster = PosterDTO(
                    id = "633979ecc22d011bb572a35b",
                    url = "https://st.kp.yandex.net/images/film_big/1032606.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_1032606.jpg"
                )
            ),
            MovieDTO(
                id = 1045553,
                alternativeName = "Ozark",
                description = "Финансовый консультант Марти Бёрд вместе с женой Вэнди и остальными членами семьи вынужден тайно переехать из престижного предместья Чикаго в курортный городок Озарк штата Миссури. К неожиданному переезду главного героя вынудили крупные долги, и на новом месте он надеется поправить своё финансовое положение.",
                length = 60,
                name = "Озарк",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6361476611f1fd7ff1faa29e", start = 2017, end = 2022)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6ebbd68d824d6ca8f5b62"
                ),
                rating = RatingDTO(
                    kp = 7.921,
                    imdb = 8.5,
                    filmCritics = 0,
                    russianFilmCritics = 100.0,
                    await = 100.0,
                    id = "63e6ebbd68d824d6ca8f5b61"
                ),
                poster = PosterDTO(
                    id = "63397956c22d011bb56f7980",
                    url = "https://st.kp.yandex.net/images/film_big/1045553.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_1045553.jpg"
                )
            ),
            MovieDTO(
                id = 582358,
                alternativeName = "13 Reasons Why",
                description = "Однажды Клэй Дженсен находит на пороге своего дома коробку с аудиокассетами, записанными Ханной Бейкер. Он был влюблен в эту девушку в школе, пока она однажды не покончила жизнь самоубийством. В своих записях Ханна указала 13 причин, которые толкнули её на это. И Клэй - одна из них.",
                length = 50,
                name = "13 причин почему",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359af949f6a011dbf6437d2", start = 2017, end = 2021)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6f05968d824d6cad22327"
                ),
                rating = RatingDTO(
                    kp = 7.323,
                    imdb = 7.5,
                    filmCritics = 0,
                    russianFilmCritics = 0.0,
                    await = 97.69,
                    id = "63d813d9f38219e94f4b8029"
                ),
                poster = PosterDTO(
                    id = "63397a6ec22d011bb5759af1",
                    url = "https://st.kp.yandex.net/images/film_big/582358.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_582358.jpg"
                )
            ),
            MovieDTO(
                id = 958500,
                alternativeName = "Mindhunter",
                description = "Конец 1970-х. Два агента ФБР опрашивают находящихся в заключении серийных убийц с целью понимания их образа мыслей, а также раскрытия текущих преступлений.",
                length = 60,
                name = "Охотник за разумом",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6361475111f1fd7ff1f94025", start = 2017, end = 2019)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6ea8968d824d6ca812298"
                ),
                rating = RatingDTO(
                    kp = 8.107,
                    imdb = 8.6,
                    filmCritics = 0,
                    russianFilmCritics = 100.0,
                    await = 99.12,
                    id = "63d04fd657101ffd39bc3594"
                ),
                poster = PosterDTO(
                    id = "63397866c22d011bb56b57d5",
                    url = "https://st.kp.yandex.net/images/film_big/958500.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_958500.jpg"
                )
            ),
            MovieDTO(
                id = 1007426,
                alternativeName = "The Handmaid's Tale",
                description = "Действие разворачивается в будущем, в республике Гилеад, где у власти стоят военные. В стране жестокие порядки и нравы, уважением в обществе пользуются только офицеры и их жёны. Помимо тоталитарного устройства общества, в мире будущего есть серьёзная проблема — бесплодие. Лишь каждая сотая женщина способна к воспроизведению потомства. Чтобы продолжить офицерский род, семьи берут в дом служанку - суррогатную мать из числа простых женщин, способных к деторождению. Исполнив  долг, служанка должна расстаться со своим ребёнком и перейти на службу к новым хозяевам.",
                length = 50,
                name = "Рассказ служанки",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359aea29f6a011dbf6315ac", start = 2017, end = null)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6e61468d824d6ca474824"
                ),
                rating = RatingDTO(
                    kp = 7.95,
                    imdb = 8.4,
                    filmCritics = 0,
                    russianFilmCritics = 57.1429,
                    await = 97.85,
                    id = "63d987e7f38219e94f8d6c3e"
                ),
                poster = PosterDTO(
                    id = "636a81a45aba03d332f8062f",
                    url = "https://st.kp.yandex.net/images/film_big/1007426.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_1007426.jpg"
                )
            ),
            MovieDTO(
                id = 978853,
                alternativeName = "The Punisher",
                description = "После того, как семья Фрэнка Касла была убита во время перестрелки между бандами Нью-Йорка, он решает отомстить и начинает охоту на преступников города. В криминальном мире он становится известен как Каратель.",
                length = 53,
                name = "Каратель",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6361478011f1fd7ff1fc2e17", start = 2017, end = 2019)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6e7d068d824d6ca5c4f0a"
                ),
                rating = RatingDTO(
                    kp = 8.141,
                    imdb = 8.2,
                    filmCritics = 0,
                    russianFilmCritics = 0.0,
                    await = 0.0,
                    id = "63e6e7d068d824d6ca5c4f09"
                ),
                poster = PosterDTO(
                    id = "63397a73c22d011bb575b615",
                    url = "https://st.kp.yandex.net/images/film_big/978853.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_978853.jpg"
                )
            ),
            MovieDTO(
                id = 1071384,
                alternativeName = "The End of the F***ing World",
                description = "Джеймсу семнадцать и он уверен, что он - психопат. Алисе тоже семнадцать, она - капризная новенькая девчонка в школе. Они с пол-оборота заводят подростковый роман, и Алиса уговаривает Джеймса начать поиски её отца. Джеймсу от Алисы тоже кое-что нужно.",
                length = 25,
                name = "Конец ***го мира",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359af919f6a011dbf643257", start = 2017, end = 2019)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6e0d568d824d6ca0f6615"
                ),
                rating = RatingDTO(
                    kp = 8.141,
                    imdb = 8.2,
                    filmCritics = 0,
                    russianFilmCritics = 0.0,
                    await = 0.0,
                    id = "63d447bb57101ffd391f0157"
                ),
                poster = PosterDTO(
                    id = "63397c2ac22d011bb57f4978",
                    url = "https://st.kp.yandex.net/images/film_big/1071384.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_1071384.jpg"
                )
            ),
            MovieDTO(
                id = 932068,
                alternativeName = "Taboo",
                description = "Искатель приключений Джеймс Кезайя Делейни строит свою собственную корабельную империю в начале XIX века.",
                length = 59,
                name = "Табу",
                shortDescription = null,
                type = "tv-series",
                year = 2017,
                releaseYears = listOf(
                    ReleaseYearDTO(id = "6359af4d9f6a011dbf63de5a", start = 2017, end = 2017)
                ),
                votes = VotesDTO(
                    kp = 206987,
                    imdb = 485046,
                    filmCritics = 0,
                    russianFilmCritics = 1,
                    await = 0,
                    id = "63e6f00468d824d6cacce30b"
                ),
                rating = RatingDTO(
                    kp = 8.141,
                    imdb = 8.2,
                    filmCritics = 0,
                    russianFilmCritics = 0.0,
                    await = 0.0,
                    id = "63e6f00468d824d6cacce30a"
                ),
                poster = PosterDTO(
                    id = "633978bbc22d011bb56c9f83",
                    url = "https://st.kp.yandex.net/images/film_big/932068.jpg",
                    previewUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_932068.jpg"
                )
            )
        )

    }
}