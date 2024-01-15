# Save that Song! 

## What is it?

This project stems from my experience listening to the radio and wanting to easily be able to save nice songs that I hear.
Often because of laziness and sometimes because of other difficulties (for example, while driving the car), it's not possible
to write down the song you're hearing and would like to keep track of.

This is where Save that Song! comes into play. When you want to save a song currently playing on a supported input (see list below),
all you need to do is make an API call to the application and it will automatically figure out what's playing and will persist it
"somewhere" (see list below).

## Supported actions

### From actions

At the moment, the only supported "From" input is `RADIO`. Thus, if you're listening to a specific radio and want to save
the song that's playing, send a request containing the stream URL of the radio you're listening to and Save that Song! will
do the rest

I'm planning on supporting `SONOS` as well, for which you'd provide the Sonos system you're currently listening on, this is
being tracked in [issue #22](https://github.com/Migwel/SaveThatSong/issues/22).

### To actions

The following To actions are currently supported:

* `DATABASE`: this simply persists the song in the database. This is the most reliable method as it will (almost?) always succeed
* `SPOTIFY`: this adds the song to the **Liked Songs** playlist on Spotify. This may not always work as it can happen that
             the song cannot be found on Spotify

At the moment, only one To action can be specific in the save request but [it will probably change in the future](https://github.com/Migwel/SaveThatSong/issues/24)

## Status

The basic functionality is there and works locally. The main next steps are:
* Support Sonos as a From action
* Build better user management (at the moment, user need to be directly added to the db...)
* Build some kind of UI

Next to that, I'll need to think about whether other inputs or actions could be added, all suggestions are welcome (open an issue with your idea)

## How to build and run

To build, execute 
```
mvn verify
```
Once this succeeded, you can run the application by executing
```
java -jar target/SaveThatSong-0.0.1-SNAPSHOT.jar
```