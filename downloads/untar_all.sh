#!/bin/sh

# This program deals with the dataset from WU
# it unzip all the tar files,
# and ask to delete all tar files.
#
# author: Ke Wang
# email: wangkekekexili@gmail.com


TAR_FILE_COUNT=0

# count number of tar files
for FILENAME in *.tar
do
  TAR_FILE_COUNT=`expr $TAR_FILE_COUNT + 1`
done

#
if [ $TAR_FILE_COUNT -eq 0 ]
then
  echo "No tar file has been found."
else
  echo "$TAR_FILE_COUNT tar files have been found."
  echo "Do you want to keep the original tar files? (Y/N)"
  read DECISION
  if [ $DECISION = 'n' -o $DECISION = 'N' ]
  then
    for FILENAME in *.tar
    do
      tar zxvf $FILENAME
      rm $FILENAME
    done
  else
    for FILENAME in *.tar
    do
      tar zxvf $FILENAME
    done
  fi
fi
