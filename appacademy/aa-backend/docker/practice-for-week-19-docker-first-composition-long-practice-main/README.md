# First Composition

Time to take on your first Docker Compose file! You'll be creating a Compose
file for the `nginx` Dockerfile you made in Phase 1 of the Dockerfiles Galore
project.

Start off by taking a look at the [skeleton][skeleton]. You've already created
the `Dockerfile`, so all you'll need to do is create a `docker-compose.yml` file
to build your custom image and start a container with a single command. If you
are unsure about syntax or would like to see an example, the [Docker
Compose][docs] documentation is wonderfully thorough.

**Reminder:** indentation is how the YAML file formats group information, so
indentation is important. Check out this quick [YAML][yaml] reference.

You'll start off, as with every Docker Compose file, by specifying the `version`
of Docker Compose you'll be using. For this `docker-compose.yml` use version
"3". Next, you'll be defining your `services` (the containers you'll be running).
Create a `service` named `nginx`. Your file should look like this:

```
version: "3"

services:
    nginx:
```

Now you'll need to tell Compose to `build` a custom image for you. Reference the
[build documentation][build-docs] if you need any guidance about syntax. Make
sure to name the image by adding a `image` command below your `build` command.
Finally, you'll want to tell Docker Compose which ports you want to expose when
it creates the container for you. Expose your local port 80, aiming traffic at
port 80 in the container.

**Linux Users:** Some students using Linux have had trouble exposing port 80
locally. If this happens, feel free to use port 81 instead. 

Awesome! Use `docker-compose up` and when Compose tells you the container is
attached to the network, it will then "hang". Now try going to
`http://localhost:80`.

Awesome! Make sure to check out the color coded logs that Compose provides you
for each container. Now stop the server and use `docker-compose down` to have
compose automatically stop and remove the container it started up.

However, what if you wanted to change the HTML? Head into the `index.html` we
provided and change the text inside of the `h1` tag. What happens if you use
`docker-compose up` again? Wait a second... the text doesn't change!

What you've just encountered is a common error with Compose. This error is so
common you are even warned about it in the logs:

```
WARNING: Image for service custom-nginx was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
```

If you change your `Dockerfile`, or any source code your `Dockerfile` is using
in order to build your image, you will need to command Compose to rebuild the
image for you. Otherwise, Compose will use the previous image it built. You can
tell Docker Compose to rebuild your image by using the `docker-compose up
--build` or `docker-compose build` command. Now let's try using Compose in
detached mode: `docker-compose up -d --build` to re-start your `nginx` server and
head to `localhost:80` to see your HTML change.

Good job! Feel free to commit this file to Github for later use. Make sure to
use `docker-compose down` to clean up your container, or you could add the
`--rmi` to tell Compose to additionally remove the image it built.

In this project, you've used Docker Compose commands to build an image and run a
single container - note that this is not the most common use case for Docker
Compose. Where Compose really shines is running multiple containers, and
effectively your whole application, with a single command. You'll get into more
complex Compose files in the next project, but for now, be proud of your first
Docker Compose file!

[build-docs]: https://docs.docker.com/compose/compose-file/#build
[yaml]: https://learnxinyminutes.com/docs/yaml/
[docs]: https://docs.docker.com/compose/compose-file/
[skeleton]: https://assets.aaonline.io/Docker/homeworks/first_composition/skeleton.zip