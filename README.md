# Scheduler
Scheduler is an app that arranges your events in calendar automatically according to participants availabilities and given parameters. It  consists of Java REST api with MongoDB and Angular frontend ([repo](https://github.com/luk-apps/scheduler-client)).

How it works?
1. Create participants and set up their availabilities in calendar
2. Create events and set their duration and participants
3. Provide additional parameters and generate schedule

## How to run?

You can run the app with docker:
```console
git clone https://github.com/luk-apps/scheduler-api.git
cd scheduler-api
docker-compose up
```
Then open [localhost:80](localhost:80). By default the app runs on port 80. You can change it in docker-compose.yml file if neccessary.

For the purpose of demonstration log in with the following creditentials:
username: *admin*
password: *password*

### Author
[Łukasz Zawistowski](https://github.com/luk-apps)

