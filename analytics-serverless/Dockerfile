FROM public.ecr.aws/lambda/nodejs:12

# Copy function code
COPY src/package.json src/package-lock.json /var/task/

RUN npm install

COPY src/index.js /var/task

# Set the CMD to your handler (could also be done as a parameter override outside of the Dockerfile)
CMD [ "main.handler" ]