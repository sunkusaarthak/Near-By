# What is it. 
Nearby is an android application that helps you find things that are near you, it might be a Restaurant or an ATM or anything else, as long as it is listed on the internet, Nearby will help you find it. Nearby application is intended for anyone who wants to quickly and easily find nearby locations, such as restaurants, ATMs, or other points of interest.

# Purpose
This application is a part of my 'experiments with android', as I was learning android development I wanted to check out the Google cloud Place API. So I built a basic application that helped me learn how to work withe APIs and integrate google cloud services with Android. 

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
If you intend to experiment with what I have done, feel free to download and run it on your android device. The code is also available feel free to use it too. You just need a little knowledge of how to work with APIs to start working on something similar or take my app ahead. 

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




