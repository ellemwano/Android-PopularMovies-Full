package com.mwano.lauren.movies.model;

public class Review {
//    {
//        "id": 244786,
//            "page": 1,
//            "results": [
//        {
//            "author": "MJM",
//                "content": "DISGUSTING NONSENSE...\r\n\r\n*** This review may contain spoilers ***\r\n\r\nI find it very sad that so many people - including so-called professional reviewers - have rated this crap so highly. I did not walk out (although I was greatly tempted to do so) but saw it to the end. A total waste of time.\r\n\r\nHere's what might spoil it for you, should you believe the BS that's being spread around this stinking pile of excrement: It could have actually been OK if it hadn't been so laughably impossibly ridiculous. Perhaps if it had been set in the fifties or the forties when people had much less developed consciousness of human rights? But even so... \r\n\r\nI suppose the moral/lesson we are supposed to learn is... if you can't warp your students enough by abuse to force them to become great musicians then it is perfectly alright to discard or destroy them in the attempt. \r\n\r\nThis glorified tyrant and bully can himself only produce music at a grade one level and so because he cannot 'do' he 'teaches?'\r\n\r\nHe does not teach, he does not inspire; he withholds approval, negatively reinforces and rules by fear, and is feared rather than respected. I would have a difficult time to point to a single (pedagogical) scene in the film that had any merit whatsoever or was worth watching for any reason. Maybe I should say that its evident popularity may be evidence that we are truly living in the end times... ha! \r\n\r\nSee the film if you want to be current, but please decide for yourself from watching it and don't believe the hype about its 'genius' or 'brilliance.' It is not either of those things; it's a poorly written, sad joke. \r\n\r\nI would expect that those people who rate it so highly A) want to seem cool because 'it's about jazz' B) have never actually been in a teacher/student situation and therefore, can only imagine how its done C) see all the other positive reviews and so must follow the herd D) don't really know their ass from their elbow or E) thought the the actor had truly grown because in Spiderman he only yell, but it THIS one, he throws chairs.... or F) all of the above.\r\n\r\nSave your money or see something uplifting instead rather than this horseshit.",
//                "id": "54c82e03c3a36870ba000a3d",
//                "url": "https://www.themoviedb.org/review/54c82e03c3a36870ba000a3d"
//        },
//        {
//            "author": "Andres Gomez",
//                "content": "Fantastic movie with a good cast with an impressive Miles Teller and a yet even more impressive J.K. Simmons. Decent script, great directing, selection of the repertoire and performances.\r\n\r\nJust sit down, get a good audio system and enjoy one of the best movies of the 2010s.",
//                "id": "56ab260cc3a3681c54001f8a",
//                "url": "https://www.themoviedb.org/review/56ab260cc3a3681c54001f8a"
//        },
//        {
//            "author": "DanDare",
//                "content": "J K Simmons won an Oscar for Best Supporting Actor for his mean, bullying monster. Jazz teacher Terence Fletcher who abhors the words 'good job.'\r\n\r\nFletcher tells a tale about Charlie Parker and how doing a good job was not enough for him. Good job means mediocrity.\r\n\r\nYet Fletcher is a mediocre educator and his college seems not to have noticed this. Instead of being an inspiration his students fear him. He is a nasty tyrant and an incident from his past comes back to haunt him.\r\n\r\nMiles Teller plays Andrew Neiman the put upon jazz drummer in the Shaffer Conservatory in New York. Neiman is eager to impress Fletcher but nothing he can do is good enough and withstands all the effluent Fletcher throws at him until one day he snaps.\r\n\r\nDamien Chazelle unleashed an unethical monster in Fletcher with Neiman providing the film's heart but I am not convinced that this is a good film. The rest of the students in the class are silent to the abuse taking place right in front of them. They are young adults, not kids and they do nothing about it.",
//                "id": "58b352d5c3a368525400c173",
//                "url": "https://www.themoviedb.org/review/58b352d5c3a368525400c173"
//        },
//        {
//            "author": "mattwilde123",
//                "content": "When I sat down to watch this film, I didn't know what to expect. I am not usually a fan of films about musicians but this was a brilliant and tense masterpiece. The story is of a very ambitious boy named Andrew (played by Miles Teller) who is a music student in New York. He aspires to be noticed by a prestigious music teacher named Fletcher (played by J.K. Simmons). As his wish starts becoming a reality, he realises the brutality of this teacher who continually pushes him with questionable methods. Damien Chazelle's direction is almost of a tense boxing or war action drama in that it constantly uses the music of the drumming to build the tension whilst using violent and sharp editing whilst the jazz band play.\r\n\r\nChazelle's screenplay is very well written. It is filled with humour, tension and heartbreak. The way in which Fletcher switches during Andrew's first lesson is expertly done. Fletcher relentlessly insults Andrew in a very shocking way which really creates a huge sense of sympathy for Andrew's character as he struggles to respond. The insulting dialogue is very similar to Stanley Kubrick's 'Full Metal Jacket' in that it is both humourous and disturbingly distressing.\r\n\r\nThe performances in this film are remarkable. Miles Teller is a relative newcomer but takes to this character with so much depth and understanding. The drumming scenes look like incredible and torturous workouts and Teller really shows the pain and agony his character is going through to reach his dream. The chemistry between the two central characters is flawless. J.K. Simmons is perfect as the abusive music teacher. Simmons manages to combine Fletcher's distinguished persona with his terrifying unpredictability. Simmons reminds me of his character in Valve's 'Portal 2' in which he continuously spouts insults at the player in a darkly hilarious way.\r\n\r\nOverall, 'Whiplash' is a fascinating study of passion, ambition and love. The film asks questions about the morality of getting one's dreams and the acceptable methods of acquiring them. Full of amazing performances all around, and created with such intensity and spirit, my final rating for this film is 4.5 stars.\r\n\r\n★★★★½",
//                "id": "58c14edac3a368265b009826",
//                "url": "https://www.themoviedb.org/review/58c14edac3a368265b009826"
//        }
//  ],
//        "total_pages": 1,
//            "total_results": 4
//    }

//    https://api.themoviedb.org/3/movie/(movie_id)/reviews?api_key=(API_KEY)

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewUrl;

    public Review (String reviewId, String reviewAuthor, String reviewContent, String reviewUrl) {
        mReviewId = reviewId;
        mReviewAuthor = reviewAuthor;
        mReviewContent = reviewContent;
        mReviewUrl = reviewUrl;
    }

    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId (String reviewId) {
        mReviewId = reviewId;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public void setReviewAuthor (String reviewAuthor) {
        mReviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewUrl() {
        return mReviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        mReviewUrl = reviewUrl;
    }

    @Override
    public String toString() {
        return mReviewId + "--" + mReviewAuthor + "--" + mReviewContent + "--" + mReviewUrl + "--";
    }
}
