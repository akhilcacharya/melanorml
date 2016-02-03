#Melanorml
PoC of Early detection for Melanoma using mobile devices using a Convolutional Neural Network (CNN).  
The CNN was trained from a hand-curated training set, which can be found in the /data folder.

##Screenshots
![App Screens](https://github.com/akhilcacharya/melanorml/blob/master/demo/Screens.png)

##About
This application was written as the final project for my Honors 310 class at NC State University, which covers the Creative Process in Science. By vote, this project in particular was voted to be the most impressive project of the section.

##Demo Video
Video may be found [here](https://www.youtube.com/embed/J6HgB2uLf6M).

##Slide Deck
Slides can be found [here](https://docs.google.com/presentation/d/1KWWua1R3g4If4Sazgls8uGM5KEeL75cZn2ZkIc77QQs/edit?usp=sharing)

##Getting Started
The application makes heavy use of the [Metamind](https://www.metamind.io/) API for fast access to Convolutional Neural Networks. To build the app, create an API key and include it within the /Models/MelanomaService.java file. Then build as normal.

##Credits

The application makes use of [edmodo/cropper](https://github.com/edmodo/cropper), which is licensed by the Apache 2.0 License. The License may be found in the assets folder.


##License
The MIT License (MIT)

Copyright (c) 2015 Akhil C. Acharya

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
