# pennapps

This repository holds our Android hackathon project for Penn Apps Winter 2015. Our project was named Footsies. For more information, please go to the following link: http://blog.cherryzhang.net/2015/03/developing-footsies-app-at-pennapps.html

To summarize, it is basically a rehabilitative app that uses this "sock sensor" that collects raw data (e.g. accelerometer and pressure sensors) to give you a diagnosis on any feet problems/gait problems you may have. It also has a preliminary rehabilitation program to improve your gait (if you have a bad one). It can also collect this data and send it to Parse, which can be easily viewable/sent to the patient's doctor.

# Improvement 1 (Filtering out the Noise from Sensors)
https://en.wikipedia.org/wiki/Low-pass_filter#Simple_infinite_impulse_response_filter

Because I was still a first year engineering student, I had some trouble with the design of how to interpret all of the raw data coming every time the sensor data updated. This is because in real life, there is noise in any signal, and of course people weigh differently/have different feet sizes, so we needed to somehow interpret the data for anyone and everyone. That is why we had a calibration system where the user needed to do the four classic gait poses, and then we save those numbers/data we get from the calibration data into persistent local app storage. However, sometimes there still may be noise.

In order to solve this noise issue, my electrical engineering friend taught me the concept of low-pass filters (which I will probably learn in my upper years of undergrad). More specifically, we can use the algorithm that is linked above in the wikipedia article:

So, if we have a stream of data (e.g. data from an accelerometer in the z-direction, let's just say), we can use the following simple algorithm:

for i from 1 to n
       y[i] := y[i-1] + α * (x[i] - y[i-1])

where y is the output data (the "modified"/"corrected" signal/accelerometer data) and x was the original raw data you got from the accelerometer sensor.

This blog post from raweng also details more about this:

http://www.raweng.com/blog/2013/05/28/applying-low-pass-filter-to-android-sensors-readings/

We can basically filter out all of the high frequency noise using this simple low-pass filter algorithm.

On a fun side note, one of my other friends from MIT mentioned that this exact same equation/algorithm is used when calculating the exponential moving average, which can be used in "stock-market math" to smooth out the curve.

Link about this stock market smoothing curve is here:
https://en.wikipedia.org/wiki/Moving_average#Exponential_moving_average

^^^ As you can see, it is literally the EXACT SAME equation! They even use the same symbol for the coefficient (alpha).

# Improvement 2 (Multiple Devices Screen Size UI Responsiveness)

I'm not going to link the ChallengePost link to our hackathon project, simply because one of our team members used screenshots of the app's "first run-time UI/guide" on his OnePlus One, which obviously made the text formatting onto the screen look a bit wonky, as our UI/xml coder mostly tried to optimize the UI for my Nexus 4, which has a smaller screen size. If this wasn't an actual hackathon, we would've totally accounted for multiple device-screen sizes.
