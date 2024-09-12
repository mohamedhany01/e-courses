# Deployment to Heroku Command List

For your convenience, here's a list of Heroku commands that you may need to use
for your Heroku-deployed project.

## Log in to the Heroku CLI

Log in to the Heroku CLI from your terminal to interact with Heroku through
terminal commands. This will open up a browser window where you need to submit
your Heroku user credentials.

```sh
heroku login
```

## Add the `heroku` remote repository

You should only do this once or whenever you make a new Git clone for your
project.

Connect your project on Heroku as a remote Git repository to your local Git
repository. The remote repository that you are used to using to push to GitHub
is called `origin`. The remote repository that will be connected to your
application on Heroku is called `heroku`.

```sh
heroku git:remote -a <name-of-Heroku-app>
```

Replace `<name-of-Heroku-app>` with the name of your application on Heroku.

## Deploy a branch to Heroku

Push a local branch to Heroku to deploy.

```sh
git push heroku <branch-name>:main
```

Replace `<branch-name>` with the name of the local repository branch that you
want to deploy to Heroku.

This command pushes the `<branch-name>` as the `main` branch on the `heroku`
remote repository.

## Run migrations on the Heroku PostgresQL database

```sh
heroku run npm run sequelize db:migrate
```

## Run seeders on the Heroku PostgresQL database

```sh
heroku run npm run sequelize db:seed:all
```

## Reset the production database

To destroy the database, remove the "Heroku Postgres" add-on, then add it back
in manually through the Heroku dashboard, **OR** programmatically:

Remove the Heroku Postgres add-on:

```sh
heroku addons:destroy heroku-postgresql:hobby-dev
```

Add the Heroku Postgres add-on back:

```sh
heroku addons:create heroku-postgresql:hobby-dev
```

## Open the Heroku terminal

```sh
heroku bash
```

This allows you to interact with the production terminal on Heroku. For example,
you can run migrations or any other npm scripts from here like you would
normally do in development:

```sh
npm run sequelize db:migrate
```

## Heroku Terminal Output

View the extensive outputs that the Heroku application printed to the
terminal.

```sh
heroku logs
```

View the shortened outputs that the Heroku application printed to the
terminal.

```sh
heroku logs --tail
```

## Open the deployed application in the browser

Open up a browser window to the root URL of your application deployed to Heroku.

```sh
heroku open
```

## Set an environment variable on Heroku

To set the value of an environment variable on Heroku from your local terminal:

```sh
heroku config:set <KEY>=<value>
```

Replace `<KEY>` with the environment variable name that you wish to set. Replace
`<value>` with the value you wish to set the environment variable to.

## Output an environment variable value on Heroku

To output the value of an environment variable value on Heroku to your local
terminal:

```sh
heroku config:get <KEY>
```

Replace `<KEY>` with the environment variable name that you wish to see.