#!/bin/sh -ex
WPILIB=$HOME/wpilib

##cd $HOME/Java
##mkdir -p libs
##cd libs
##ln -fs ${WPILIB}/tools/SmartDashboard.jar
##ln -fs ${WPILIB}/java/current/lib/ntcore.jar
##ln -fs ${WPILIB}/java/current/lib/opencv.jar
##ln -fs ${WPILIB}/java/current/lib/WPILib.jar
##ln -fs ${WPILIB}/java/current/lib/ntcore.jar
##ln -fs ${WPILIB}/java/current/lib/opencv.jar
##ln -fs ${WPILIB}/java/current/lib/cscore.jar
##ln -fs ${WPILIB}/java/current/lib/wpiutil.jar
##ln -fs ${WPILIB}/user/java/lib/CTRE_Phoenix.jar
##
##cd ..
##
##echo ant -lib ${WPILIB}/java/current/ant/ant-classloadertask.jar \
##	-Dwpilib.ant.dir=${WPILIB}/java/current/ant \
##	-Duser.home=. \
##	-DuserLibs.dir=libs \
##	-Dsrc.dir=. \
##	-Dbuild.dir=build \
##	-Ddist.jar=dist.jar \
##	jar

ant -lib ${WPILIB}/java/current/ant/ant-classloadertask.jar \
	-Dwpilib.ant.dir=${WPILIB}/java/current/ant \
	-Duser.home=$HOME \
	-Dwpilib.jar=${WPILIB}/java/current/lib/WPILib.jar \
	-Dntcore.jar=${WPILIB}/java/current/lib/ntcore.jar \
	-Dopencv.jar=${WPILIB}/java/current/lib/opencv.jar \
	-Dcscore.jar=${WPILIB}/java/current/lib/cscore.jar \
	-Dwpiutil.jar=${WPILIB}/java/current/lib/wpiutil.jar \
	-Dwpilib=${WPILIB}/ \
	-DuserLibs=${WPILIB}/tools/ \
	-DuserLibs.dir=${WPILIB}/user/java/lib \
	-Dsrc.dir=. \
	-Dbuild.dir=build \
	-Dbuild.jars=jars \
	-Ddist.dir=dist \
	-Ddist.jar=dist.jar \
	compile
