import os
import requests
from flask import Flask, request, jsonify
from flask_cors import CORS
from dotenv import load_dotenv

load_dotenv()
app = Flask(__name__)
CORS(app)

# API setup
API_URL = "https://router.huggingface.co/novita/v3/openai/chat/completions"
HF_API_TOKEN = os.getenv('HF_API_TOKEN', 'YOUR_HUGGINGFACE_API_TOKEN')
PORT = os.getenv('PORT', 4000)
HEADERS = {"Authorization": f"Bearer {HF_API_TOKEN}"}
MODEL = "meta-llama/llama-4-scout-17b-16e-instruct"

def get_llama_response(user_message):
    payload = {
        "messages": [
            {
                "role": "user",
                "content": user_message
            }
        ],
        "model": MODEL,
        "max_tokens": 500,
        "temperature": 0.7,
        "top_p": 0.9
    }

    response = requests.post(API_URL, headers=HEADERS, json=payload)
    if response.status_code == 200:
        result = response.json()["choices"][0]["message"]["content"]
        return result
    else:
        raise Exception(f"API request failed: {response.status_code} - {response.text}")

@app.route('/chat', methods=['GET'])
def chat():
    message = request.args.get('message')
    
    if not message:
        return jsonify({'error': 'Missing message parameter'}), 400
    
    try:
        response_text = get_llama_response(message)
        return jsonify({'response': response_text}), 200
    except Exception as e:
        print(f"Error generating response: {str(e)}")
        return jsonify({'error': str(e)}), 500

@app.route('/test', methods=['GET'])
def run_test():
    return jsonify({'status': 'API is working'}), 200

if __name__ == '__main__':
    port_num = int(PORT)
    print(f"Llama ChatBot API running on port {port_num}")
    app.run(port=port_num, host="0.0.0.0", debug=True)
