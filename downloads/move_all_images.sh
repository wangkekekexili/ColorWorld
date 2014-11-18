#!/bin/sh

# This program deals with the image dataset from Washington University
# The images are processed by a Java program.
# This program moves all images into 'data' directory
#
# author: Ke Wang

for FILENAME in *
do
    if [ -d $FILENAME ]
    then
        mv $FILENAME/*.* ../data 
    fi
done