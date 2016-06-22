var express = require('express');
var pg = require('pg');
var bodyParser = require('body-parser');
var crypto = require('crypto'),
    algorithm = 'aes-256-ctr',
    password = 'Y0uc@ntG3tinh3r3';

var app = express();

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.set('port', (process.env.PORT || 5000));

app.get('/user', function (req, res) {
  pg.connect(process.env.DATABASE_URL, function(err, client, done) {
    var query = 'SELECT username, first, last, email, bio FROM users WHERE username=($1)';
    client.query(query, [req.query.username.toLowerCase()], function(err, result) {
      done();
      if (err) {
        console.error(err);
        res.json({
          error: err}
        );
      }
      else {
        delete result.rows[0].password;
        res.json({user: result.rows[0]});
      }
    });
  });
});

app.get('/login', function(req, res) {
  pg.connect(process.env.DATABASE_URL, function(err, client, done) {
    var queryText = 'SELECT username, password, first, last, email, bio FROM users WHERE username=($1)';
    client.query(queryText, [req.query.username.toLowerCase()], function(err, result) {
      done();
      if (err) {
        res.json({
          error: err
        });
      }
      else if (result.rowCount === 0) {
        res.json({
          error: 'USERNAME DOES NOT EXIST'
        });
        return;
      }
      else {
        // check password
        var decrypted = decrypt(result.rows[0].password);

        if (decrypted !== req.query.password) {
          res.json({
            error: 'INCORRECT PASSWORD'
          });
        }
        else {
          delete result.rows[0].password;
          res.json({
            status: 'OK',
            user: result.rows[0]
          });
        }
      }
    });
  });
});

app.post('/signup', function(req, res) {
  if (!req.body.username || !req.body.password || !req.body.email || !req.body.first || !req.body.last)  {
    res.json({status: req.body });
  }
  else {
    pg.connect(process.env.DATABASE_URL, function(err, client, done) {
      var queryText = 'INSERT INTO users(username, password, first, last, email) VALUES($1, $2, $3, $4, $5)';
      client.query(queryText,[req.body.username.toLowerCase(), encrypt(req.body.password), req.body.first, req.body.last,
        req.body.email], function(err, result) {
        done();

        if (err) {
          res.json({
            error: err
          });
        }
        else {
          res.json({status:'OK'});
        }
      });
    });
  }
});

app.post('/updatebio', function(req,res) {
  pg.connect(process.env.DATABASE_URL, function(err, client, done) {
    var queryText = 'UPDATE users SET bio=($1) WHERE username=($2)';
    client.query(queryText, [req.body.bio, req.body.username.toLowerCase()], function(err, result) {
      done();
      if (err) {
        res.json({
          error: err
        });
        return;
      }
      else if (result.rowCount === 0) {
        res.json({
          error: 'USERNAME NOT FOUND'
        });
        return;
      }
      else {
        res.json({
          status: 'Bio updated'
        });
      }
    });
  });
});

app.listen(app.get('port'), function() {
  console.log('Node app is running on port', app.get('port'));
});

function encrypt(text){
  var cipher = crypto.createCipher(algorithm, password);
  var crypted = cipher.update(text,'utf8','hex');
  crypted += cipher.final('hex');
  return crypted;
}

function decrypt(text){
  var decipher = crypto.createDecipher(algorithm, password);
  var dec = decipher.update(text,'hex','utf8');
  dec += decipher.final('utf8');
  return dec;
}
