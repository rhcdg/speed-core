@echo off
rem Put the .env variables in the dev's environment
FOR /F "tokens=1,2 eol=# delims==" %%a in (.env-dev) DO (
  echo set %%a=%%b
  set %%a=%%b
)
