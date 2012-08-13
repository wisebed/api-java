#!/bin/bash
wsimport \
 -b src/main/resources/CommonTypes.xjb \
 -b src/main/resources/SNAATypes.xjb \
 -b src/main/resources/SNAA.xjb \
 -s src/main/java/ \
 -keep \
 -wsdllocation REPLACE_WITH_ACTUAL_URL \
 -Xnocompile \
 src/main/resources/SNAA.wsdl
