# FCS Graphics

## Description
A small Java utility that will convert a small image file into the Flow Cytometer Standard Data Format (FCS). 

The output FCS file is designed to be loaded by any software that can read FCS version 3.1 ([specification here](http://isac-net.org/PDFS/90/9090600d-19be-460d-83fc-f8a8b004e0f9.pdf)).

## Usage
The application requires Java 1.8 or later.  When run without command line input, 2 file open dialogs will be presented. The first dialog selects the source image.  The second dialog selects the destination FCS file (the FCS file does not need to already exist).  

## Command Line
Command line usage : 
`java FcsGraphics -image [imagefile] -fcs [fcsOutput.fcs]`

## Notes
* Writing the FCS file can take up to a minute or so if the source image is large or complex.
* Using an image that is too large will crash the program.
* To view an FCS file try using :
  * [FCS Express](https://www.denovosoftware.com/site/reader.shtml) (Working)
  * [MATLAB](http://www.mathworks.com/matlabcentral/fileexchange/9608-fcs-data-reader) (not tested)
  * [FlowJo](http://www.flowjo.com/flowjo-free-trial/) (not tested)

Shockingly, FCS is not an efficient means of storing image data.  Most FCS files contain a few hundred thousand events, which are sufficient for most lab experiments.  By default, the program will attempt to scale the source image to fit within the bounds of the data range (currently 2^24 or 16,777,216).  As a result, for images greater than about 400x400 pixels, the utility will generate more event data than can be handled by the FCS format, and crash with an exception declaring that the event capacity has been reached.
