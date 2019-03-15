_This project was built with love and sweat by yours truly as part of the Android Developer Nanodegree by Udacity._

_You are more than welcome to look at it for inspiration and use it as per the MIT Licence included._

_However, if you feel like copying/pasting it and submitting it as your own work, remember that plagiarism is a violation of the Udacity Honor Code. The consequences of such act may include your expulsion from the ND program (without refund) and could go as far as having you banned for life from any Udacity course and/or scholarship offered in partnership with Udacity._
_Udacity can also revoke your graduation credential at anytime if plagiarism is detected after you graduate._

_Your call._

---

# MovieNight
Projects #1 and #2 of the [Android Developer](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801) Nanodegree by Udacity.

## Project Overview
Build an app to allow users to discover the most popular or the highest-rated movies playing. The development of this app is split in two stages:
- **Stage 1**
  * Present the user with a grid arrangement of movie posters upon launch.
  * Allow your user to change sort order via a setting. The sort order can be by most popular or by highest-rated
  * Allow the user to tap on a movie poster and transition to a details screen with additional information such as: original title, movie poster image thumbnail, a plot synopsis (called `overview` in the api), user rating (called `vote_average` in the api), release date
- **Stage 2**
  * Allow users to view and play trailers (either in the YouTube app or a web browser).
  * Allow users to read reviews of a selected movie.
  * Allow users to mark a movie as a favorite in the details view by tapping a button(star).
  * Create a database to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
  * Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
  
## Completed Project - MovieNight
![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/PopularSMComp.jpg) ![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/PopLandSMComp.jpg) ![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/NavPortSMComp.jpg)  ![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/DetailsSMComp.jpg)  ![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/ReviewsMinSMComp.jpg)  ![](https://github.com/ellemwano/android-MovieNight/blob/master/pics/ReviewsFullSMComp.jpg)

- **Android Framework**
  * HttpURLConnection
  * JSON Parsing from REST API ([TheMovieDB](https://www.themoviedb.org/))
  * RecyclerView
  * ContentProvider
  * SQLite
  * Loaders
  * CursorLoader
  * AsyncTaskLoader
- **UI**
  * ConstraintLayout
  * Navigation Drawer
  * FAB
  * Expandable/Collapsible TextView	
- **Libraries**
  * [Picasso](https://square.github.io/picasso/) v.2.5.2

**Installation**
  * Get an API key (v3 auth) from [TheMovieDB](https://www.themoviedb.org/)
  * Enter your API key in the project's `gradle.properties` file as such: `API_KEY="(your API key)"`

## Why this Project
To become an Android developer, you must know how to bring particular mobile experiences to life. Specifically, you need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices. You will hone these fundamental skills in this project.

By building this app, you will demonstrate your understanding of the foundational elements of programming for Android. Your app will communicate with the Internet and provide a responsive and delightful user experience.

## Learning Objectives
- Fetch data from the Internet with theMovieDB API.
- Use adapters and custom list layouts to populate list views.
- Incorporate libraries to simplify the amount of code you need to write
- Build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).

## Project Requirements
- **User Interface - Layout**
  * UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
  * Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
  * UI contains a screen for displaying the details for a selected movie.
  * Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
  * Movie Details layout contains a section for displaying trailer videos and user reviews.
- **User Interface - Function**
  * When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
  * When a movie poster thumbnail is selected, the movie details screen is launched.
  * When a trailer is selected, app uses an Intent to launch the trailer.
  * In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.
- **Network API Implementation**
  * In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
  * App requests for related videos for a selected movie via the `/movie/{id}/videos` endpoint in a background thread and displays those details when the user selects a movie.
  * App requests for user reviews for a selected movie via the `/movie/{id}/reviews` endpoint in a background thread and displays those details when the user selects a movie.
- **Data Persistence**
  * The titles and IDs of the user’s favorite movies are stored in a native SQLite database and exposed via a ContentProvider
  * Data is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.
  * When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.
