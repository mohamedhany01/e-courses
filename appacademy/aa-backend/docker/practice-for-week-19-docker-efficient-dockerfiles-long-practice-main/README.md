# Make Dockerfiles More Efficient

Okay, so you've made a couple of `Dockerfile`s. Now it's time to look at some
common mistakes and how you can avoid them. 

Check out the provided `Dockerfile` for a Node app. There are some problems with 
it. To say the least, it has errors trying to run the application!

## Before you begin

Keep your [Docker command reference] on hand.

After each phase you can re`build` the image and `run` a container from it to 
see your progress incrementally. Remember to tag your image and name your 
container, so they are easier to reference in other commands (e.g. `stop`, `rm`, 
`prune`, `ls` and so on).

> Hint: When you run the container you will need to provide the appropriate port
> flag in order to view the container from your browser.

### Option A: Run locally

Perhaps you'd like to run the application on your machine to see what it does 
before you put it in **Docker**. If so, run the `npm install` and `npm start` 
the project. Look in the __index.js__ file to see which port **Express** was 
started on so you can go to the [localhost url] in your browser. When you are 
done, stop **npm** using `Ctrl+c`.

### Option B: Try Docker as-is

Build the image then run the container and see what errors are reported.

Test your knowledge by trying to determine the correct **Docker** commands on 
your own. If you get stuck, check out the [hints](#hints) section at the end.

> Hint: Tag this image with a version named something like 'flaws:original'. 
> This will allow you to compare sizes later.

### Option C: Both

To get the most out of this lesson, it is recommended that you try both of these
options.

## Phase 1: Docker ignore

One of the first things you should do when you use a `Dockerfile` is to write a 
`.dockerignore`. Sounds familiar right? A `.dockerignore` file ignores the files
you don't want to have in your Docker image. You can ignore the same sorts of 
things as with `.gitignore`. 

For this project, your `.dockerignore` for this setup should be ignoring:

```bash
.git/
node_modules/
dist/
```

It is best practice to also ignore Docker's files. By using wild cards like `*`,
any of these files in subdirectories or which have variations on the name will
also be ignored.

```bash
*Dockerfile*
*docker-compose*
```

**TAKE ACTION**: Go ahead and make the `.dockerignore` file now. Build the image
and run the container to see the differences.

Nice! Looking more efficient already without all those `node_modules` taking up
space in your image. :)

## Phase 2: Single-process container

Technically, you **CAN** start multiple processes inside a Docker container. You
**CAN** put your database, frontend, backend, [`ssh`][ssh], and
[`supervisor`][supervisor] all into one docker image. However, this is a poor
practice because:

1. Your build times will be **long**. Remember, every time something changes in 
   a Dockerfile everything BELOW the change won't be able to use the image cache.
2. You image will be large and take a while to download and upload.
3. Your container logs can be a mess with so much going on in one container.
4. A single image deployment doesn't scale well - you can't give more resources
   to just the part of the system that is running slowly (for example).

The list goes on! Docker's advice is to prepare a separate Docker image for each
component of your app.

**TAKE ACTION**: Remove the packages that aren't helping you run a **node** app
from your `Dockerfile`. Make sure to update the `CMD` instruction to only 
include the command for the package(s) you kept.

## Phase 3: Order instructions to get the most benefit from caching

Docker is all about **layers**. Knowledge of how they work is essential.

Here is a quick reminder:

1. Each command in your Dockerfile will create a new layer.
2. Layers are cached and reused.
3. Invalidating the cache of a single layer invalidates all subsequent layers.
4. Layers are immutable, so if you add a file in one layer, then remove it in 
   the next one, the image STILL contains that file (it's just not available in
   the container).

**TAKE ACTION** Remove the `apt-get upgrade` instruction, as it makes your build
non-deterministic. Instead, you want to rely on your base image for OS package 
upgrades. Go ahead and build the image to see how much faster it is already!

Now, look at the Dockerfile and think about what would happen to all subsequent
layers whenever something is changed in the source code (__index.js__).

> Hint: Which instruction is adding all your project files to the image?

**TAKE ACTION** Modify the order of the commands, so any source code changes 
affect the `Dockerfile` as late in the process as possible. You can try several
variations until you find the best option.

1. Move the instruction
2. Rebuild the image
3. Edit index.js by changing the displayed message
4. Rebuild the image again
5. See how many cached layers were used
6. Repeat 1 through 5 until you have a working version with the most steps
   reporting ` ---> Using cache`

Now, look at all those `RUN` commands. Each one of them is currently making a
whole new layer - very inefficient. 

**TAKE ACTION** Squash the `RUN` commands together! Look at the documentation or
previous project for a reminder of how to do multi-line commands.

## Phase 4: Specify the version of the base image

The `latest` tag will always be the default when no tag is included. This means
the current instruction of `FROM ubuntu` is equal to `FROM ubuntu:latest`. There
is a problem with this: what if `ubuntu` releases a new version that your build 
isn't compatible with? So, unless it is your intention to have a generic 
`Dockerfile` that must stay up-to-date with the base image, you should provide a
specific tag for the base image.

**TAKE ACTION** For this project, use the `18.04` tagged version of `ubuntu`.

## Phase 5: Use the most-specific base image possible

If this is a `Dockerfile` for a Node app, then why is `ubuntu` your base image?
Strongly consider your needs before using generic images. Do you really need a 
general-purpose base image, when you just want to run a node application?

A better option is to use a specialized image with node already installed. While
you are at it, use the `alpine` tagged version of node to make sure your image 
is as small as possible. 

**TAKE ACTION** Change the base image to `node:10-alpine` and remove all the 
`apt-get` lines that were installing node for you (since you now have node 
installed in the base image).

> Reminder: If you were to install any packages using the `alpine` distribution,
> you would need to remember to use the **Alpine Package System** (`apk`) 
> instead of `apt-get`.

## Phase 6: Prefer `COPY` Over `ADD`

Just like the title says - use `COPY`, it's simpler. `ADD` has some logic for
downloading remote files and extracting archives, which in most scenarios is
not necessary. Just stick with `COPY` whenever your files are local.

**TAKE ACTION** Switch `ADD` to `COPY` in your `Dockerfile`.

## Phase 7: Changing directories

There is a specific command in Docker for changing the active directory of the
container to a specified location, and that is `WORKDIR`!

**TAKE ACTION** Following `Dockerfile` best practices, use `WORKDIR` to instead
of `RUN` to change directories.

> Tip: If you set the WORKDIR before COPY, then you can change the second
> parameter of copy to `.` which means you need to modify fewer instructions
> (ideally only one!) if the directory used inside Docker ever changes.

## Phase 8: Syntax

Something about that `CMD` looks a bit off. 

**TAKE ACTION** Check out the Docker [`CMD`][cmd] docs and fix that syntax to
follow the preferred form!

Now, try building your new image, and then running a container with an exposed 
port based off that image. You should be able to navigate to the port you 
exposed in your browser and see the simple `Hello World!` message there.

## Congratulations!

You fixed 8 common flaws in `Dockerfile`s. Good work!

## Hints

Here are some **Docker** commands that you may find useful for this project.

* `docker image build -t flaws .`
* `docker container run -p 3000:3000 --name flaws -d flaws`
* `docker container stop flaws`
* `docker container ls`
* `docker container rm flaws`
* `docker image ls`
* `docker image rm flaws`
* `docker image rm $(docker image ls -a -q)` (clean up a bunch of unused images)
* `docker container rm -f flaws` (force kill and delete, if something goes 
   wrong where the container won't stop normally)


[Docker command reference]: https://appacademy-open-assets.s3-us-west-1.amazonaws.com/Modular-Curriculum/content/docker/topics/containers/assets/reading-container-command-sheet.pdf
[localhost url]: http://localhost:3000
[cmd]: https://docs.docker.com/engine/reference/builder/#cmd