# SoundTracker - JAK

## Index

I. Live site  
II. Description  
III. Usage  
IV. Images  
V. Installation  
VI. CDNs used  
VII. APIs used  
VIII. Contributions  
IX. Challenges  
X. Successes  
XI. The Road Ahead  
XII. Acknowledgements  
XIII. License

## Live site:

Come visit our beautiful app at https://dev10-capstone-team7.azurewebsites.net/


## Description:

SoundTrack is an event-planning app geared towards technicians who need to track their organization's staff and inventory. 
The app allows administrators to manage inventory, staff and locations for the organization, and to use that information
to plan events, including what equipment or volunteers will be present at which events. The app makes sure that the user
can only check out equipment, locations, or staff members that are not already busy with another event, and also allows
regular users to see what events they are signed up for as well as to see an overview of the organization's information.
The app also notifies users via email when they are added to an event as well as when they are added to the system
or change their password.

## Usage:

For administrators, the navbar contains links to add locations, users, items and events to the system. 
These will be displayed on the main dashboard, which has image links to navigate between information. These pages
also include the ability to update or remove information from the system (note that anything deleted will be gone forever).


## Images:

In progress...

## Installation:

No installation necessary

## CDNs used:

- Bootstrap https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css
- font-awesome

## APIs used:

- Axios https://unpkg.com/axios/dist/axios.min.js
- Validation Factory

## Contributions:
This app is the brainchild of three developers from the Dev10 Program, collectively referred to as **Team JAK**:

**Justin Torres** is [enter bio here]

**AyDy Burling** is [enter bio here]

**Kelton Holsen** is a software developer with Ameriprise Financial. They have a B.A. in Computer Science and Creative Writing
from Augsburg University. They live in a small apartment with their partner, Jessie, and their cat, Artemis. In their free time,
they enjoy going on long walks, running tabletop RPGs, and making mashups.


## Challenges:

None of the members of this team had dealt with authentication or deployment before, and this led to some setbacks and frustrations.
This was also the first major collaborative project that any of us had done using Git, so early in the process we were slowed down 
by merge conflicts several times until we were able to get used to the system and set up our .gitignore file properly.

## Successes:
Our process of building and connecting one feature slice at a time allowed us to stay ahead of schedule for most of the project,
and to spend much of the latter time polishing and refining our user interface. We also were able to successfully deploy to Azure
and set up email notifications.

## The Road Ahead:
At some point, one of us may update this app to include repair tickets for items, which was a functionality that we originally envisioned
for the project but did not fit into the scope of the two weeks. This would allow users to submit repair requests if an item was broken and 
administrators to assign someone to repair it and update its status as needed. 

## Acknowledgements:

Shout-out to James Churchill, Kiel Donelson, and Irina Cudo, the Dev10 instruction team, for their hard work and quality 
mentorship over the past three months.

## License:

Copyright © 2021 Justin Torres, AyDy Burling, and Kelton Holsen

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
