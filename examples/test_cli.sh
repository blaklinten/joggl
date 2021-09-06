#!/bin/bash

printf "%s\n" "What do you want to do?"
select choise in "Start example entry" "Stop a running entry" "Get current status" "Sum by name"; do
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

		Sum*)
			read NAME
			curl localhost:8080/sum-entries-by-name?name=$NAME
		;;		
	esac
done
