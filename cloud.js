var AV = require('leanengine');

/**
 * 一个简单的云代码方法
 */
AV.Cloud.define('hello', function(request) {
  return 'Hello world!';
});

/**
 * 一个简单的云代码方法
 */
AV.Cloud.define('testnet', function(request) {
  return true;
});

/**
 * 登录方法
 * @param  {[type]} request [description]
 * @return bool 登录结果true or false [description]
 */
AV.Cloud.define('login', function(request) {
  var query = new AV.Query('Client');
  query.equalTo('name', request.params.name);
  return query.find().then(function(results) {
    //result!results!!!
    if(results[0].get('password') == request.params.password){
    	return results[0].id;
    }
    else{
    	return false;
    }
  }).catch(function(error) {
    throw new AV.Cloud.Error("查询失败");
  });
});
/**
 * 注册方法
 * @param  {[type]} request [description]
 * @return bool true or false [description]
 */
AV.Cloud.define('signin', function(request) {
  var query = new AV.Query('Client');
  query.equalTo('name', request.params.name);
  return query.find().then(function(results) {
    if (results.length == 0) {
      var Client = AV.Object.extend('Client');
      var newclient = new Client();
      newclient.set('name', request.params.name);
      newclient.set('password', request.params.password);
      return newclient.save().then(
        function(client) {
        	return true;
        },
        function(error) {
          return false;
        }).catch(function(error) {
	    throw new AV.Cloud.Error("查询失败");
	  });
    } else {
      return false;
    }
  }).catch(function(error) {
    throw new AV.Cloud.Error("查询失败");
  });
});

AV.Cloud.define('getAllInfos',function (request) {
	// body...
	var query = new AV.Query('Infomation');
	query.equalTo('state',true);
	return query.find().then(function (results) {
		return results;
		// body...
	}).catch(function (error) {
		// body...
		throw new AV.Cloud.Error("查询失败");
	});
});

AV.Cloud.define('addInfo',function (request) {
	var Infomation = AV.Object.extend('Infomation');
	var infomation = new Infomation();
	infomation.set('writername',request.params.writername);
	infomation.set('time',request.params.publishtime);
	infomation.set('title',request.params.title);
	infomation.set('detail',request.params.content);
	infomation.set('state',true);
	infomation.set('writerId',request.params.writerId);
	return infomation.save().then(function (infomation) {
		return true;
		// body...
	},function () {
		// body...
		return false;
	}).catch(function (error) {
		throw new AV.Cloud.Error("查询失败");
	});
});

AV.Cloud.define('getInfosById',function (request) {
	var query = new AV.Query('Infomation');
	query.equalTo('state',true);
	query.equalTo('writerId',request.params.id);
	return query.find().then(function (results) {
		return results;
	}, function () {
		return false;
	}).catch(function (error) {
		throw new AV.Cloud.Error("查询失败");
	});
});

AV.Cloud.define('deliteInfoById',function (request) {
	var query = new AV.Query('Infomation');
	return query.get(request.params.id).then(function (info) {
		info.set('state',false);
		return info.save().then(function (info) {
			return true;
		},function () {
			return false;
		}).catch(function (error) {
			throw new AV.Cloud.Error("查询失败");
		});
	}, function () {
		return false;
	}).catch(function (error) {
		throw new AV.Cloud.Error("查询失败");
	});
});
