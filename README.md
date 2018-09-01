_This project was built with love and sweat by yours truly as part of the Android Developer Nanodegree by Udacity._

_You are more than welcome to look at it for inspiration and use it as per the MIT Licence included._

_However, if you feel like copying/pasting it and submitting it as your own work, remember that plagiarism is a violation of the Udacity Honor Code. The consequences of such act may include your expulsion from the ND program (without refund) and could go as far as having you banned for life from any Udacity course and/or scholarship offered in partnership with Udacity._
_Udacity can also revoke your graduation credential at anytime if plagiarism is detected after you graduate._

_Your call._

---

# P2-Popular-Movies

_Project 1 of Udacity's Android Developer Nanodegree_ 

## Project Overview
Build an app to allow users to discover the most popular movies playing. The development of this app is split in two stages. 
In this stage 1 I built the core experience of the movies app.

The app:

- Presents the user with a grid arrangement of movie posters upon launch.
- Allows the user to change sort order via a setting:
The sort order can be by most popular or by highest-rated
- Allows the user to tap on a movie poster and transition to a details screen with additional information such as:
    * original title
    * movie poster image thumbnail
    * A plot synopsis (called overview in the api)
    * user rating (called vote_average in the api)
    * release date

## Installation

- You will first need to apply for an API key (v3 auth) from [TheMovieDB](https://www.themoviedb.org/)
- You then need to enter your API key in the project's gradle.properties file:
    ```API_KEY="(your API key)"```
