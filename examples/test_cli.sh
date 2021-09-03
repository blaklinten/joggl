#!/bin/bash

printf "%s\n" "What do you want to do?"
select choise in "Start example entry" "Stop a running entry" "Get current status"; do
	case $choise in
		Stop*)
			curl localhost:8080/stop-timer
		;;
		Start*)
			curl -X POST -H "Content-type: application/json" -d "$(cat entry.json)" "localhost:8080/start-timer"
		;;
		Get*)
			curl localhost:8080/get-status
		;;
		
	esac
done
