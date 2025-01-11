# Use a Node.js base image
FROM node:18

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire Angular app source code
COPY . .

# Expose the development server's port (default is 4200)
EXPOSE 4200

# Run the Angular app in development mode
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0"]
