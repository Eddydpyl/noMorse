# noMorse [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Build Status](https://travis-ci.org/Eddydpyl/noMorse.svg?branch=master)](https://travis-ci.org/Eddydpyl/noMorse) ![Status: abandoned](https://img.shields.io/badge/status-abandoned-red.svg)
Doesn't it bother you when someone starts sending text messages, one after another, making your phone ring or vibrate like it's gone mad? I keep my App notifications on because I do want to be notified when someone writes to me. What I don't particularly enjoy is being informed of every single word they decide to type and immediately send, it's as if they were talking to me in Morse code! I don't really have anything against bite-sized text messages, but this behavior many (if not all) messaging Apps have really gets on my nerves.

This open-source project aims to fix the problem by instructing its users on how to turn off sound and vibration for their favorite messaging Apps, so that we can handle that side of the notification and put an end to Morse code!

<p align="center"><img src="/app/src/main/res/raw/logo_without_backbround.png" alt="Logo" width="250"/></p>

## Contribute to the project
Right now the biggest issue is the unpredictable behaviour of our [MessageListenerService](https://github.com/Eddydpyl/noMorse/blob/master/app/src/main/java/dpyl/eddy/nomorse/controller/MessageListenerService.java) due to how Android handles services (I assume Android stops the service every now and then, meaning a notification may arrive and the App won't work). If anyone could figure out a better implementation, that would be enourmously helpful. Also, the logic behind deciding when to vibrate or play a sound could probably be tuned up a bit.

### Support for other messaging Apps
If you'd like to help in adding support for other Apps, while respecting the current project structure, follow these steps:
1. Create a new AppCompatActivity named after the messaging App and make it use a PreferenceFragment inside.
2. Add your new AppCompatActivity to the [SettingAdapter](https://github.com/Eddydpyl/noMorse/blob/master/app/src/main/java/dpyl/eddy/nomorse/view/adapter/SettingAdapter.java).
3. Create a Controller singleton Class for the new App. The logic for deciding wether to vibrate, or play a sound, when receiving a notification goes in here. The user preferences are checked inside [MessageListenerService](https://github.com/Eddydpyl/noMorse/blob/master/app/src/main/java/dpyl/eddy/nomorse/controller/MessageListenerService.java) though.
4. Instantiate the Controller inside [MessageListenerService](https://github.com/Eddydpyl/noMorse/blob/master/app/src/main/java/dpyl/eddy/nomorse/controller/MessageListenerService.java) and make use of the onNotificationPosted() method. To check if the notification comes from the new App we are adding support for, you should know its package name.
