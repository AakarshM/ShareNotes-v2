var express = require('express');
var AWS = require('aws-sdk');
var fs = require('fs');
var path = require('path');
var bodyParser = require('body-parser');
var multer  = require('multer');
var axios = require('axios');

var app = express();
AWS.config.loadFromPath('./config.json');

app.use(express.static('public'))
app.use(bodyParser.json());

//initialize s3

var s3 = new AWS.S3({apiVersion: '2006-03-01'});


app.get('/', function (req, res) {
	res.send('Hey')
});

function transformName(name){
	var str = name.replace(/\s+/g, "+");
	return str;
}


//upload.single('file') where 'file' comes from multipart upload form
var fileUpload = multer({dest:'./tmp/'}).single('file');
app.post('/uploader', fileUpload, function (req, res) {

	var body = req.body;
	var university = body.university;
	var date = body.date; //Year
	var semester = body.semester; //Summer, Winter, Fall
	var course = body.course;
	var professor = body.professor;
	var name = req.file.originalname; //Name of notes

	var safeUniversity = transformName(university);
	var safeDate = transformName(date);
	var safeSemester = transformName(semester);
	var safeCourse = transformName(course);
	var safeProfessor = transformName(professor);
	var safeName = transformName(name);


	var tmp_path = req.file.path;

	var newPath = "Files/" + safeUniversity + "/" + safeDate + "/" + safeSemester + "/" + safeCourse + "/" + safeProfessor + "/" + safeName;

	uploadParams = {
		Bucket: "uw-note-share",
		Key: newPath,
		Body: "",
		ContentType: req.file.mimetype
	};

	var originalName = req.file.originalname;
	var type = req.file.mimetype;


	var fileStream = fs.createReadStream(tmp_path);
	fileStream.on('error', function(err) {
 	// console.log('File Error', err);
	});
	uploadParams.Body = fileStream;


	s3.upload(uploadParams, function (err, data) {
  	if (err) {
    	console.log("Error", err);
 	 } if (data) {
    	console.log("Upload Success", data.Location);

    	axios.post('https://uwsharenotes.herokuapp.com/api/uploader', {
    		 'name': name.toLowerCase(),
   			 'university': university.toLowerCase(),
   			 'course': course.toLowerCase(),
   			 'professor': professor.toLowerCase(),
   			 'date': date.toLowerCase(),
   			 'semester': semester.toLowerCase(),
   			 'url': newPath

 		 })
  			.then(function (response) {
   			 console.log(response);
 		 })
  			.catch(function (error) {
  			  console.log(error);
  			});

 	 }
	});

	fs.unlinkSync(tmp_path);

	res.send(originalName);

})

app.use('/upload',express.static(path.join(__dirname, 'public/upload.html')));

app.listen(3000, function (argument) {
	console.log('Listening on 3000')
});