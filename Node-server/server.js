var express = require('express');
var AWS = require('aws-sdk');
var fs = require('fs');
var path = require('path');
var bodyParser = require('body-parser');
var multer  = require('multer');
var axios = require('axios');
var mime = require('mime');


var app = express();
AWS.config.loadFromPath('./config.json');

app.use(express.static('public'))
app.use(bodyParser.json());

//initialize s3

var s3 = new AWS.S3({apiVersion: '2006-03-01'});


app.get('/', function (req, res) {
	res.send('Hey')
});


//upload.single('file') where 'file' comes from multipart upload form
var fileUpload = multer({
	dest:'./tmp/',
  filename: function (req, file, cb) {
    cb(null, Date.now() + path.extname(file.originalname)) //Appending extension
  }

}).single('file');

app.post('/uploader', fileUpload, function (req, res) {

	var tmp_path = req.file.path;

	uploadParams = {
		Bucket: "uw-note-share",
		Key: "Files/" + req.file.originalname.toString(),
		Body: ""
	};

	var originalName = req.file.originalname.toString();
	console.log(originalName);
	var type = req.file.mimetype;

   
	var fileStream = fs.createReadStream(tmp_path);
	fileStream.on('error', function(err) {
 	  console.log('File Error', err);
	});
	uploadParams.Body = fileStream; 


	s3.upload(uploadParams, function (err, data) {
  	if (err) {
    	console.log("Error:", err);
 	 } if (data) {
    	console.log("Upload Success", data.Location);
    	
    	axios.post('https://uwsharenotes.herokuapp.com/uploader', {
    		 'name': '0',
   			 'university': '1',
   			 'course': '2',
   			 'firstLine': '3',
   			 'fileName': '4',
   			 'filePath': 'Files/p'
 		 })
  			.then(function (response) {
   			 console.log(response);
 		 })
  			.catch(function (error) {
  			  console.log(error);
  			});

 	 }
	});


	
	fs.unlinkSync(tmp_path)
	res.send(originalName);

})

app.use('/upload',express.static(path.join(__dirname, 'public/upload.html')));

app.listen(3000, function (argument) {
	console.log('Listening on 3000')
});