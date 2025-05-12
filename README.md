# Llama 2 ChatBot - Android App

This Android application provides a chat interface to interact with the Llama 2 language model. It was developed as part of SIT708 Task 8.1C.

## Features

- User authentication with username
- Chat interface to interact with Llama 2 AI model
- Real-time messaging with the AI
- Modern and responsive UI

## Setup Instructions

### Backend Server

1. Navigate to the backend directory:
   ```
   cd BackendApiLLM
   ```

2. Install the required dependencies:
   ```
   pip install -r requirements.txt
   ```

3. Set up your Hugging Face API token in a `.env` file:
   ```
   HF_API_TOKEN=your_huggingface_token
   ```

4. Run the Flask server:
   ```
   python main.py
   ```
   The server will run on port 4000 by default.

### Android App

1. Open the ChatBot project in Android Studio.

2. Build and run the application on an emulator or physical device.

3. If you're using an emulator, the app is already configured to connect to the backend at `http://10.0.2.2:4000` (which routes to localhost:4000 on your host machine).

4. If you're using a physical device, you'll need to modify the `BASE_URL` in `ApiClient.kt` to point to your computer's IP address.

## API Endpoints

The backend server provides the following endpoints:

- `GET /chat?message=<message>` - Send a message to the Llama 2 model and get a response
- `GET /test` - Test if the API is working

## Technology Stack

- **Backend**: Flask, Hugging Face API, Llama 2 model
- **Frontend**: Android (Kotlin), Retrofit for API calls

## Architecture

The application follows a client-server architecture:

- The Flask server acts as a middleware between the Android app and the Llama 2 model
- The Android app communicates with the Flask server via RESTful API calls
- The UI is built using Android's native components and follows Material Design guidelines

## License

This project is part of a university assignment and is provided for educational purposes only.
