# Overview

This is a proof of concept (POC) using [Spring Batch](https://spring.io/projects/spring-batch).

## Use case

## Definitions

### Job

A job is a set of steps or flows and manages how to transition from one state to next!

### Flow

A flow is a set of steps or other flows.

### Step

There are two types of steps:

* Tasklet: 
* Chunk: 

### Decider

# Setup

1º Install [Docker](https://docs.docker.com/get-docker/)

2º After install Docker, we need to install [MySQL](https://www.mysql.com/), to do that, run the command below:

`docker run -d --net=host --name=mysql -e MYSQL_ROOT_PASSWORD=1234567890 mysql:8.0.21`

# Testing

Each folder has the `Application` class, for example:

- JobApplication
- FlowApplication
- SplitApplication

Just run it or open the terminal and run the command bellow:

`./mvnw spring-boot:run`

# Be Happy