from flask import Flask, request, make_response, jsonify, send_file

app = Flask(__name__)

portNumber = 8000

@app.route('/factorial', methods=['GET', 'POST'])
def returnFactorial():
	if request.method == 'GET':
		n = request.args.get('number')
	elif request.method == 'POST':
		n = request.form['number']
	n = int(n)
	fact = factorial(n)
	return make_response(str(fact))


def factorial(n):
	fact = 1
	for i in range(2, n+1):
		fact *= i
	return fact


@app.route('/combination', methods=['GET', 'POST'])
def combination():
	if request.method == 'GET':
		n = request.args.get('n')
		r = request.args.get('r')
	elif request.method == 'POST':
		if request.json:
			n = request.json['n']
			r = request.json['r']
		else:
			n = request.form['n']
			r = request.form['r']
	n = int(n)
	r = int(r)
	nCr = factorial(n)/(factorial(n-r)*factorial(r))
	return make_response(jsonify({'result':nCr}))


@app.route('/get_image', methods=['GET', 'POST'])
def getImage():
	file_name='scenery1.jpg'
	try:
		return send_file(file_name, attachment_filename='scenery1.jpg')
	except Exception as e:
		return str(e)


if __name__ == '__main__':
	app.run('0.0.0.0', port=portNumber, debug=True)