#!/bin/bash

printf "%s\n" "What do you want to do?"
select choise in "Start example entry" "Stop a running entry" "Get current status" "Sum by name" "Sum by client" "Sum by project"; do
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

		Sum*name)
			read NAME
			curl localhost:8080/sum-entries-by-name?name=$NAME
		;;		
		Sum*client)
			read CLIENT
			curl localhost:8080/sum-entries-by-client?client=$CLIENT
		;;		
		Sum*project)
			read PROJECT
			curl localhost:8080/sum-entries-by-project?project=$PROJECT
		;;		
	esac
done
