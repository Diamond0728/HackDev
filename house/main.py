from flask import Flask,render_template,jsonify,request
import DnnPredict

app = Flask(__name__)
 
@app.route('/')
def test():
    return render_template('index.html')

@app.route('/estimate', methods=['GET', 'POST'])
def estimate () :
    if request.method == 'POST':
        data = request.get_json(silent=False)
        print(data)
        price = DnnPredict.predict(data);
        print(price)
        return '{\"success\": true, \"price\": '+ str(price) + '}'
 
if __name__ == '__main__':
    app.run(debug=True)