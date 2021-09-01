#!/bin/bash

printf "%s\n" "What do you want to do?"
select choise in "Start example entry" "Stop a running entry"; do
	case $choise in
		Sto*)
			curl localhost:8080/stop-timer
			;;
		Sta*)
			curl -X POST -H "Content-type: application/json" -d "$(cat entry.json)" "localhost:8080/start-timer"
			
		;;
	esac
done
