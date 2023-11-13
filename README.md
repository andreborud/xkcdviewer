# The Coding Challenge

The challenge was to build a [xkcd comics](https://xkcd.com/) [MVP](https://en.wikipedia.org/wiki/Minimum_viable_product) app.

The client had a long list of requests but for a first MVP I have opted to not implement all of them as they would add more complexity to an initial simple app. What I have implemented is:

- browse through the comics,
- see the comic details, including its description,
- search for comics by the comic number ~~as well as text~~,
- get the comic explanation
- favorite the comics, which would be available offline too,
- send comics to others,
- ~~get notifications when a new comic is published~~,
- support multiple form factors.

I decided to skip search by text as the xkcd search didn't work, I could make it work by creating a small server app which could scrape the content of xkcd and provide a search api for. Same goes for the notifications for new comics as I would need a backend to know when new comics have been added and in that case send a notification. But even so, the work needed to add push notification to app and server would have added a significant amount of time to the project. 

When developing the project I opted for a modular version of Clean Architecture with library modules for __data__ & __domain__, while the UI part stays in the main module. I also added a __common__ module for models and possibly other code shared between the modules. I have added some unit and integration tests for the different modules. 
I made the project modular as if it would grow in the future its ready for the advantages modularity gives. For an even bigger project I would use feature modules to make all feature independent of each other to encapsulate the code better. In the main module I opted for the MVI pattern as I find it very clean. And I used old school XML for this project instead oj Jetpack Compose as I'm still faster at prototyping an app in XML, but I'm working on changing that. 

## Libraries used

For dependency injection I used Hilt, I'm more used to dagger but thought I would try hilt. Which I'm using with KSP instead of KAPT, which is currently in alpha together with hilt, but I thought it could be fun to try it together, but I would not put something in alpha in production.

For api calls I used Retrofit with Moshi. Moshi because I never tried it and it much more modern and adapted for kotlin in comparison to GSON which I'm more used to. 

For downloading images I decided to try Picasso to stay in the square family. But I normally use Glide. 

To store comics offline I used Room, in case the project would grow it better to be prepared with a proper database.

I also used the navigation component as it's faster to implement that for handling the navigation between the sections of the app with less code.

