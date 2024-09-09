# Project: Build a React app and serve it with nginx

# Step 1: Base image for build stage - use official node image with alpine base
#         Name it build-stage
FROM node:16-alpine AS build-stage

# Step 2: Set the working directory to /app
WORKDIR /app

# Step 3: Copy in all the files needed to install dependencies
COPY package*.json /app

# Step 4: Install dependencies using npm
#   To keep the image small, (force) clean the npm cache after
#   Chain the commands to reduce the number of layers in the image
RUN npm install && npm cache clean --force

# Step 5: Copy in all the files from the current directory
COPY . .

# Step 6: Build application
RUN npm run build

# Step 7: Bring in the base image for NGINX (alpine)
FROM nginx:stable-alpine

# (There will be no need to EXPOSE a port because this base image
# already has an EXPOSE command)

# Step 8: Set working directory to the html folder for nginx
#   (Hint: This directory was also used in phase 1)
WORKDIR /usr/share/nginx/html

# Step 9: Copy over the build files from build-stage
#   The build directory was created inside the app directory in the 
#   build-stage. The files inside that folder can be put directly into
#   the html folder that you just set as your working directory
COPY --from=build-stage /app/build .

# Step 10: Replace the default NGINX config with the application's version
#    The absolute path to the default NGINX config file is 
#    /etc/nginx/conf.d/default.conf —replace it with the nginx.conf file
#    provided in this folder
COPY nginx.conf /etc/nginx/conf.d/default.conf

# (No need to add a CMD because it's included in the base image)