# HasBikeRack

*HasBikeRack* is an application written to solve two issues I have atm:

+ Learning Kotlin and Jetpack Compose 🤓
+ Trying to track and jot down places in Curitiba that actually have Bicycle Parking Racks I can use 🚴‍♀️

## Project structure

`app/src/main`: The app itself (UI + Logic).

`app/src/test`: Unit and Integration Tests.

`app/src/androidTest`: UI Tests based on Android

## Building and Testing

Check [the build-test GitHub action](.github/workflows/build-and-test.yml) for a step-by-step on how to build and test, assuming you're on a linux machine.

But for those looking for the requirements itself:

+ Android SDK has to be installed and properly configured
+ Either Gradle in your machine's path or use an IDE that bundles it within

Assuming you have those right, just do a `./gradlew build` and ggwp (Good game, well played).

For testing you'll want to run a `./gradlew test`.