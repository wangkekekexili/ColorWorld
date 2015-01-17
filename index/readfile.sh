#!/bin/bash
for((a=0;a<1000;a++))   #for循环，从1到10，每次递增1
    do
        #echo $a
        cat $a.txt
    done     # 最后语句结束。
