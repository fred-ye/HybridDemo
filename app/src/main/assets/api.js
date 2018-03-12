var Controller = {
  methods: {}, //页面上的函数集合,
  callbackFunList: {},
  id: 0,
  getId: function() {
    return ++this.id;
  },
  registerFunc: function(name, func) {
    this.methods[name] = func;
  },
  setCallbackFunc: function(id, func) {
    this.callbackFunList[id] = func;
  },
  send: function(message) {
    HybridAPI.sendToNative(JSON.stringify(message));
  },
  executeMethod: function(message) {
    var func = this.methods[message.method];
    if (!func) {
      return;
    }
    func(message.params, (error, data) => {
      //如果执行后不需要向native端返回结果
      if (!message.id) {
        return;
      }
      if (error) {
        this.send({
          id: message.id,
          error
        })
      } else {
        this.send({
          data,
          id: message.id
        })
      }
    });
  },
  //Javascript ---> Java 如果带有回调，在Java层执行完后，再执行对应的回调函数.
  executeCallback: function(message) {
    var callbackFun = this.callbackFunList[message.id];
    if (callbackFun) {
      callbackFun(message.error || null, message.data);
    }
    delete this.callbackFunList[message.id];
  }
}

var HybridAPI = window.HybridAPI || WeexAPI || {}

HybridAPI.invoke = function(method, params, callback) {
  var message = {
    method,
    params
  };
  var id;
  if (callback) {
    id = 'Hybrid_CB_' + Controller.getId();
    Controller.setCallbackFunc(id, callback);
    message.id = id;
  }
  Controller.send(message);
}

HybridAPI.sendToJavaScript = function(message) {
  try {
    message = JSON.parse(message);
  } catch (exception) {
    return Controller.send({
      error: {
        msg: 'JSON Parse Error'
      },
      id: null
    })
  }
  if (message.method) {//如果是native调用前端的某个方法
    Controller.executeMethod(message);
  } else if (message.id) { //如果是函数的callback
    Controller.executeCallback(message);
  }
}
HybridAPI.registerHandler = function(name, func) {
  if (!name || !func) {
    return;
  }
  Controller.registerFunc(name, func);
}
