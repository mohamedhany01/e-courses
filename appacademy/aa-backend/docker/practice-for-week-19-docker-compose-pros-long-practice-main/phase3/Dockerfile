FROM drupal:9.3

RUN apt update && apt install -y git && rm -rf /var/lib/apt/lists/*

WORKDIR /var/www/html/themes

RUN git clone --branch 8.x-3.x --single-branch --depth 1 https://git.drupal.org/project/bootstrap.git

RUN chown -R www-data:www-data bootstrap
