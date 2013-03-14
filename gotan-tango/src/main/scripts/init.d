#!/bin/sh
#
#     System startup script for gotan
#     Copyright (C) 2013  Vincent Hardion
#          
#     This library is free software; you can redistribute it and/or modify it
#     under the terms of the GNU Lesser General Public License as published by
#     the Free Software Foundation; either version 2.1 of the License, or (at
#     your option) any later version.
#			      
#     This library is distributed in the hope that it will be useful, but
#     WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#     Lesser General Public License for more details.
#      
#     You should have received a copy of the GNU Lesser General Public
#     License along with this library; if not, write to the Free Software
#     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
#     USA.
#
### BEGIN INIT INFO
# Provides:          gotan
# Required-Start:    $local_fs $remote_fs $network $time $named
# Should-Start: $time sendmail
# Required-Stop:     $local_fs $remote_fs $network $time $named
# Should-Stop: $time sendmail
# Default-Start:     3 5
# Default-Stop:      0 1 2 6
# Short-Description: gotan Internet Of Thing server
# Description:       Start the gotan server
### END INIT INFO

GOTAN_PID_FILE="/var/run/gotan.pid"
GOTAN_HOME="/opt/gotan"
GOTAN_USER="root"

# Source function library.
. /etc/init.d/functions

GOTAN_CMD="$GOTAN_HOME/bin/gotan.sh"

RETVAL=0

case "$1" in
    start)
	echo -n "Starting gotan "
	daemon --user "$GOTAN_USER" --pidfile "$GOTAN_PID_FILE" $GOTAN_CMD > /var/log/gotan.log 2>&1 &
	RETVAL=$?
	if [ $RETVAL = 0 ]; then
	    success
            # Retrieve JAVA Gotan process ID 
            sleep 1
            PIDDAEMON=`pgrep -u $GOTAN_USER -n -f $GOTAN_CMD` 
            [ -z "$PIDDAEMON" ] || PIDJAVA=`ps -o pid= --ppid $PIDDAEMON` 
	    echo $PIDJAVA > "$GOTAN_PID_FILE"  # just in case we fail to find it
	else
	    failure
	fi
	echo
	;;
    stop)
	echo -n "Shutting down gotan "
	killproc gotan
	RETVAL=$?
	echo
	;;
    try-restart|condrestart)
	if test "$1" = "condrestart"; then
		echo "${attn} Use try-restart ${done}(LSB)${attn} rather than condrestart ${warn}(RH)${norm}"
	fi
	$0 status
	if test $? = 0; then
		$0 restart
	else
		: # Not running is not a failure.
	fi
	;;
    restart)
	$0 stop
	$0 start
	;;
    force-reload)
	echo -n "Reload service gotan "
	$0 try-restart
	;;
    reload)
    	$0 restart
	;;
    status)
    	status gotan
	RETVAL=$?
	;;
    probe)
	## Optional: Probe for the necessity of a reload, print out the
	## argument to this init script which is required for a reload.
	## Note: probe is not (yet) part of LSB (as of 1.9)

	test "$GOTAN_CONFIG" -nt "$GOTAN_PID_FILE" && echo reload
	;;
    *)
	echo "Usage: $0 {start|stop|status|try-restart|restart|force-reload|reload|probe}"
	exit 1
	;;
esac
exit $RETVAL
