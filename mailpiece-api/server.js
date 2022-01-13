var express    = require('express');       
var app        = express();                
var bodyParser = require('body-parser');
var states = ["INGESTED", "DELIVERED"];
    
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

var router = express.Router();

router.get('/', function(req, res) {
    console.log('Getting ids');
    res.json([
        { id: Math.floor(Math.random() * 101) },
        { id: Math.floor(Math.random() * 101) }
    ]);
});

router.get('/state/:id', function(req, res) {
    console.log('Getting state for id ' + req.params.id);
    res.json({ state: states[Math.floor(Math.random() * states.length)] });
});

app.use('/api', router);

app.listen(port, () => {
    console.log("Server is listening on port " + port);
});
