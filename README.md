Android application that uses The Movie Database (TMDB) API to render a
single screen. The screen should consist of a top bar and a horizontal tab row with vertical grids
as tabs.
• The top bar should contain a title (of your choosing).
• The horizontal tab row should be populated with all the genres available for movies -
https://developer.themoviedb.org/reference/genre-movie-list. The tab row should be
scrollable. Tapping on a genre tab indicator will switch the user to that tab.
• The vertical grid should be populated with the movies of its associated genre tab -
https://developer.themoviedb.org/reference/discover-movie. The grid should be
scrollable. The grid should contain two columns. The grid item should contain the
movie’s poster, title and year of release. The aspect ratio of the poster should be
preserved. To be able to display TMDB imagery, you must use -
https://developer.themoviedb.org/reference/configuration-details - in combination with
https://developer.themoviedb.org/reference/discover-movie
The application should be written in Kotlin, with the UI written using Jetpack Compose
