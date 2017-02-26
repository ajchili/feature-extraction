# edge detection
A way to identify points in a digital image at which the image brightness hanges sharply, or more formally, has discontinutiies.

## understanding edge detection
Edge detection works by identifiying the brightness of surrounding pixels, determining if the difference in brightness is greater than a determined amount, then saving that determination. The determination will be saved, then referenced again to create an output image. This image will display all of the determinations, thus making an edge detection map. This map highlights where the brightness levels differ significantly and likewise will identify edges.
