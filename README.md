# Introduction 
It doesn't matter what you're looking for a restaurant, an ATM, or anything else as long as it's listed online, Nearby can help you locate it. Nearby is an android app. Anyone looking to quickly and conveniently discover local establishments, such as eateries, ATMs, or other areas of interest, should use the Nearby application.

# Purpose
As I was studying android development, I wanted to check out the Google cloud Place API, so I created this application as part of my "experiments with android". In order to understand how to deal with APIs and link Google Cloud services with Android, I constructed a simple application.

# Implementation
## Tech Stack
    The Nearby Places application is developed using
    * Kotlin
    * XML
    * Google Cloud Place API. 
    * Fused Location Provider API.  
## Architecture
The Nearby Places application follows a standard Android app architecture, with a single main activity and map fragments for displaying search results on map.
## Key design decisions
Some of the key design decisions made during the development of the Nearby Places application include the use of the Google Cloud Place API for location search and the implementation of a map view to display search results.

# Features
## Keyword search
The Nearby Places application allows users to search for nearby locations by entering a keyword, such as "Restaurants", "ATMs"...
## Map view
The Nearby Places application displays search results on a map, with markers for each location.
## Location details
Users can view details about a specific location, including its name and can make directions.
   
# Working
<img src="https://user-images.githubusercontent.com/59611699/205653115-07a34ed3-2fb8-4a11-80c7-fdfd54367474.png">
Technically, the application will aquire GPS Location, through Fused Location Provider API and shows the location on the map view. when the user types an keyword and click on the button then this process will be executed -

1. Current Location along with the searched Keyword is send to the Google Place API
2. The Place API will analyse the input parameters and will generate an appropirate result
3. Place API will return JSON data containing of locations near the user 
4. Map will be updated with the Markers on the result locations

# How to use
If you want to play around with what I've done, you may download and run it on your Android device. Please feel free to use the code as well. You only need a basic understanding of how to interact with APIs to begin working on something similar or to advance my programe check out [Contribute](#contribute) for more details

1. Open the Application
2. Wait for Aquiring your Location
3. Search for any keyword like atm, restaurants, hotels...
4. Access Your Near By Locations

<p float="left" align="center">

  <img src="https://user-images.githubusercontent.com/59611699/209367292-074457f5-340e-4c51-9eac-4f9a83bcfd3b.png" width="300" />
  <img src="https://user-images.githubusercontent.com/59611699/209367308-b0cd2ea0-2e5b-40dd-bd87-9242ee8a532d.png" width="300" />
  
  <br/>  
  
  <img src="https://user-images.githubusercontent.com/59611699/209367314-032a01fb-fa1c-40db-bf82-22133cd76869.png" width="300"  />
  <img src="https://user-images.githubusercontent.com/59611699/209367321-f2f0b406-0347-4c2f-a49c-b0601b70eecb.png" width="300"  />

</p>

# Contribute

## Prerequisite
There are the Prerequisite you required to contribute to this project
* Kotlin
* Working with JSON Data
* Knowledge on using API's

## Getting Started
1. Fork the Repo
2. Download the Zip File 
3. Add the project to your Android Studio from the extracted Files
4. Add your API key in the local.properties file in the name API_KEY
5. Solve the Issue 😉
6. Push the changes to your GitHub 
7. Open the Pull Request

