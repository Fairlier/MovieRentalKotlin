package com.example.movierentalkotlin.util

object Constants {

    const val INSERT_TEXT = "Заполните все поля перед добавлением записи."

    object Role {
        const val ADMINISTRATOR = "Администратор"
    }

    enum class FragmentSource {
        INSERT_CLIENT_MOVIE_RATING,
        SEARCH_CLIENT_MOVIE_RATING,
        EDIT_CLIENT_MOVIE_RATING,
        INSERT_MOVIE_RENTAL,
        SEARCH_MOVIE_RENTAL,
        EDIT_MOVIE_RENTAL
    }

    const val DATABASE_NAME: String = "movie_rental"

    object Movie {
        const val TABLE_NAME: String = "movies"
        const val ID: String = "id"
        const val TITLE: String = "title"
        const val RELEASE_YEAR: String = "release_year"
        const val DIRECTOR: String = "director"
        const val COUNTRY: String = "country"
        const val DURATION: String = "duration"
        const val RENTAL_COST: String = "rental_cost"
        const val AVERAGE_RATING: String = "average_rating"
        const val DESCRIPTION: String = "description"
        const val IMAGE_URL: String = "image_url"
    }

    object Employee {
        const val TABLE_NAME: String = "employees"
        const val ID: String = "id"
        const val FULL_NAME: String = "full_name"
        const val DATE_OF_BIRTH: String = "date_of_birth"
        const val ADDRESS: String = "address"
        const val PHONE_NUMBER: String = "phone_number"
        const val DATE_OF_HIRE: String = "date_of_hire"
        const val DATE_OF_DISMISSAL: String = "date_of_dismissal"
        const val SALARY: String = "salary"
        const val IMAGE_URL: String = "image_url"
    }

    object Client {
        const val TABLE_NAME: String = "clients"
        const val ID: String = "id"
        const val FULL_NAME: String = "full_name"
        const val DATE_OF_BIRTH: String = "date_of_birth"
        const val ADDRESS: String = "address"
        const val PHONE_NUMBER: String = "phone_number"
        const val DATE_OF_REGISTRATION: String = "date_of_registration"
        const val IMAGE_URL: String = "image_url"
    }

    object MovieRental {
        const val TABLE_NAME: String = "movie_rentals"
        const val ID: String = "id"
        const val CLIENT_ID: String = "client_id"
        const val EMPLOYEE_ID: String = "employee_id"
        const val MOVIE_ID: String = "movie_id"
        const val DATE_OF_RECEIPT: String = "date_of_receipt"
        const val DATE_OF_RETURN: String = "date_of_return"
    }

    object ClientMovieRating {
        const val TABLE_NAME: String = "client_movie_ratings"
        const val ID: String = "id"
        const val CLIENT_ID: String = "client_id"
        const val MOVIE_ID: String = "movie_id"
        const val RATING: String = "rating"
        const val COMMENT: String = "comment"
    }
}