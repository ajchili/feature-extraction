# [edge detection](https://en.wikipedia.org/wiki/Edge_detection)
A way to identify points in a digital image at which the image brightness hanges sharply, or more formally, has discontinutiies.

## understanding edge detection
Edge detection works by identifiying the brightness of surrounding pixels, determining if the difference in brightness is greater than a determined amount, then saving that determination. The saved determination will then be referenced again to create an output image. This image will display all of the determinations, thus making an edge detection map. This map highlights where the brightness levels differ significantly and likewise will identify edges.

For my implementation of edge detection, I will be using [Sobel operator](https://en.wikipedia.org/wiki/Sobel_operator) and [Kernels](https://en.wikipedia.org/wiki/Kernel_(image_processing)) _([convolutions](https://en.wikipedia.org/wiki/Convolution))_. For more information and to fully understand these topics, looking through the provided links is recommended.

## implementation
Implementing edge detection started with getting kernal types. These types specified the importance of pixel brightness. The implemented types are listed below.

| Index         | Sobel operator(s) |
| ------------- |-------------|
| 0             | ``` { 1, 0, -1, 0, 0, 0, -1, 0, 1 } ``` |
| 1             | ``` { 0, 1, 0, 1, -4, 1, 0, 1, 0 } ``` |
| 2             | ``` { -1, -1, -1, -1, 8, -1, -1, -1, -1} ``` |
| true          | ``` { -1, 0, 1, -2, 0, 2, -1, 0, 1 } ``` and ``` { 1, 2, 1, 0, 0, 0, -1, -2, -1 } ``` |

_Index "true" occurs in a 2 pass system, where the first Sobel operator is run before the second_

If a pixel at [2, 2] in an image had a brightness of 155 _(out of 255)_, the surrounding pixels are checked to determine if there is a difference in brightness high enough to be considered an edge. With a Sobel operator, we determine this by multiplying the reversed index of the kernel _(see table above)_ by the matrix of the surrounding pixels and adding each mulitplied index together.

![Image from Wikipedia](https://wikimedia.org/api/rest_v1/media/math/render/svg/570600fdeed436d98626278f22bf034ff5ab5162)

To properly understand this, the first matrix is the kernel _(after it has been reversed)_ and will be a Sobel operator, while the second matrix is the surrounding pixels of a specified pixel.

With this value obtained, we then calculate if there is an edge. This is done by seeing if the value provided by the convolution above is greater than 0 and less than or equal to 255. This is done because of how alpha _(brightness)_ is determined with BufferedImages in Java and can be seen [here](https://github.com/ajchili/feature-extraction/blob/master/edge%20detection/EdgeDetection.java#L105) with the ternary operator.

With the math out of the way, here are the image processing steps:
1. Image is obtained. 
<img src="https://github.com/ajchili/feature-extraction/blob/master/edge%20detection/images/reference.JPG" alt="reference image" width="350"/>

2. Image is converted to greyscale. 
<img src="https://github.com/ajchili/feature-extraction/blob/master/edge%20detection/images/greyscale.JPG" alt="greyscale image" width="350"/>

3. Every pixel is analyzed.
4. Output image is produced.
<img src="https://github.com/ajchili/feature-extraction/blob/master/edge%20detection/images/kernels.JPG" alt="kernels image" width="350"/>

_Image generated with "true" index, viewing in full resolution is recommended. All other generations can be seen under images and have corresponding names to the table above._

## usage
To use the [EdgeDetection](https://github.com/ajchili/feature-extraction/blob/master/edge%20detection/EdgeDetection.java) class, generate a jar, run the class with your IDE or use the terminal and provide these arguments:
```  file name ``` and ``` Sobel operator index ```

_(the order must remain the same and both arguments must be provided)_
